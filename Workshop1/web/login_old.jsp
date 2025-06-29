<%-- 
    Document   : login
    Created on : Jun 20, 2025, 9:07:28 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <%
            if (AuthUtils.isLoggedIn(request))
            {
                response.sendRedirect("dashboard.jsp");
            }
            else
            {
        %>
        
        <h1>LOGIN</h1>
        
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="login"/>
            <div>
                <label for="username">Username: </label>
                <input type="text" name="un"/>
            </div>
            
            <div>
                <label for="password">Password: </label>
                <input type="password" name="pw"/>
            </div>
            
            <div>
                <input type="submit" value="Login"/>
            </div>
            
        </form>
        
        <%
            Object obj = request.getAttribute("message");
            String message = (obj != null)? ((String)obj):"";
        %>
        
        <span style="color: red"><%=message%></span>
        
        <%
            }
        %>
    </body>
</html>
