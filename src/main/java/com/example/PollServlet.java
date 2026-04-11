package com.example;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/submit-vote")
public class PollServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        ConcurrentHashMap<String, Integer> votes = (ConcurrentHashMap<String, Integer>) getServletContext().getAttribute("votes");
        
        if (votes == null) {
            votes = new ConcurrentHashMap<>();
            getServletContext().setAttribute("votes", votes);
        }
        
        if (option != null) {
            votes.merge(option, 1, Integer::sum);
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // Keeps the user on the same page
    }
}
