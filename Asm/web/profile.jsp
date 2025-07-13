<%-- 
    Document   : profile
    Created on : Jul 13, 2025, 9:39:16 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">   
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}//assets/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <%
            if (!AuthUtils.isLoggedIn(request))
            {
                response.sendRedirect("login.jsp");
            }
            else
            {
                UserDTO user = AuthUtils.isLoggedIn(request)
                   ? AuthUtils.getCurrentUser(request)
                   : null;
        %>
        
        <h1><%= user.getName()%></h1>
        <h2>UserName: <%= user.getUserName()%></h2>
        <h2>Email: <%= user.getEmail()%></h2>
        <h2>Role: <%= user.getRole()%></h2>
        <h2>Create At: <%= user.getCreatedAt()%></h2>
        <h2>Status:  <%=user.isIsActive() ? "Active" : "Inactive"%></h2>
        
        <form>
            
        </form>
        <%
            }
        %>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
