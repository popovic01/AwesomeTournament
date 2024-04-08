package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExampleServlet extends HttpServlet {
    protected final static Logger LOGGER = LogManager.getLogger(ExampleServlet.class, StringFormatterMessageFactory.INSTANCE);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Received get request");
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
