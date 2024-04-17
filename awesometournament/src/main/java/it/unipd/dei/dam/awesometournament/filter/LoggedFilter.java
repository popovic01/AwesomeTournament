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

public class LoggedFilter implements Filter {
    protected static final Logger LOGGER = LogManager.getLogger(LoggedFilter.class,
            StringFormatterMessageFactory.INSTANCE);

    private DataSource ds;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        // the JNDI lookup context
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

        // I set some attributes the next servlet might want to use
        req.setAttribute("email", session.getAttribute("email"));

        LOGGER.info("filter passed");
        // go to the next step
        chain.doFilter(request, response);
    }
}
