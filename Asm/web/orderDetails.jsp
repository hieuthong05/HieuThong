<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.DTO.OrderDTO" %>
<%@ page import="model.DTO.OrderItemDTO" %>
<%@ page import="model.DTO.PaymentDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Order Details</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/orders.css"/>
</head>
<body>
  <jsp:include page="header.jsp"/>

  <div class="container">
    <h3 class="section-title"><i class="fa-solid fa-receipt"></i> Order #<%= ((OrderDTO)request.getAttribute("order")).getOrderId() %></h3>

    <%
      OrderDTO order = (OrderDTO) request.getAttribute("order");
      // Items
      List<OrderItemDTO> items = order.getItems();
    %>
    <h4>Items</h4>
    <table class="table">
      <thead>
        <tr>
          <th>Product ID</th>
          <th>Quantity</th>
          <th>Unit Price</th>
          <th>Subtotal</th>
        </tr>
      </thead>
      <tbody>
        <%
          double total = 0;
          for (OrderItemDTO oi : items) {
            double sub = oi.getQuantity() * oi.getUnitPrice();
            total += sub;
        %>
        <tr>
          <td><%= oi.getProductId() %></td>
          <td><%= oi.getQuantity() %></td>
          <td><%= String.format("%,.0f", oi.getUnitPrice()) %> đ</td>
          <td><%= String.format("%,.0f", sub) %> đ</td>
        </tr>
        <%
          }
        %>
      </tbody>
      <tfoot>
        <tr>
          <th colspan="3">Total</th>
          <th><%= String.format("%,.0f", total) %> đ</th>
        </tr>
      </tfoot>
    </table>

    <%
      PaymentDTO pay = order.getPayment().orElse(null);
      if (pay != null) {
    %>
      <h4>Payment</h4>
      <p>Method: <strong><%= pay.getMethod() %></strong></p>
      <p>Amount: <strong><%= String.format("%,.0f", pay.getAmount()) %> đ</strong></p>
      <p>Date: <strong><%= pay.getPaidAt() %></strong></p>
    <%
      } else {
    %>
      <h4 style="color:red;">This order has not been paid yet.</h4>
      <form action="PaymentController" method="post">
        <input type="hidden" name="action" value="processPayment"/>
        <input type="hidden" name="orderId" value="<%= order.getOrderId() %>"/>
        <label>Amount: </label>
        <input type="text" name="amount" value="<%= total %>" readonly/>
        <label>Method: </label>
        <select name="method">
          <option value="credit">Credit Card</option>
          <option value="bank">Bank Transfer</option>
          <option value="cod">Cash on Delivery</option>
        </select>
        <button type="submit" class="btn-buy-full">Pay Now</button>
      </form>
    <%
      }
    %>
  </div>

  <jsp:include page="footer.jsp"/>
</body>
</html>
