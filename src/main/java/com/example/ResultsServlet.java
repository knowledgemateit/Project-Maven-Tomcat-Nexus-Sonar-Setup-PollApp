package com.example;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/get-results")
public class ResultsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ConcurrentHashMap<String, Integer> votes = (ConcurrentHashMap<String, Integer>) getServletContext().getAttribute("votes");
        StringBuilder sb = new StringBuilder();
        
        if (votes == null || votes.isEmpty()) {
            sb.append("<p>No votes yet!</p>");
        } else {
            votes.forEach((k, v) -> sb.append("<div class='result-bar'>").append(k).append(": ").append(v).append("</div>"));
        }
        
        resp.setContentType("text/html");
        resp.getWriter().write(sb.toString());
    }
}
