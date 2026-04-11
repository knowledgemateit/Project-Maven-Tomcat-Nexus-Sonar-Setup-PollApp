package com.example;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submit-vote")
public class PollServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String option = request.getParameter("option");
        
        if (option != null) {
            // Get the global vote map from Application Scope
            ConcurrentHashMap<String, Integer> votes = (ConcurrentHashMap<String, Integer>) 
                getServletContext().getAttribute("votes");
            
            if (votes == null) {
                votes = new ConcurrentHashMap<>();
                getServletContext().setAttribute("votes", votes);
            }
            
            // Increment the vote count
            votes.merge(option, 1, Integer::sum);
        }
        
        // Redirect back to the main page to see results
        response.sendRedirect("index.jsp");
    }
}
