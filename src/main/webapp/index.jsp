<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Classroom Live Poll</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; text-align: center; margin-top: 50px; background-color: #f4f4f9; }
        h1 { color: #333; }
        .poll-buttons button { 
            padding: 15px 25px; margin: 10px; font-size: 16px; cursor: pointer; 
            border: none; border-radius: 8px; background-color: #007bff; color: white; 
            transition: background 0.3s; 
        }
        .poll-buttons button:hover { background-color: #0056b3; }
        
        #results-container { 
            max-width: 400px; margin: 20px auto; padding: 20px; 
            background: white; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); 
        }
        .result-bar { 
            background: #4CAF50; color: white; padding: 12px; margin: 8px 0; 
            border-radius: 6px; font-weight: bold; text-align: left;
            animation: fadeIn 0.5s;
        }
        @keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
    </style>
</head>
<body>

    <h1>Which DevOps tool is your favorite?</h1>
    
    <form action="submit-vote" method="post" target="hidden_iframe" class="poll-buttons">
        <button name="option" value="Docker">Docker</button>
        <button name="option" value="Kubernetes">Kubernetes</button>
        <button name="option" value="Terraform">Terraform</button>
        <button name="option" value="Jenkins">Jenkins</button>
    </form>

    <iframe name="hidden_iframe" id="hidden_iframe" style="display:none;"></iframe>

    <hr style="width: 50%; margin: 30px auto;">

    <h2>Live Results</h2>
    <div id="results-container">
        <%
            // Initial load using JSP logic
            ConcurrentHashMap<String, Integer> votes = (ConcurrentHashMap<String, Integer>) application.getAttribute("votes");
            if (votes != null && !votes.isEmpty()) {
                for (String key : votes.keySet()) {
                    out.println("<div class='result-bar'>" + key + ": " + votes.get(key) + " votes</div>");
                }
            } else {
                out.println("<p id='no-votes'>No votes yet. Be the first!</p>");
            }
        %>
    </div>

    <script>
        /**
         * This function fetches the updated HTML from the 'get-results' servlet
         * and injects it into the results-container without refreshing the page.
         */
        function updatePollResults() {
            fetch('get-results')
                .then(response => {
                    if (!response.ok) throw new Error("Servlet not found");
                    return response.text();
                })
                .then(html => {
                    document.getElementById('results-container').innerHTML = html;
                })
                .catch(err => console.warn("Waiting for Servlet to be ready..."));
        }

        // Check for new votes every 2 seconds
        setInterval(updatePollResults, 2000);
    </script>

</body>
</html>
