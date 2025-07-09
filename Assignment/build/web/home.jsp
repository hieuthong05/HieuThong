<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.UserDTO"%>
<%@page import="model.DTO.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page import="utils.AuthUtils"%>

<%
    UserDTO user = null;
    if (AuthUtils.isLoggedIn(request)) {
        user = AuthUtils.getCurrentUser(request);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FPT Shop - Trang chủ</title>   
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- CSS Home Page -->    
    <link rel="stylesheet" href="assets/css/header.css">
    <link rel="stylesheet" href="assets/css/body.css">
    <link rel="stylesheet" href="assets/css/footer.css">

</head>
<body>

<header>
    <div><h2>FPT Shop</h2></div>
    <nav>
        <% if (user == null) { %>
            <a href="signup.jsp">Đăng ký</a>
            <a href="login.jsp">Đăng nhập</a>
        <% } else { %>
            <span class="welcome">Xin chào, <%= user.getName() %></span>
            <form action="MainController" method="post" style="display:inline;">
                <input type="hidden" name="action" value="Logout">
                <input type="submit" value="Đăng xuất">
            </form>
            <form method="post" action="MainController">
                <div class="dropdown">
                    <button class="dropbtn"><%= user.getName() %></button>
                    <div class="dropdown-content">
                        <button type="submit" name="action" value="DeleteAccount">Delete Account</button>
                        <button type="submit" name="action" value="UpdateProfile">Update Profile</button>
                        <button type="submit" name="action" value="Logout">Logout</button>
                    </div>
                </div>
            </form>
            <div>
                <form action="MainController" method="get">
                    <input type="hidden" name="action" value="MyOrder">
                    <input type="submit" value="My Order"/>
                </form>
            </div>
        <% } %>
    </nav>
</header>

<!-- Search bar -->
<div class="search-bar">
    <form action="MainController" method="get">
        <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm...">
        <input type="hidden" name="action" value="Search">
        <input type="submit" value="Tìm kiếm">
    </form>
</div>

<!-- Category Filter -->
<div class="category-bar">
    <form action="MainController" method="get">
        <input type="hidden" name="action" value="GetProductByCategory">
        <input type="hidden" name="categoryId" value="1">
        <input type="submit" value="Laptop">
    </form>
    <form action="MainController" method="get">
        <input type="hidden" name="action" value="GetProductByCategory">
        <input type="hidden" name="categoryId" value="2">
        <input type="submit" value="Smartphone">
    </form>
    <form action="MainController" method="get">
        <input type="hidden" name="action" value="GetProductByCategory">
        <input type="hidden" name="categoryId" value="3">
        <input type="submit" value="Tablet">
    </form>
</div>

<!-- Product Grid -->
<div class="container">
    <h3>Sản phẩm nổi bật</h3>
    <div class="product-grid">
        <%
            List<ProductDTO> products = (List<ProductDTO>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
                for (ProductDTO p : products) {
        %>
        <div class="product-card">
            <h3><%= p.getName() %></h3>
            <p><strong>Giá:</strong> <%= String.format("%,.0f", p.getPrice()) %> đ</p>
            <p><strong>Tồn kho:</strong> <%= p.getStock() %></p>
            <form action="MainController" method="get">
                <input type="hidden" name="action" value="ViewProductDetails">
                <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                <input type="submit" value="Xem chi tiết">
            </form>
        </div>
        <%
                }
            } else {
        %>
        <p>Không có sản phẩm nào để hiển thị.</p>
        <%
            }
        %>
    </div>
</div>

</body>

<footer>
    <p>&copy; 2025 FPT Shop. All rights reserved.</p>
</footer>

</html>
