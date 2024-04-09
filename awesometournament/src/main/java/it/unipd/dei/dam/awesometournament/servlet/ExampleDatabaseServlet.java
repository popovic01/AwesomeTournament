package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.database.CreateUserDAO;
import it.unipd.dei.dam.awesometournament.resources.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/db")
public class ExampleDatabaseServlet extends AbstractDatabaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Hello!");

        try (Connection conn = getConnection()) {
            LOGGER.info("Got connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        LOGGER.info("email: "+ email);
        User u = new User(0, email, password);

        try (Connection con = getConnection()) {
            new CreateUserDAO(con, u).access();
            resp.getWriter().println("added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
