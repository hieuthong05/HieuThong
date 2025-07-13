<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.ProductDTO" %>
<%@page import="model.DTO.UserDTO" %>
<%@page import="java.text.DecimalFormatSymbols" %>
<%@page import="java.text.DecimalFormat" %>
<%
    
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator('.');
    DecimalFormat vnFormatter = new DecimalFormat("#,###", symbols);
    
    ProductDTO product = (ProductDTO) request.getAttribute("product");
    UserDTO user = (UserDTO) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"> 
    <link rel="stylesheet" href="${pageContext.request.contextPath}//assets/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}//assets/css/productDetails.css">
</head>
<body>


    <header>
        <div><h2>FPT Shop</h2></div>
        <nav>
            <% if (user == null) { %>
                <a href="registerForm.jsp">Đăng ký</a>
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
    <form action="MainController" method="get" class="search-form">
        <input type="hidden" name="action" value="Search">
        <div class="search-input-wrapper">
            <i class="fas fa-search"></i>
            <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm...">
        </div>
    </form>
</div>

<% if (product != null) { %>
    <div class="product-details">
        <img src="<%= product.getImageUrl() %>" alt="Product Image" />

        <div class="product-info">
            <h2><%= product.getName() %></h2>
            <p class="price">Giá: <%= vnFormatter.format(product.getPrice()) %>đ</p>
            <p class="stock">In Stock: <%= product.getStock() %></p>
            <p><strong>Description:</strong> <%= product.getDescription() %></p>

            <!-- Buttons nằm bên trong product-info -->
            <div class="product-actions-center">
                <form action="MainController" method="post" class="add-to-cart-form">
                    <input type="hidden" name="action" value="AddToCart">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button type="submit" class="btn-cart" title="Thêm vào giỏ hàng">
                        <i class="fas fa-shopping-cart"></i>
                    </button>
                </form>

                <form action="MainController" method="post" class="buy-now-form">
                    <input type="hidden" name="action" value="BuyNow">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button type="submit" class="btn-buy-full">
                        <i class="fas fa-credit-card"></i> Mua ngay
                    </button>
                </form>
            </div>
        </div>
    </div>

<% } else { %>
    <p style="text-align:center; margin-top:50px;">Product not found.</p>
<% } %>
 <jsp:include page="footer.jsp"/>
</body>
</html>
