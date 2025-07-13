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
      <!-- Logout form -->
      <form action="${pageContext.request.contextPath}/MainController"
            method="post" style="display:inline;">
        <input type="hidden" name="action" value="logout"/>
        <input type="submit" value="Đăng xuất"/>
      </form>

      <!-- Dropdown profile -->
      <div class="dropdown">
        <button class="dropbtn"><%= user.getName() %></button>
        <div class="dropdown-content">
          <button form="profileForm" name="action" value="UpdateProfile">
            Cập nhật thông tin
          </button>
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

<!-- SEARCH BAR -->
<div class="search-bar">
  <form action="${pageContext.request.contextPath}/ProductController"
        method="get" class="search-form">
    <input type="hidden" name="action" value="search"/>
    <div class="search-input-wrapper">
      <i class="fas fa-search"></i>
      <input type="text"
             name="keyword"
             placeholder="Tìm kiếm sản phẩm..."
             value="<%= request.getAttribute("keyword") != null 
                       ? request.getAttribute("keyword") 
                       : "" %>"
             required/>
    </div>
  </form>
</div>
