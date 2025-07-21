<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.DTO.OrderItemDTO,model.DTO.ProductDTO,model.DAO.ProductDAO"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>

    <!-- CSS chung -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css">

    <!-- CSS riêng -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<%
    List<OrderItemDTO> cart = (List<OrderItemDTO>) request.getAttribute("cart");
    ProductDAO         dao  = new ProductDAO();                // lấy tên + hình
%>

<section class="cart-container">

    <h3 class="cart-title"><i class="fas fa-shopping-cart"></i> Giỏ hàng</h3>

    <!-- THÔNG BÁO THÊM VÀO GIỎ -->
    <%
        String msg = (String) session.getAttribute("cartMessage");
        if (msg != null) {
    %>
        <div class="alert success"><i class="fas fa-check-circle"></i> <%= msg %></div>
    <%
            session.removeAttribute("cartMessage");            // chỉ hiển thị 1 lần
        }
    %>

    <!-- GIỎ HÀNG TRỐNG -->
    <% if (cart == null || cart.isEmpty()) { %>

        <p class="cart-empty">Giỏ hàng trống.</p>
        <a class="btn-continue"
           href="${pageContext.request.contextPath}/MainController?action=displayProducts">
            Tiếp tục mua sắm
        </a>

    <% } else {                                                             %>

    <!-- BẢNG GIỎ HÀNG -->
    <table class="cart-table">
        <thead>
        <tr>
            <th>#</th>
            <th>Ảnh</th>
            <th>Tên&nbsp;sản&nbsp;phẩm</th>
            <th class="qty-col">SL</th>
            <th>Đơn&nbsp;giá</th>
            <th>Thành&nbsp;tiền</th>
            <th>Thao&nbsp;tác</th>
        </tr>
        </thead>
        <tbody>
        <%
            double total = 0;
            for (int i = 0; i < cart.size(); i++) {
                OrderItemDTO item = cart.get(i);
                ProductDTO   p    = dao.getProductById(item.getProductId());
                double       sub  = item.getQuantity() * item.getUnitPrice();
                total += sub;
        %>
        <tr>
            <td><%= i + 1 %></td>
            <td><img class="thumb" src="<%= p.getImageUrl() %>" alt=""></td>
            <td class="name-col"><%= p.getName() %></td>

            <!-- CỘT SỐ LƯỢNG VỚI NÚT +/- -->
            <td>
                <form action="CartController" method="post" class="qty-form">
                    <input type="hidden" name="action" value="updateQuantity">
                    <input type="hidden" name="index"  value="<%= i %>">

                    <button type="submit" name="delta" value="-1" class="btn-qty">−</button>
                    <span  class="qty-display"><%= item.getQuantity() %></span>
                    <button type="submit" name="delta" value="1"  class="btn-qty">+</button>
                </form>
            </td>

            <td><%= String.format("%,.0f đ", item.getUnitPrice()) %></td>
            <td><%= String.format("%,.0f đ", sub) %></td>
            <td>
                <form action="CartController" method="post">
                    <input type="hidden" name="action" value="removeFromCart">
                    <input type="hidden" name="index"  value="<%= i %>">
                    <button type="submit" class="btn-remove">Xóa</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
        <tfoot>
        <tr>
            <th colspan="5" class="text-right">Tổng cộng</th>
            <th colspan="2"><%= String.format("%,.0f đ", total) %></th>
        </tr>
        </tfoot>
    </table>

    <!-- NÚT THANH TOÁN -->
    <div class="cart-actions">
        <a class="btn-checkout"
           href="${pageContext.request.contextPath}/OrderController?action=checkout">
            Thanh toán
        </a>
    </div>

    <% } /* END else */ %>
</section>

<jsp:include page="footer.jsp"/>
</body>
</html>
