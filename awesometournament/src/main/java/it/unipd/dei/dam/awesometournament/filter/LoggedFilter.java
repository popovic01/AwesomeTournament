package it.unipd.dei.dam.awesometournament.filter;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.apache.tomcat.jdbc.pool.DataSource;

import it.unipd.dei.dam.awesometournament.resources.LogContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * A filter to check if a user is logged in.
 */
public class LoggedFilter implements Filter {
    /**
     * The logger for this class.
     */
    protected static final Logger LOGGER = LogManager.getLogger(LoggedFilter.class,
            StringFormatterMessageFactory.INSTANCE);

    /**
     * The data source for connecting to the database.
     */
    private DataSource ds;

    /**
     * Initializes the filter.
     *
     * @param filterConfig The filter configuration.
     * @throws ServletException If an error occurs during initialization.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        InitialContext cxt;

        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/awesome");
        } catch (NamingException e) {
            ds = null;

            LOGGER.error("Unable to acquire the connection pool to the database.", e);

            throw new ServletException("Unable to acquire the connection pool to the database", e);
        }
    }

    /**
     * Filters requests to check if the user is logged in.
     *
     * @param request  The servlet request.
     * @param response The servlet response.
     * @param chain    The filter chain.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet error occurs.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        LogContext.setIPAddress(request.getRemoteAddr());

        LOGGER.info("doFilter for LoggedFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(false);
        if (session == null) {
            LOGGER.info("No session found");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in!");
            return;
        }

        req.setAttribute("session_email", session.getAttribute("email"));
        req.setAttribute("session_id", session.getAttribute("id"));

        LOGGER.info("filter passed");
        chain.doFilter(request, response);
    }
}
