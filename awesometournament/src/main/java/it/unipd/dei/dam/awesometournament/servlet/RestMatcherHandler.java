package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Interface that represents an handler of a particular path
 */
public abstract class RestMatcherHandler {
    /**
     * The DataSource is initialized by the factory
     */
    private DataSource dataSource = null;

    /**
     * Sets the DataSource for the object
     * 
     * @param dataSource dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Initializes a Connection object form the DataSource
     * 
     * @return an initialized Connection object
     * @throws SQLException
     */
    protected Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    /**
     * Abstract method that is called when this path is matched
     * 
     * @param method the Method the route was called with
     * @param req Servlet HttpServletRequest object
     * @param res Servlet HttpServletResponse object
     * @param params Array containing the parameters (*)s of the path
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public abstract Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException;
}
