<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.DTO.*, java.util.*" %>
<%
    OrderDTO order = (OrderDTO) request.getAttribute("order");
    List<OrderItemDTO> items = order.getItems();

    double total = 0;
    for (OrderItemDTO oi : items) {
        total += oi.getQuantity() * oi.getUnitPrice();
    }

    boolean unpaid = !order.getPayment().isPresent();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order #<%= order.getOrderId() %></title>

    <!-- CSS chung -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css">

    <!-- CSS riêng cho Order -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/orderDetails.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<main class="order-container">

    <h2 class="order-heading">
        <i class="fa-solid fa-receipt"></i>&nbsp;Order&nbsp;#<%= order.getOrderId() %>
    </h2>

    <!-- ====== bảng Items ====== -->
    <table class="order-table">
        <thead>
        <tr>
            <th>Product&nbsp;ID</th>
            <th>Qty</th>
            <th>Unit&nbsp;Price</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <% for (OrderItemDTO oi : items) { %>
            <tr>
                <td><%= oi.getProductId() %></td>
                <td><%= oi.getQuantity() %></td>
                <td><%= String.format("%,.0f đ", oi.getUnitPrice()) %></td>
                <td><%= String.format("%,.0f đ", oi.getQuantity() * oi.getUnitPrice()) %></td>
            </tr>
        <% } %>
        </tbody>
        <tfoot>
        <tr>
            <th colspan="3">Total</th>
            <th><%= String.format("%,.0f đ", total) %></th>
        </tr>
        </tfoot>
    </table>

    <!-- ====== Payment ====== -->
    <% if (unpaid) { %>
        <div class="pay-wrapper">
            <p class="note warning">
                <i class="fa-solid fa-circle-exclamation"></i>
                This order has not been paid yet.
            </p>

            <form class="pay-form" action="PaymentController" method="post">
                <input type="hidden" name="action" value="processPayment">
                <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">

                <label>
                    Amount
                    <input type="text" name="amount"
                           value='<%= String.format("%,.0f", total) %>' readonly />
                </label>

                <label>
                    Method
                    <select name="method">
                        <option value="credit">Credit Card</option>
                        <option value="bank">Bank Transfer</option>
                        <option value="cod">Cash on Delivery</option>
                    </select>
                </label>

                <button type="submit" class="btn-pay">
                    <i class="fa-solid fa-credit-card"></i>&nbsp;Pay&nbsp;Now
                </button>
            </form>
        </div>

    <% } else { 
           PaymentDTO p = order.getPayment().get(); %>

        <div class="pay-wrapper paid">
            <p class="note success">
                <i class="fa-solid fa-circle-check"></i>
                Paid&nbsp;<strong><%= String.format("%,.0f đ", p.getAmount()) %></strong>
                via&nbsp;<%= p.getMethod() %> on <%= p.getPaidAt() %>
            </p>
        </div>

    <% } %>

</main>

<jsp:include page="footer.jsp"/>

</body>
</html>
