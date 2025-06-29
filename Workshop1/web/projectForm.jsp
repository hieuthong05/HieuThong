<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<%@page import="model.ProjectDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Form</title>

    <!-- Google Fonts + Font Awesome -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to right, #f0f9ff, #dff6ff);
            margin: 0;
            padding: 0;
            color: #2c3e50;
        }

        .container {
            max-width: 650px;
            margin: 50px auto;
            padding: 30px 40px;
            background-color: #ffffff;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #0077b6;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
            color: #1d3557;
        }

        input[type="text"],
        input[type="date"],
        textarea,
        select {
            width: 100%;
            padding: 10px 12px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            background-color: #f8f9fa;
        }

        textarea {
            min-height: 100px;
            resize: vertical;
        }

        input[type="submit"],
        input[type="reset"] {
            margin-top: 25px;
            padding: 10px 16px;
            border: none;
            border-radius: 8px;
            font-weight: bold;
            font-size: 15px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"] {
            background-color: #00b4d8;
            color: #ffffff;
        }

        input[type="submit"]:hover {
            background-color: #0096c7;
        }

        input[type="reset"] {
            background-color: #adb5bd;
            color: white;
            margin-left: 10px;
        }

        input[type="reset"]:hover {
            background-color: #6c757d;
        }

        .message {
            margin-top: 20px;
            padding: 12px;
            border-radius: 8px;
            font-weight: bold;
        }

        .error-message {
            background-color: #ffe6e6;
            color: #c0392b;
        }

        .success-message {
            background-color: #d4edda;
            color: #2e7d32;
        }
    </style>
</head>
<body>
    <div class="container">
    <%
        if (AuthUtils.isFounder(request)) {
            ProjectDTO proj = (ProjectDTO) request.getAttribute("proj");
            String errorMessage = (String) request.getAttribute("errorMessage");
            String message = (String) request.getAttribute("message");
            String keyword = (String) request.getAttribute("keyword");
            boolean isEdit = request.getAttribute("isEdit") != null;
    %>
        <h1><%= isEdit ? "Update Project Status" : "Create New Project" %></h1>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="<%= isEdit ? "updateProjectStatus" : "createProject" %>"/>

            <% if (keyword != null) { %>
                <input type="hidden" name="kw" value="<%=keyword%>"/>
            <% } %>

            <label for="id">Project ID*</label>
            <input type="text" name="id" required placeholder="Enter project ID..."
                   value="<%=proj != null ? proj.getProjId() : "" %>"
                   <%= isEdit ? "readonly" : "" %> />

            <label for="name">Project Name*</label>
            <input type="text" name="name" required placeholder="Enter project name..."
                   value="<%=proj != null ? proj.getProjName() : "" %>"
                   <%= isEdit ? "readonly" : "" %> />

            <label for="description">Description</label>
            <textarea name="description" placeholder="Enter project description..." <%= isEdit ? "readonly" : "" %>>
<%=proj != null ? proj.getDescription() : ""%>
            </textarea>

            <label for="status">Status</label>
            <select id="status" name="status" required>
                <option value="Empty" <%= (proj == null) ? "selected" : "" %>>Choose Status</option>
                <option value="Ideation" <%= proj != null && "Ideation".equals(proj.getStatus()) ? "selected" : "" %>>💡 Ideation</option>
                <option value="Development" <%= proj != null && "Development".equals(proj.getStatus()) ? "selected" : "" %>>🔧 Development</option>
                <option value="Launch" <%= proj != null && "Launch".equals(proj.getStatus()) ? "selected" : "" %>>🚀 Launch</option>
                <option value="Scaling" <%= proj != null && "Scaling".equals(proj.getStatus()) ? "selected" : "" %>>📈 Scaling</option>
            </select>

            <label for="est">Estimated Launch Date</label>
            <input type="date" id="launch" name="est" required
                   value="<%=proj != null ? proj.getEst().toString() : "" %>"
                   <%= isEdit ? "readonly" : "" %> />

            <input type="submit" value="<%= isEdit ? "Update Project" : "Add Project" %>"/>
            <% if (!isEdit) { %>
                <input type="reset" value="Reset Form"/>
            <% } %>
        </form>

        <% if (errorMessage != null) { %>
            <div class="message error-message"><%= errorMessage %></div>
        <% } %>

        <% if (message != null) { %>
            <div class="message success-message"><%= message %></div>
        <% } %>

    <%
        } else {
    %>
        <div class="message error-message">
            <%= AuthUtils.getAccessDeniedMessage("Create Project Form") %>
        </div>
    <%
        }
    %>
    </div>
</body>
</html>
