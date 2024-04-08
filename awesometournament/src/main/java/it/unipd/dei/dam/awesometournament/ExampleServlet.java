package it.unipd.dei.dam.awesometournament;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class ExampleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", "DAM");
        List<String> names = new ArrayList<String>();
        names.add("Alberto");
        names.add("Alessandro");
        names.add("Andrea");
        names.add("Andrea");
        names.add("Davide");
        names.add("Milica");
        req.setAttribute("names", names);

        req.setAttribute("date", new Date());

        req.getRequestDispatcher("hello.jsp").forward(req, resp);
    }
}
