package com.example;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
// CHANGE THESE TO JAKARTA
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/submit-vote")
public class PollServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String option = request.getParameter("option");
        
        // Get the global vote map from Application Scope
        // Note: 'getServletContext()' is now 'jakarta.servlet.ServletContext'
        ConcurrentHashMap<String, Integer> votes = (ConcurrentHashMap<String, Integer>) 
            getServletContext().getAttribute("votes");
        
        if (votes == null) {
            votes = new ConcurrentHashMap<>();
            getServletContext().setAttribute("votes", votes);
        }
        
        if (option != null) {
            votes.merge(option, 1, Integer::sum);
        }
        
        response.sendRedirect("index.jsp");
    }
}
