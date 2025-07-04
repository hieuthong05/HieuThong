<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils"%>
<%@page import="model.UserDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f6f8;
                margin: 0;
                padding: 0;
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100vh;
            }

            .login-container {
                background-color: white;
                padding: 40px 30px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }

            h1 {
                text-align: center;
                color: #333;
                margin-bottom: 30px;
            }

            form {
                display: flex;
                flex-direction: column;
            }

            label {
                font-weight: 600;
                margin-bottom: 5px;
                color: #444;
            }

            input[type="text"],
            input[type="password"] {
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 14px;
                transition: border-color 0.3s;
            }

            input[type="text"]:focus,
            input[type="password"]:focus {
                border-color: #007bff;
                outline: none;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.2);
            }

            input[type="submit"] {
                background-color: #007bff;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            .error-message {
                color: red;
                text-align: center;
                margin-top: 15px;
                font-size: 14px;
            }
        </style>

            
    </head>
    <body>

        <%
            if (AuthUtils.isLoggedIn(request)) {
                response.sendRedirect("welcome.jsp");
            } else {
        %>
        <div class="login-container">
            <h1>Login</h1>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="login" />

                <label for="username">Username*</label>
                <input type="text" id="username" name="username" required />

                <label for="password">Password*</label>
                <input type="password" id="password" name="password" required />

                <input type="submit" value="Login" />
            </form>

            <%
                Object objMessage = request.getAttribute("message");
                String message = (objMessage == null) ? "" : (objMessage + "");
                if (!message.isEmpty()) {
            %>
                <div class="error-message"><%= message %></div>
            <%
                }
            %>
        </div>
        <%
            }
        %>
    </body>
</html>