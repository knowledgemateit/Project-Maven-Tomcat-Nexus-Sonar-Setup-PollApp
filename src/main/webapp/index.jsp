<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
<html>
<head>
    <title>Classroom Live Poll</title>
    <style>
        body { font-family: Arial; text-align: center; margin-top: 50px; }
        .result-bar { background: #4CAF50; color: white; padding: 5px; margin: 5px; border-radius: 5px; }
    </style>
</head>
<body>
    <h1>Which DevOps tool is your favorite?</h1>
    
    <form action="submit-vote" method="post">
        <button name="option" value="Docker">Docker</button>
        <button name="option" value="Kubernetes">Kubernetes</button>
        <button name="option" value="Terraform">Terraform</button>
        <button name="option" value="Jenkins">Jenkins</button>
    </form>

    <hr>
    <h2>Live Results</h2>
    <%
        ConcurrentHashMap<String, Integer> votes = (ConcurrentHashMap<String, Integer>) 
            application.getAttribute("votes");
        if (votes != null) {
            for (String key : votes.keySet()) {
                out.println("<div class='result-bar'>" + key + ": " + votes.get(key) + " votes</div>");
            }
        } else {
            out.println("<p>No votes yet. Be the first!</p>");
        }
    %>
</body>
</html>
