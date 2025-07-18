<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.UserDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= (request.getAttribute("isEdit") != null) ? "Update Profile" : "Sign Up" %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/registerForm.css">
</head>
<body>
    <%
        UserDTO user = (UserDTO) request.getAttribute("us");
        String errorMessage = (String) request.getAttribute("errorMessage");
        String message = (String) request.getAttribute("message");
        boolean isEdit = request.getAttribute("isEdit") != null;
    %>

    <form action="MainController" method="post" class="form">
        <p class="title"><%= isEdit ? "Update Profile" : "Sign Up" %></p>
        <input type="hidden" name="action" value="<%= isEdit ? "updateProfile" : "register" %>"/>
        <input type="hidden" name="userId" value="<%= user.getUserId()%>"/>
        <label>
            <input class="input" type="text" name="userName" required value="<%= (user != null) ? user.getUserName() : "" %>">
            <span>User Name *</span>
        </label>

        <label>
            <input class="input" type="text" name="fullName" required value="<%= (user != null) ? user.getName() : "" %>">
            <span>Full Name *</span>
        </label>

        <label>
            <input class="input" type="text" name="email" required value="<%= (user != null) ? user.getEmail() : "" %>">
            <span>Email *</span>
        </label>

        <label>
            <input class="input" type="password" name="password" required value="<%= (user != null) ? user.getPassword() : "" %>">
            <span>Password *</span>
        </label>

        <label>
            <input class="input" type="password" name="cfPassword" required>
            <span>Confirm Password *</span>
        </label>

        <div>
            <input type="submit" class="submit" value="<%= isEdit ? "Save" : "Sign Up" %>"/>
            <% if (!isEdit) { %>
                <input type="reset" class="reset" value="Reset"/>
            <% } %>
        </div>

        <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <div class="error"><%= errorMessage %></div>
        <% } %>

        <% if (message != null && !message.isEmpty()) { %>
            <div class="message"><%= message %></div>
        <% } %>
    </form>
</body>
</html>
