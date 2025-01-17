package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dam.awesometournament.servlet.handler.*;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for routing HTTP requests to appropriate handlers based on the URL pattern
 */
@MultipartConfig
public class RestMatcherServlet extends AbstractDatabaseServlet {

    ObjectMapper om = new ObjectMapper();
    ResponsePackageNoData response;

    /**
     * Enumeration defining HTTP methods
     */
    public enum Method {
        GET, POST, DELETE, PUT
    }

    /**
     * Enumeration defining possible results of URL matching
     */

    public enum Result {
        CONTINUE, STOP
    }

    /**
     * Represents an entry in the routing table.
     * An entry has to be partial if the matching of entries should
     * continue after this one
     */
    private static class Entry {
        public Pattern pattern;
        public RestMatcherHandler handler;
        public boolean partialMatch;

        /**
         * Constructs an Entry object.
         *
         * @param pattern       The URL pattern to match against
         * @param partialMatch  Indicates whether the pattern is a partial match
         * @param handler       The handler to execute if the pattern matches
         */
        public Entry(String pattern, boolean partialMatch, RestMatcherHandler handler) {
            this.partialMatch = partialMatch;
            String newptn = pattern.replaceAll("\\*", "(\\\\d+)"); // replace * with a regex group
            if (partialMatch)
                newptn = newptn + ".*";
            this.pattern = Pattern.compile(newptn);
            this.handler = handler;
        }
    }

    /**
     * Represents a matched entry along with its parameters
     */
    private static class EntryPair {
        public Entry entry;
        public String[] params;

        public EntryPair(Entry entry, String[] params) {
            this.entry = entry;
            this.params = params;
        }
    }

    /**
     * Executes the appropriate handler for the given HTTP method and URL path.
     *
     * @param method     The HTTP method of the request
     * @param path       The URL path of the request
     * @param req        The HTTP servlet request object
     * @param res        The HTTP servlet response object
     * @throws Exception If an error occurs during execution
     */
    private void execute(Method method, String path, HttpServletRequest req, HttpServletResponse res) throws Exception {
        LOGGER.info("Rest handler path = "+path);
        if(path == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
            return;
        }
        // To be executed, we need to find at least one full match!
        ArrayList<EntryPair> runEntries = new ArrayList<>();
        for (Entry e : entries) {
            Matcher matcher = e.pattern.matcher(path);
            if (matcher.matches()) {
                LOGGER.info("path: " + path + ", match found: " + e.pattern);
                String[] params = null;
                if (matcher.groupCount() > 0) {
                    params = new String[matcher.groupCount()];
                    for (int i = 0; i < matcher.groupCount(); i++) {
                        params[i] = matcher.group(i + 1);
                    }
                }
                runEntries.add(new EntryPair(e, params));
                if (!e.partialMatch)
                    break;
            }
        }
        // the last we found must be a full match
        if (runEntries.size() == 0) {
            LOGGER.info("no matches");
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (runEntries.get(runEntries.size() - 1).entry.partialMatch) {
            LOGGER.info("last match partial");
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        for (EntryPair e : runEntries) {
            Entry entry = e.entry;
            Result result = entry.handler.handle(method, req, res, e.params);
            if (result == Result.STOP)
                break;
        }
    }

    /**
     * Creates an instance of a handler class using reflection.
     *
     * @param clazz The class of the handler
     * @return      The instantiated handler object
     */
    private RestMatcherHandler factoryHandler(Class<? extends RestMatcherHandler> clazz) {
        try {
            Constructor<? extends RestMatcherHandler> constructor = clazz.getDeclaredConstructor();
            RestMatcherHandler handler = constructor.newInstance();
            handler.setDataSource(this.getDataSource());
            return handler;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    List<Entry> entries;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        /*
         * The order of the entries matter in che case partial matching.
         */
        entries = new ArrayList<>();
        entries.add(new Entry("/", true, factoryHandler(SessionHandler.class)));
        entries.add(new Entry("/matches/*", true, factoryHandler(MatchAuthenticatorHandler.class)));
        entries.add(new Entry("/matches/*", false, factoryHandler(MatchHandler.class)));
        entries.add(new Entry("/players/*", false, factoryHandler(PlayerHandler.class)));
        entries.add(new Entry("/tournaments/*/teams", false, factoryHandler(TournamentTeamHandler.class)));
        entries.add(new Entry("/tournaments/*/teams/*", false, factoryHandler(TournamentTeamHandler.class)));
        entries.add(new Entry("/tournaments/*", false, factoryHandler(TournamentIdHandler.class)));
        entries.add(new Entry("/tournaments", false, factoryHandler(TournamentHandler.class)));
        entries.add(new Entry("/tournaments/*/matches", false, factoryHandler(TournamentMatchesHandler.class)));
        entries.add(new Entry("/teams/*/players", false, factoryHandler(TeamPlayerHandler.class)));
        entries.add(new Entry("/teams/*", true, factoryHandler(TeamAuthenticatorHandler.class)));
        entries.add(new Entry("/teams/*", false, factoryHandler(TeamHandler.class)));
        entries.add(new Entry("/events/*", false, factoryHandler(EventHandler.class)));
        entries.add(new Entry("/matches/*/events", false, factoryHandler(MatchEventHandler.class)));
    }

    /**
     * Extracts the subpath from the request URL.
     *
     * @param req The HTTP servlet request object
     * @return    The subpath extracted from the request URL
     */
    private String getSubpath(HttpServletRequest req) {
        return req.getPathInfo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.execute(Method.GET, getSubpath(req), req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.execute(Method.POST, getSubpath(req), req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.execute(Method.PUT, getSubpath(req), req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.execute(Method.DELETE, getSubpath(req), req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}