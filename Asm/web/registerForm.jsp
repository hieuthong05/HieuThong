<%-- 
    Document   : registerForm
    Created on : Jul 10, 2025, 8:30:17 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Form</title>
    </head>
    <body>
        <h1>Sign Up</h1>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="register"/>
            <div>
                <label for="userName">User Name: *</label>
                <input type="text" name="userName" required placeholder="Enter your User Name..."/>
            </div>
            
            <div>
                <label for="fullName">Full Name: *</label>
                <input type="text" name="fullName" required placeholder="Enter your Full Name..."/>
            </div>
            
            <div>
                <label for="email">Email: *</label>
                <input type="text" name="email" required placeholder="Enter your Email..."/>
            </div>
            
            <div>
                <label for="password">Password: *</label>
                <input type="password" name="password" required placeholder="Enter your Password..."/>
            </div>
            
            <div>
                <label for="cfPassword">Confirm Password: *</label>
                <input type="password" name="cfPassword" required placeholder="Confirm Password..."/>
            </div>
            
            <div>
                <input type="submit" value="Sign Up"/>
                <input type="reset" value="Reset Form"/>
            </div>
        </form>
    </body>
</html>
