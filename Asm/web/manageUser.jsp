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
                <div class="card__row">
                    <div class="label">Username</div>
                    <div class="value"><%= u.getUserName() %></div>
                </div>
                <div class="card__row">
                    <div class="label">Full Name</div>
                    <div class="value"><%= u.getName() %></div>
                </div>
                <div class="card__row">
                    <div class="label">Email</div>
                    <div class="value"><%= u.getEmail() %></div>
                </div>
                <div class="card__row">
                    <div class="label">Role</div>
                    <div class="value"><%= u.getRole() %></div>
                </div>
                <div class="card__row">
                    <div class="label">Created</div>
                    <div class="value"><%= u.getCreatedAt() %></div>
                </div>
                <div class="card__row">
                    <div class="label">Status</div>
                    <div class="value"><%= u.isIsActive() ? "Active" : "Deleted/Banned" %></div>
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
