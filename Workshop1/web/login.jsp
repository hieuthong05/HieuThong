<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    
    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to right, #74ebd5, #ACB6E5);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            background-color: #ffffff;
            padding: 40px 30px;
            border-radius: 12px;
            box-shadow: 0px 10px 25px rgba(0, 0, 0, 0.2);
            width: 400px;
        }

        h1 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 25px;
        }

        form div {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            color: #34495e;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #3498db;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #2980b9;
        }

        .message {
            text-align: center;
            color: red;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>

<body>
<%
    if (AuthUtils.isLoggedIn(request)) {
        response.sendRedirect("dashboard.jsp");
    } else {
%>
    <div class="login-container">
        <h1>Login</h1>

        <form action="MainController" method="post">
            <input type="hidden" name="action" value="login"/>

            <div>
                <label for="username">Username:</label>
                <input type="text" name="un" id="username" required/>
            </div>

            <div>
                <label for="password">Password:</label>
                <input type="password" name="pw" id="password" required/>
            </div>

            <div>
                <input type="submit" value="Login"/>
            </div>
        </form>

        <%
            Object obj = request.getAttribute("message");
            String message = (obj != null) ? (String) obj : "";
        %>

        <div class="message"><%= message %></div>
    </div>
<%
    }
%>
</body>
</html>
