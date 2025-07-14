<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    </head>
    <body>
        <%
            if (AuthUtils.isLoggedIn(request)) {
                response.sendRedirect("home.jsp");
            } else {
        %>
        
        

        <!-- LOGIN FORM -->
        <div class="container">
            <form action="MainController" method="post" class="form">
                <div class="title">Welcome,<br><span>Login to continue</span></div>
                <input type="hidden" name="action" value="login"/>

                <div>
                    <input type="text" name="un" class="input" placeholder="UserName" required/>
                </div>

                <div>
                    <input type="password" name="pw" class="input" placeholder="Password" required/>
                </div>

                <%
                    Object obj = request.getAttribute("message");
                    String message = (obj != null)? ((String)obj):"";
                %>
                <span class="title" style="color: red"><%=message%></span>
                
                <div>
                    <input type="submit" value="Login" class="button-confirm"/>
                </div>
            </form>     
        </div>

        <%
            }
        %>
    </body>
</html>
