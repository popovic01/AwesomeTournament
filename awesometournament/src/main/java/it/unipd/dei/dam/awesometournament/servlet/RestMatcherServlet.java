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

        public Entry(Pattern pattern, Handler handler) {
            this.pattern = pattern;
            this.handler = handler;
        }

        public Entry(String pattern, Handler handler) {
            String newptn = pattern.replaceAll("\\*", "(\\\\d+)"); //replace * with a regex group
            LOGGER.info("new pattern is " + newptn);
            this.pattern = Pattern.compile(newptn);
            this.handler = handler;
        }
    }

    private void execute(Method method, String path, HttpServletRequest req, HttpServletResponse res) throws Exception {
        for (Entry e : entries) {
            Matcher matcher = e.pattern.matcher(path);
            if (matcher.matches()) {
                LOGGER.info("match found, groupcount is "+matcher.groupCount());
                String[] params = null;
                if (matcher.groupCount() > 0) {
                    params = new String[matcher.groupCount()];
                    for (int i = 0; i < matcher.groupCount(); i++) {
                        params[i] = matcher.group(i+1);
                        LOGGER.info("param is" + params[i]);
                    }

                }
                e.handler.handle(method, req, res, getConnection(), params);
                break; // we suppose to have only one matching entry
            }
        }
    }

    List<Entry> entries;

    public RestMatcherServlet() {
        entries = new ArrayList<>();
        entries.add(new Entry("/", new Handler() {
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
        entries.add(new Entry("/ciao", new Handler() {
            @Override
            public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
                    String[] params) {
                try {
                    res.getWriter().println("welcome to the ciao!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Result.CONTINUE;
            }
        }));
        entries.add(new Entry("/players/*", new Handler() {
            @Override
            public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
                    String[] params) {
                try {
                    res.getWriter().println("welcome to the players!");
                    res.getWriter().println("player: " + params[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Result.CONTINUE;
            }
        }));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("received request");
        try {
            this.execute(Method.GET, req.getParameter("path"), req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
