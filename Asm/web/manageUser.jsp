<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<%@page import="model.DTO.UserDTO" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/manageUser.css">
</head>
<body>
<%
    if (AuthUtils.isAdmin(request)) {
        List<UserDTO> list = (List<UserDTO>) request.getAttribute("list");
        if (list == null || list.isEmpty()) {
%>
    <span style="color: red"><h1>Have No Users!</h1></span>
<%
        } else {
%>
    <div class="card-wrapper">
<%
            for(UserDTO u : list) {
%>
        <div class="card">
            <div class="card__title">User ID: <%= u.getUserId() %></div>
            <div class="card__data">
                <div class="card__right">
                    <div class="item">Username</div>
                    <div class="item">Full Name</div>
                    <div class="item">Email</div>
                    <div class="item">Role</div>
                    <div class="item">Created</div>
                    <div class="item">Status</div>
                </div>
                <div class="card__left">
                    <div class="item"><%= u.getUserName() %></div>
                    <div class="item"><%= u.getName() %></div>
                    <div class="item"><%= u.getEmail() %></div>
                    <div class="item"><%= u.getRole() %></div>
                    <div class="item"><%= u.getCreatedAt() %></div>
                    <div class="item"><%= u.isIsActive() ? "Active" : "Deleted/Banned" %></div>
                </div>
            </div>
        </div>
<%
            }
%>
    </div>
<%
        }
    } else {
        response.sendRedirect("MainController");
    }
%>
</body>
</html>
