<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.DTO.OrderDTO, model.DTO.PaymentDTO"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders</title>

    <!-- CSS dùng chung -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/header.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/body.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/footer.css">

    <!-- CSS riêng cho trang danh sách đơn -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/orders.css">
</head>
<body>

    <!-- ====== HEADER ====== -->
    <jsp:include page="header.jsp"/>

    <!-- ====== MAIN ====== -->
    <main class="orders-container">

        <h3 class="orders-title">
            <i class="fa-solid fa-folder-open"></i>&nbsp;My&nbsp;Orders
        </h3>

        <%
            List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
            if (orders == null || orders.isEmpty()) {
        %>
            <p class="orders-empty">Bạn chưa có đơn hàng nào.</p>
        <%
            } else {
        %>

        <table class="orders-table">
            <thead>
            <tr>
                <th>Order&nbsp;ID</th>
                <th>Order&nbsp;Date</th>
                <th>Expected&nbsp;Delivery</th>
                <th>Status</th>
                <th>Payment</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%  for (OrderDTO o : orders) {
                    PaymentDTO pay = o.getPayment().orElse(null); %>
                <tr>
                    <td><%= o.getOrderId() %></td>
                    <td><%= o.getOrderDate() %></td>
                    <td><%= o.getExpectedDeliveryDate() %></td>
                    <td><%= o.getStatus() %></td>
                    <td>
                        <% if (pay != null) { %>
                            <span class="paid"><%= pay.getMethod() %></span>
                        <% } else { %>
                            <span class="unpaid">Unpaid</span>
                        <% } %>
                    </td>
                    <td class="orders-actions">
                        <!-- nút Details -->
                        <form action="OrderController" method="get">
                            <input type="hidden" name="action"  value="viewOrder">
                            <input type="hidden" name="orderId" value="<%= o.getOrderId() %>">
                            <button type="submit" class="btn-detail">Details</button>
                        </form>

                        <!-- nút Pay now (chỉ hiện nếu chưa thanh toán) -->
                        <% if (pay == null && "pending".equals(o.getStatus())) { %>
                            <form action="OrderController" method="get">
                                <input type="hidden" name="action"  value="viewOrder">
                                <input type="hidden" name="orderId" value="<%= o.getOrderId() %>">
                                <button type="submit" class="btn-payNow"
                                        title="Pay now"><i class="fas fa-credit-card"></i></button>
                            </form>
                        <% } %>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>

        <% } /* end else */ %>

    </main>

    <!-- ====== FOOTER ====== -->
    <jsp:include page="footer.jsp"/>

</body>
</html>
