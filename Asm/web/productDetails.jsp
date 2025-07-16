<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.ProductDTO" %>
<%@page import="model.DTO.UserDTO" %>
<%@page import="java.text.DecimalFormatSymbols" %>
<%@page import="java.text.DecimalFormat" %>
<%@page import="java.util.List" %>
<%@page import="model.DTO.ReviewDTO" %>
<%
    
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator('.');
    DecimalFormat vnFormatter = new DecimalFormat("#,###", symbols);
    
    ProductDTO product = (ProductDTO) request.getAttribute("product");
    UserDTO user = (UserDTO) session.getAttribute("user");
    List<ReviewDTO> list = (List<ReviewDTO>) request.getAttribute("list");
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


    <jsp:include page="header.jsp"/>


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
        <div class="product-comment">
            <table>
                <thead>
                    <tr>
                        <th>User Id</th>
                        <th>Rate</th>
                        <th>Comment</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for(ReviewDTO u:list)
                        {
                    %><tr>
                        <td><%= u.getUserId()%></td>
                        <td><%= u.getRating()%></td>
                        <td><%= u.getComment()%></td>
                        <td><%= u.getCreatedAt()%></td>
                      </tr><%
                        }
                    %>
                </tbody>
               </table>
        </div>   
    </div>

<% } else { %>
    <p style="text-align:center; margin-top:50px;">Product not found.</p>
<% } %>
 <jsp:include page="footer.jsp"/>

</body>
</html>
