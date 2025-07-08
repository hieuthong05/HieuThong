<%-- 
    Document   : home
    Created on : Jul 6, 2025, 4:39:36 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <style>
  .dropdown {
    position: relative;
    display: inline-block;
  }

  .dropbtn {
    background-color: #3498db;
    color: white;
    padding: 10px;
    font-size: 14px;
    border: none;
    cursor: pointer;
  }

  .dropdown-content {
    display: none;
    position: absolute;
    background-color: #f1f1f1;
    min-width: 160px;
    z-index: 1;
  }

  .dropdown-content button {
    width: 100%;
    padding: 10px;
    border: none;
    background: none;
    text-align: left;
    cursor: pointer;
  }

  .dropdown:hover .dropdown-content {
    display: block;
  }

  .dropdown-content button:hover {
    background-color: #ddd;
  }
</style>

    </head>
    <body>
        <h1>FPT Shop</h1>
        <%
            if (!AuthUtils.isLoggedIn(request))
            {
        %>
                <div>
                    <form>
                        <input type="submit" value="Sign Up"/>
                    </form>
                </div>
        
                <div>
                    <form action="login.jsp" method="post">
                        <input type="submit" value="Login"/>
                    </form>
                </div>
                
                <div>
                    <form>
                        <input type="submit" value="Category"/>
                    </form>
                </div>

        <%
            }
        %>
        
        
        
        
        
        
        <%
            UserDTO user = AuthUtils.getCurrentUser(request);
            if (AuthUtils.isLoggedIn(request))
            {
        %>
                <form method="post" action="MainController">
                <div class="dropdown">
                  <button class="dropbtn"><%=user.getName()%></button>
                  <div class="dropdown-content">
                    <button type="submit" >Delete Account</button>
                    <button type="submit" >Update Profile</button>
                    <button type="submit" >Logout</button>
                  </div>
                </div>
              </form>
                  
            <div>
                <form>
                    <input type="submit" value="My Order"/>
                </form>
            </div>

        <%
            }
        %>

    </body>
</html>
