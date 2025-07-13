<%-- 
    Document   : registerForm
    Created on : Jul 10, 2025, 8:30:17 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.UserDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>In4 Form</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">   
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}//assets/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    </head>
    <body>


        <%
            UserDTO user = (UserDTO) request.getAttribute("us");
            String errorMessage = (String) request.getAttribute("errorMessage");
            String message = (String) request.getAttribute("message");
            boolean isEdit = request.getAttribute("isEdit") != null;
        %>
        <h1><%= isEdit?"Update Profile":"Sign Up"%></h1>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="<%= isEdit?"updateProfile":"register"%>"/>
            <div>
                <label for="userName">User Name: *</label>
                <input type="text" name="userName" required placeholder="Enter your User Name..."
                       value="<%= (user != null) ? user.getUserName() : "" %>"/>
            </div>
            
            <div>
                <label for="fullName">Full Name: *</label>
                <input type="text" name="fullName" required placeholder="Enter your Full Name..."
                       value="<%= (user != null) ? user.getName() : "" %>"/>
            </div>
            
            <div>
                <label for="email">Email: *</label>
                <input type="text" name="email" required placeholder="Enter your Email..."
                       value="<%= (user != null) ? user.getEmail() : "" %>"/>
            </div>
            
            <div>
                <label for="password">Password: *</label>
                <input type="password" name="password" required placeholder="Enter your Password..."
                       value="<%= (user != null) ? user.getPassword() : "" %>"/>
            </div>
            
            <div>
                <label for="cfPassword">Confirm Password: *</label>
                <input type="password" name="cfPassword" required placeholder="Confirm Password..."/>
            </div>
            
            <div>
                <input type="submit" value="<%= isEdit?"Save":"Sign Up"%>"/>
                <%if (!isEdit){%>
                <input type="reset" value="Reset"/>
                <% } %>
            </div>
        </form>
            <span style="color: red"><%=(errorMessage!=null)?errorMessage:""%></span>
            <span style="color: green"><%=(message!=null)?message:""%></span>
             <jsp:include page="footer.jsp"/>
    </body>
</html>
