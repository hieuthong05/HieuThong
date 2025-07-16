<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.DTO.UserDTO, utils.AuthUtils" %>
<%
    // Lấy user hiện tại (nếu đã login)
    UserDTO user = AuthUtils.isLoggedIn(request)
                   ? AuthUtils.getCurrentUser(request)
                   : null;
%>

<!-- HEADER TOPBAR -->
<header>
  <div><h2>
    <!-- Link về trang chủ -->
    <a href="${pageContext.request.contextPath}/MainController?action=displayProducts"
       style="color: inherit; text-decoration: none;">
      FPT Shop
    </a>
  </h2></div>
  <nav>
    <% if (user == null) { %>
      <a href="${pageContext.request.contextPath}/registerForm.jsp">Đăng ký</a>
      <a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
    <% } else { %>
      <span class="welcome">Xin chào, <%= user.getName() %></span>
     <%
         if (AuthUtils.isAdmin(request))
         {
        %>  <form action="${pageContext.request.contextPath}/MainController"
              method="post" style="display:inline;">
                 <input type="hidden" name="action" value="manageUser"/>
                 <input type="submit" value="User List"/>
            </form> <%    
         }
     %>

      <!-- Dropdown profile -->
      <div class="dropdown">
        <button class="dropbtn"><i class="fa-solid fa-circle-user"></i></button>
        <div class="dropdown-content">
            <form action="profile.jsp" method="post">
                <button type="submit">Your Profile</button>
            </form>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="editProfile"/>
                <input type="hidden" name="curUserName" value="<%= user.getUserName()%>"/>
                <button type="submit">Update Profile</button>
            </form>
             <!-- Logout form -->
            <form action="${pageContext.request.contextPath}/MainController"
                  method="post" style="display:inline;">
              <input type="hidden" name="action" value="logout"/>
              <input type="submit" value="Đăng xuất"/>
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

