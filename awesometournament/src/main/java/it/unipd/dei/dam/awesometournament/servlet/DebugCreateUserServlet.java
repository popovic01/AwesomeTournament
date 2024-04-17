package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.database.CreateUserDAO;
import it.unipd.dei.dam.awesometournament.resources.entities.User;
import it.unipd.dei.dam.awesometournament.utils.Hashing;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DebugCreateUserServlet extends AbstractDatabaseServlet{
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
        String clearpassword = req.getParameter("password");
        String hashedpassword = Hashing.hashPassword(clearpassword);
        LOGGER.info("email: "+ email);
        User u = new User(0, email, hashedpassword);

        try (Connection con = getConnection()) {
            CreateUserDAO cud = new CreateUserDAO(con, u);
            cud.access();
            Integer newId = cud.getOutputParam();
            if(newId != null) {
                resp.getWriter().println(newId);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
