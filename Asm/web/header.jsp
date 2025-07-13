<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.DTO.UserDTO, utils.AuthUtils" %>
<%
    UserDTO user = AuthUtils.isLoggedIn(request)
                   ? AuthUtils.getCurrentUser(request)
                   : null;
%>

<header>
  <div>
    <h2>
      <a href="${pageContext.request.contextPath}/MainController?action=displayProducts"
         style="color: inherit; text-decoration: none;">
        FPT Shop
      </a>
      <!-- THÊM BIỂU TƯỢNG GIỎ HÀNG LUÔN HIỂN THỊ -->
      <a href="${pageContext.request.contextPath}/CartController?action=viewCart"
         style="margin-left:20px; color: inherit; text-decoration: none;">
        <i class="fas fa-shopping-cart"></i>
        <%-- có thể thêm số lượng: (<%= session.getAttribute("cartSize") %>) --%>
      </a>
    </h2>
  </div>
  <nav>
    <% if (user == null) { %>
      <a href="${pageContext.request.contextPath}/registerForm.jsp">Đăng ký</a>
      <a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
    <% } else { %>
      <span class="welcome">Xin chào, <%= user.getName() %></span>
      <!-- logout -->
      <form action="${pageContext.request.contextPath}/MainController"
            method="post" style="display:inline;">
        <input type="hidden" name="action" value="logout"/>
        <input type="submit" value="Đăng xuất"/>
      </form>
      <!-- profile dropdown -->
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
<%
    String cartMsg = (String) session.getAttribute("cartMessage");
    if (cartMsg != null) {      // có thông báo => in toast
%>
    <div id="toast"
         style="position:fixed;bottom:30px;right:30px;
                background:#4caf50;color:#fff;padding:12px 20px;
                border-radius:6px;box-shadow:0 2px 8px rgba(0,0,0,.2);
                z-index:9999;animation:fadein .4s, fadeout .4s 2.6s;">
        <%= cartMsg %>
    </div>
    <script>
      // Ẩn toast sau 3 giây
      setTimeout(()=> document.getElementById('toast').style.display='none', 3000);
    </script>
<%
      session.removeAttribute("cartMessage");   // xoá để không lặp lại
    }
%>