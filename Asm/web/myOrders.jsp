<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.DTO.OrderDTO" %>
<%@ page import="model.DTO.PaymentDTO" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Orders</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css"/>
  <!-- nếu cần style riêng cho orders -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/orders.css"/>
</head>
<body>
  <!-- header giống home.jsp -->
  <jsp:include page="header.jsp"/>

  <div class="main-layout">
    <!-- nếu có sidebar, include ở đây -->
    <jsp:include page="sidebar.jsp"/>

    <div class="container">
      <h3 class="section-title"><i class="fa-solid fa-folder-open"></i> My Orders</h3>

      <%
        List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
        if (orders == null || orders.isEmpty()) {
      %>
        <p>Bạn chưa có đơn hàng nào.</p>
      <%
        } else {
      %>
        <table class="table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Order Date</th>
              <th>Expected Delivery</th>
              <th>Status</th>
              <th>Payment</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <%
              for (OrderDTO o : orders) {
                PaymentDTO pay = o.getPayment().orElse(null);
            %>
            <tr>
              <td><%= o.getOrderId() %></td>
              <td><%= o.getOrderDate() %></td>
              <td><%= o.getExpectedDeliveryDate() %></td>
              <td><%= o.getStatus() %></td>
              <td>
                <%= (pay!=null) ? pay.getMethod() : "<span style='color:red;'>Unpaid</span>" %>
              </td>
              <td>
                <form action="OrderController" method="get" style="display:inline;">
                  <input type="hidden" name="action" value="viewOrder"/>
                  <input type="hidden" name="orderId" value="<%= o.getOrderId() %>"/>
                  <button type="submit" class="btn-buy-full">Details</button>
                </form>
                <%
                  if (pay==null && "pending".equals(o.getStatus())) {
                %>
                <form action="OrderController" method="get" style="display:inline;">
                  <input type="hidden" name="action" value="viewOrder"/>
                  <input type="hidden" name="orderId" value="<%= o.getOrderId() %>"/>
                  <button type="submit" class="btn-cart" title="Pay Now"><i class="fas fa-credit-card"></i></button>
                </form>
                <%
                  }
                %>
              </td>
            </tr>
            <%
              }
            %>
          </tbody>
        </table>
      <%
        }
      %>
    </div>
  </div>

  <jsp:include page="footer.jsp"/>
</body>
</html>
