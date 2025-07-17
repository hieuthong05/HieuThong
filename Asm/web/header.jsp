<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.DTO.UserDTO, utils.AuthUtils" %>
<%
    UserDTO user = AuthUtils.isLoggedIn(request)
                   ? AuthUtils.getCurrentUser(request)
                   : null;
%>

<header>
    <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/header.css">
    
  <div>
    <h2>
      <a href="${pageContext.request.contextPath}/MainController?action=displayProducts"
         style="color: inherit; text-decoration: none;">
        FPT Shop
      </a>
      
      <a href="${pageContext.request.contextPath}/CartController?action=viewCart"
         style="margin-left:20px; color: inherit; text-decoration: none;">
        <i class="fas fa-shopping-cart"></i>
      </a>
    </h2>
  </div>
  <nav>
    <% if (user == null) { %>
      <a href="${pageContext.request.contextPath}/registerForm.jsp">Đăng ký</a>
      <a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
    <% } else { %>
        <%
            if (AuthUtils.isAdmin(request))
            {
            %> <form action="${pageContext.request.contextPath}/MainController"
                    method="post" style="display:inline;">
                    <input type="hidden" name="action" value="manageUser"/>
                    <input type="submit" value="Manage User"/>
              </form> <%
            }
        %>

      <span class="welcome">Xin chào, <%= user.getName() %></span>
      
      <!-- profile dropdown -->
      <div class="dropdown">
          <button class="dropbtn" title="Account"><i class="fa-solid fa-circle-user"></i></button>
        <div class="dropdown-content">
        <form action="profile.jsp" method="post">
             <button type="submit">Your Profile</button>
        </form>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="editProfile"/>
            <input type="hidden" name="curUserName" value="<%= user.getUserName()%>"/>
            <button type="submit">
              Edit Profile
            </button>
        </form>
            <!-- logout -->
        <form action="${pageContext.request.contextPath}/MainController" method="post">
          <input type="hidden" name="action" value="logout"/>
          <button type="submit">Logout</button>
        </form>
          <button form="profileForm" name="action" value="DeleteAccount">
            Xóa tài khoản
          </button>
        </div>
      </div>
      <!-- My Orders -->
      <form action="${pageContext.request.contextPath}/MainController"
            method="get" style="display:inline;">
        <input type="hidden" name="action" value="MyOrder"/>
        <input type="submit" value="My Order"/>
      </form>
    <% } %>
  </nav>
</header>


<%
    String cartMsg = (String) session.getAttribute("cartMessage");
    if (cartMsg != null) {     
%>
    <div id="toast"
         style="position:fixed;bottom:30px;right:30px;
                background:#4caf50;color:#fff;padding:12px 20px;
                border-radius:6px;box-shadow:0 2px 8px rgba(0,0,0,.2);
                z-index:9999;animation:fadein .4s, fadeout .4s 2.6s;">
        <%= cartMsg %>
    </div>
    <script>

      setTimeout(()=> document.getElementById('toast').style.display='none', 3000);
    </script>
<%
      session.removeAttribute("cartMessage");   
    }
%>