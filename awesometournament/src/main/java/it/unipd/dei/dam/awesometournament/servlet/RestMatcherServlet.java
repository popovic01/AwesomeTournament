package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unipd.dei.dam.awesometournament.servlet.handler.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestMatcherServlet extends AbstractDatabaseServlet {

    public enum Method {
        GET, POST, DELETE, PUT
    }

    public enum Result {
        CONTINUE, STOP
    }

    private class Entry {
        public Pattern pattern;
        public RestMatcherHandler handler;
        public boolean partialMatch;

        public Entry(String pattern, boolean partialMatch, RestMatcherHandler handler) {
            this.partialMatch = partialMatch;
            String newptn = pattern.replaceAll("\\*", "(\\\\d+)"); // replace * with a regex group
            if (partialMatch)
                newptn = newptn + ".*";
            this.pattern = Pattern.compile(newptn);
            this.handler = handler;
        }
    }

    private class EntryPair {
        public Entry entry;
        public String[] params;

        public EntryPair(Entry entry, String[] params) {
            this.entry = entry;
            this.params = params;
        }
    }

    private void execute(Method method, String path, HttpServletRequest req, HttpServletResponse res) throws Exception {
        // To be executed, we need to find at least one full match!
        ArrayList<EntryPair> runEntries = new ArrayList<>();
        for (Entry e : entries) {
            Matcher matcher = e.pattern.matcher(path);
            if (matcher.matches()) {
                LOGGER.info("match found: " + e.pattern);
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
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "path not found");
            return;
        }
        if (runEntries.get(runEntries.size() - 1).entry.partialMatch) {
            LOGGER.info("last match partial");
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "path not found");
            return;
        }
        for (EntryPair e : runEntries) {
            Entry entry = e.entry;
            Result result = entry.handler.handle(method, req, res, e.params);
            if (result == Result.STOP)
                break;
        }
    }

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

        entries = new ArrayList<>();
        entries.add(new Entry("/", true, factoryHandler(SessionHandler.class)));
        entries.add(new Entry("/matches/*", true, factoryHandler(MatchAuthenticatorHandler.class)));
        entries.add(new Entry("/matches/*", false, factoryHandler(MatchHandler.class)));
        entries.add(new Entry("/players/*", false, factoryHandler(PlayerHandler.class)));
        entries.add(new Entry("/tournaments/*/matches", false, factoryHandler(TournamentMatchesHandler.class)));
        entries.add(new Entry("/teams/*/players", false, factoryHandler(TeamPlayerHandler.class)));
        entries.add(new Entry("/teams/*", true, factoryHandler(TeamAuthenticatorHandler.class)));
        entries.add(new Entry("/teams/*", false, factoryHandler(TeamHandler.class)));
        entries.add(new Entry("/matches/*/events", false, factoryHandler(EventHandler.class)));
    }

    private String getSubpath(HttpServletRequest req) {
        String url = req.getPathInfo();
        return url;
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

/*
 * Examples of handlers:
 * 
 * 
 * entries.add(new Entry("/", true, new Handler() {
 * 
 * @Override
 * public Result handle(Method method, HttpServletRequest req,
 * HttpServletResponse res, Connection connection,
 * String[] params) {
 * try {
 * res.getWriter().println("welcome to the root!");
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * return Result.CONTINUE;
 * }
 * }));
 * entries.add(new Entry("/test", true, new Handler() {
 * 
 * @Override
 * public Result handle(Method method, HttpServletRequest req,
 * HttpServletResponse res, Connection connection,
 * String[] params) {
 * try {
 * res.getWriter().println("test father node");
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * return Result.CONTINUE;
 * }
 * }));
 * entries.add(new Entry("/test", false, new Handler() {
 * 
 * @Override
 * public Result handle(Method method, HttpServletRequest req,
 * HttpServletResponse res, Connection connection,
 * String[] params) {
 * try {
 * res.getWriter().println("welcome to the test!");
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * return Result.CONTINUE;
 * }
 * }));
 * entries.add(new Entry("/test/*", false, new Handler() {
 * 
 * @Override
 * public Result handle(Method method, HttpServletRequest req,
 * HttpServletResponse res, Connection connection,
 * String[] params) {
 * try {
 * res.getWriter().println("welcome to the test/id!");
 * res.getWriter().println("param is " + params[0]);
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * return Result.CONTINUE;
 * }
 * }));
 * 
 * 
 */
