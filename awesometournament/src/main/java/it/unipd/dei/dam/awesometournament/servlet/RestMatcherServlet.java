package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/router")
public class RestMatcherServlet extends AbstractDatabaseServlet {

    private enum Method {
        GET, POST, DELETE, PUT
    }

    private enum Result {
        CONTINUE, STOP
    }

    private interface Handler {
        public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
                String[] params);
    }

    private class Entry {
        public Pattern pattern;
        public Handler handler;
        public boolean partialMatch;

        public Entry(Pattern pattern, Handler handler) {
            this.pattern = pattern;
            this.handler = handler;
        }

        public Entry(String pattern, boolean partialMatch, Handler handler) {
            this.partialMatch = partialMatch;
            String newptn = pattern.replaceAll("\\*", "(\\\\d+)"); // replace * with a regex group
            if(partialMatch)
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
                if(!e.partialMatch)
                    break;
            }
        }
        // the last we found must be a full match
        if(runEntries.size() == 0) {
            LOGGER.info("no matches");
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "path not found");
            return;
        }
        if(runEntries.get(runEntries.size()-1).entry.partialMatch) {
            LOGGER.info("last match partial");
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "path not found");
            return;
        }
        for(EntryPair e: runEntries) {
            Entry entry = e.entry;
            Result result = entry.handler.handle(method, req, res, getConnection(), e.params);
            if(result == Result.STOP)
                break;
        }
    }

    List<Entry> entries;

    public RestMatcherServlet() {
        entries = new ArrayList<>();
        entries.add(new Entry("/", true, new Handler() {
            @Override
            public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
                    String[] params) {
                try {
                    res.getWriter().println("welcome to the root!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Result.CONTINUE;
            }
        }));
        entries.add(new Entry("/test/*", false, new Handler() {
            @Override
            public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
                    String[] params) {
                try {
                    res.getWriter().println("welcome to the test!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Result.CONTINUE;
            }
        }));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.execute(Method.GET, req.getParameter("path"), req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
