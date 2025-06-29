<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="java.util.List"%>
<%@page import="model.ProjectDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Dashboard</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <!-- Icons (Font Awesome) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to right, #e0eafc, #cfdef3);
            margin: 0;
            padding: 0;
            color: #2c3e50;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        h1, h2, h3 {
            text-align: center;
        }

        h1 {
            color: #34495e;
        }

        h2 {
            color: #2c3e50;
            font-size: 20px;
        }

        h3 {
            margin-top: 40px;
            color: #2d3436;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table thead {
            background-color: #3498db;
            color: white;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ccc;
        }

        tr:nth-child(even) {
            background-color: #f8f9fa;
        }

        .button {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 10px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            border: none;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s ease;
        }

        .button:hover {
            background-color: #2980b9;
        }

        .form-group {
            margin: 20px 0;
            text-align: center;
        }

        input[type="text"] {
            padding: 8px;
            width: 250px;
            border-radius: 4px;
            border: 1px solid #ccc;
            margin-right: 10px;
        }

        .message {
            text-align: center;
            font-weight: bold;
            padding: 10px;
        }

        .success-message {
            color: green;
        }

        .error-message {
            color: red;
        }

        .status-badge {
            padding: 6px 10px;
            border-radius: 6px;
            font-weight: bold;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }

        .status-Ideation {
            background-color: #f1c40f;
            color: #fff;
        }

        .status-Development {
            background-color: #2980b9;
            color: #fff;
        }

        .status-Launch {
            background-color: #27ae60;
            color: #fff;
        }

        .status-Scaling {
            background-color: #8e44ad;
            color: #fff;
        }
    </style>
</head>
<body>
<div class="container">
    <%
        UserDTO user = AuthUtils.getCurrentUser(request);
        Object objList = session.getAttribute("projects");
        List<ProjectDTO> list = (objList != null) ? (List<ProjectDTO>) objList : null;
        String message = (String) request.getAttribute("message");

        if (AuthUtils.isLoggedIn(request)) {
    %>
    <h1>Startup Project Dashboard</h1>
    <h2>Welcome, <%=user.getName()%> 👋</h2>

    <% if (message != null) { %>
        <p class="message success-message"><%= message %></p>
    <% } %>

    <% if (list == null || list.isEmpty()) { %>
        <p class="message error-message">No Projects.</p>
    <% } else { %>
        <h3>View All Projects</h3>
        <table>
            <thead>
            <tr>
                <th>Project Name</th>
                <th>Description</th>
                <th>Status</th>
                <th>Estimated Launch</th>
            </tr>
            </thead>
            <tbody>
            <% for (ProjectDTO proj : list) {
                String status = proj.getStatus();
                String statusClass = "status-" + status;
                String statusIcon = "";

                if ("Ideation".equals(status)) {
                    statusIcon = "<i class='fas fa-lightbulb'></i>";
                } else if ("Development".equals(status)) {
                    statusIcon = "<i class='fas fa-code'></i>";
                } else if ("Launch".equals(status)) {
                    statusIcon = "<i class='fas fa-rocket'></i>";
                } else if ("Scaling".equals(status)) {
                    statusIcon = "<i class='fas fa-chart-line'></i>";
                }
            %>
                <tr>
                    <td><%=proj.getProjName()%></td>
                    <td><%=proj.getDescription()%></td>
                    <td>
                        <span class="status-badge <%=statusClass%>">
                            <%= statusIcon %> <%= status %>
                        </span>
                    </td>
                    <td><%=proj.getEst()%></td>
                </tr>
            <% } %>
            </tbody>
        </table>
    <% } %>

    <div class="form-group">
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="logout"/>
            <input type="submit" class="button" value="Logout"/>
        </form>
    </div>

    <hr/>

    <% if (AuthUtils.isFounder(request)) {
        String keyword = (String) request.getAttribute("keyword");
    %>

    <div class="form-group">
        <form action="projectForm.jsp" method="post">
            <input type="submit" class="button" value="Create New Project"/>
        </form>
    </div>

    <div class="form-group">
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="searchProjectName"/>
            <input type="text" name="kw" value="<%=keyword != null ? keyword : ""%>" placeholder="Enter project name..."/>
            <input type="submit" class="button" value="Search"/>
        </form>
    </div>

    <%
        List<ProjectDTO> searchList = (List<ProjectDTO>) request.getAttribute("searchList");

        if (searchList != null && searchList.isEmpty()) {
    %>
    <p class="message error-message">No Projects match the keyword!</p>
    <% } else if (searchList != null) { %>
    <h3>Search Results</h3>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Estimated Launch</th>
            <% if (AuthUtils.isFounder(request)) { %>
                <th>Update Status</th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <% for (ProjectDTO proj : searchList) {
            String status = proj.getStatus();
            String statusClass = "status-" + status;
            String statusIcon = "";

            if ("Ideation".equals(status)) {
                statusIcon = "<i class='fas fa-lightbulb'></i>";
            } else if ("Development".equals(status)) {
                statusIcon = "<i class='fas fa-code'></i>";
            } else if ("Launch".equals(status)) {
                statusIcon = "<i class='fas fa-rocket'></i>";
            } else if ("Scaling".equals(status)) {
                statusIcon = "<i class='fas fa-chart-line'></i>";
            }
        %>
            <tr>
                <td><%=proj.getProjId()%></td>
                <td><%=proj.getProjName()%></td>
                <td><%=proj.getDescription()%></td>
                <td>
                    <span class="status-badge <%=statusClass%>">
                        <%= statusIcon %> <%= status %>
                    </span>
                </td>
                <td><%=proj.getEst()%></td>
                <% if (AuthUtils.isFounder(request)) { %>
                    <td>
                        <form action="MainController" method="post">
                            <input type="hidden" name="action" value="editProject"/>
                            <input type="hidden" name="projId" value="<%=proj.getProjId()%>"/>
                            <input type="hidden" name="kw" value="<%=keyword != null ? keyword : ""%>"/>
                            <input type="submit" class="button" value="Edit"/>
                        </form>
                    </td>
                <% } %>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
    <% } %>
    <% } else {
        response.sendRedirect("MainController");
    } %>
</div>
</body>
</html>
