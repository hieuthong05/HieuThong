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
        <link rel="stylesheet" href="${pageContext.request.contextPath}//assets/css/review.css">
    </head>
    <body>


        <jsp:include page="header.jsp"/>


        <% if (product != null) { %>
        <div class="product-details">

            <div class="product-info">
                <div class="product-image">
                    <img src="<%= product.getImageUrl() %>" alt="Product Image" />
                </div>

                <div class="product-infomation">
                    <h2><%= product.getName() %></h2>
                    <p class="price">Giá: <%= vnFormatter.format(product.getPrice()) %>đ</p>
                    <p class="stock">In Stock: <%= product.getStock() %></p>
                    <p><strong>Description:</strong> <%= product.getDescription() %></p>

                    <div class="product-actions-center">
                       <form action="${pageContext.request.contextPath}/CartController" method="post"
      class="add-to-cart-form" style="display:inline;">
    <input type="hidden" name="action"    value="addToCart"/>
    <input type="hidden" name="productId" value="<%= product.getProductId() %>"/>
    <!-- thêm dòng này -->
    <input type="hidden" name="unitPrice" value="<%= product.getPrice() %>"/>

    <button type="submit" class="btn-cart" title="Thêm vào giỏ hàng">
        <i class="fas fa-shopping-cart"></i>
    </button>
</form>

    <!-- ===== BUY-NOW: thêm 1 món rồi nhảy thẳng sang Checkout ===== -->
<form action="<%=request.getContextPath()%>/CartController" method="post"
      class="buy-now-form">
    <input type="hidden" name="action"     value="addToCart">
    <input type="hidden" name="productId"  value="<%=product.getProductId()%>">
    <input type="hidden" name="unitPrice"  value="<%=product.getPrice()%>">
    <input type="hidden" name="qty"        value="1">
    <!-- cờ cho controller biết phải đi thẳng qua bước thanh toán -->
    <input type="hidden" name="next"       value="checkout">

    <button type="submit" class="btn-buy-full">
        <i class="fas fa-credit-card"></i> Mua ngay
    </button>
</form>


                        <form action="createReview.jsp" method="post">
                            <input type="hidden" name="productId" value="<%= (product!=null)?product.getProductId():""%>"/>
                            <button type="submit"><i class="fa-solid fa-message"></i> Comment</button>
                        </form>
                    </div> 
                </div>
            </div>
            <div class="product-comment">
                <%
                    if (list == null || list.isEmpty()) {
                %>
                <span style="color: red"><h1>Have No Reviews!</h1></span>
                <%
                    } else {
                %>
                <div class="review-container">
                    <%
                        for (ReviewDTO u : list) {
                    %>
                    <div class="card">
                        <div class="stars">
                            <%
                                int rating = Integer.parseInt(u.getRating()); // chuyển String sang int
                                for (int i = 1; i <= 5; i++) {
                                    String starClass = (i <= rating) ? "star filled" : "star empty";
                            %>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                                 fill="currentColor" class="<%= starClass %>">
                            <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
                            </svg>
                            <%
                                }
                            %>
                        </div>


                        <div class="infos">
                            <p class="date-time">
                                <%= u.getCreatedAt() %>
                            </p>
                            <p class="description">
                                <%= u.getComment() %>
                            </p>
                        </div>

                        <div class="author">
                            — User ID: <%= u.getUserId() %>
                        </div>
                        <div>
                            <form action="createReview.jsp" method="post">
                                <button type="submit"><i class="fa-solid fa-ear-listen"></i> Edit Review</button>
                            </form>
                        </div>
                    </div>
                    <%
                        }
                    %>

                </div>
                <%
                    }
                %>
            </div>   
        </div>

        <% } else { %>
        <p style="text-align:center; margin-top:50px;">Product not found.</p>
        <% } %>
        <jsp:include page="footer.jsp"/>

    </body>
</html>
