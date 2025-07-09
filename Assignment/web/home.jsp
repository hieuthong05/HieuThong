<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.UserDTO"%>
<%@page import="model.DTO.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page import="utils.AuthUtils"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FPT Shop - Trang chủ</title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f8f8;
        }

        header {
            background-color: #2d2d2d;
            padding: 10px 20px;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        header h2 {
            margin: 0;
            font-size: 24px;
            color: #f33;
        }

        nav a, nav form {
            margin-left: 15px;
            color: white;
            text-decoration: none;
            font-weight: 500;
            display: inline-block;
        }

        nav form {
            display: inline;
        }

        nav input[type="submit"] {
            background: #f33;
            color: white;
            border: none;
            padding: 5px 10px;
            font-weight: bold;
            cursor: pointer;
        }

        .search-bar {
            background-color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            border-bottom: 1px solid #ccc;
        }

        .search-bar input[type="text"] {
            width: 350px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px 0 0 4px;
        }

        .search-bar input[type="submit"] {
            padding: 9px 15px;
            border: none;
            background-color: #f33;
            color: white;
            border-radius: 0 4px 4px 0;
            font-weight: bold;
            cursor: pointer;
        }

        .category-bar {
            background-color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: center;
            border-bottom: 1px solid #ccc;
        }

        .category-bar form {
            margin: 0 5px;
        }

        .category-bar input[type="submit"] {
            background-color: #eee;
            border: 1px solid #ccc;
            padding: 6px 14px;
            cursor: pointer;
            border-radius: 4px;
        }

        .container {
            padding: 20px;
        }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
            gap: 20px;
        }

        .product-card {
            background-color: white;
            border: 1px solid #ddd;
            padding: 15px;
            text-align: center;
            border-radius: 6px;
            transition: box-shadow 0.3s ease;
        }

        .product-card:hover {
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .product-card h3 {
            font-size: 16px;
            margin: 10px 0;
            color: #333;
        }

        .product-card p {
            margin: 6px 0;
            font-size: 14px;
        }

        .product-card input[type="submit"] {
            margin-top: 10px;
            padding: 6px 12px;
            background-color: #f33;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }

        .welcome {
            color: white;
            font-weight: bold;
        }

    </style>
</head>
<body>

<header>
    <div><h2>FPT Shop</h2></div>
    <nav>
        <% if (!AuthUtils.isLoggedIn(request)) { %>
            <a href="signup.jsp">Đăng ký</a>
            <a href="login.jsp">Đăng nhập</a>
        <% } else {
            UserDTO user = AuthUtils.getCurrentUser(request);
        %>
<<<<<<< HEAD
            <span class="welcome">Xin chào, <%= user.getName() %></span>
            <form action="MainController" method="post" style="display:inline;">
                <input type="hidden" name="action" value="Logout">
                <input type="submit" value="Đăng xuất">
            </form>
        <% } %>
    </nav>
</header>
=======
               <form method="post" action="MainController">
                <div class="dropdown">
                  <button class="dropbtn"><%=user.getName()%></button>
                  <div class="dropdown-content">
                    <button type="submit" >Delete Account</button>
                    <button type="submit" >Update Profile</button>
                    <button type="submit" name="action" value="logout" >Logout</button>
                  </div>
                </div>
              </form>
                  
            <div>
                <form>
                    <input type="submit" value="My Order"/>
                </form>
            </div>
>>>>>>> f0947c0c6aeb10140ea06fe91e992d3497d6e8ff

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
</html>
