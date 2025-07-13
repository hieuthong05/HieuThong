<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.DTO.OrderItemDTO, model.DAO.ProductDAO, model.DTO.ProductDTO" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Giỏ hàng của bạn</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css"/>
  <style>
    /* CSS nhanh cho bảng cart */
    .cart-container { max-width:800px; margin:40px auto; background:#fff; padding:20px; border-radius:8px; box-shadow:0 2px 8px rgba(0,0,0,0.1); }
    table { width:100%; border-collapse:collapse; }
    th,td { padding:10px; border-bottom:1px solid #eee; text-align:center; }
    .actions a, .actions button { margin:0 4px; }
    .total-row th { text-align:right; }
  </style>
</head>
<body>
  <jsp:include page="header.jsp"/>

  <div class="cart-container">
    <h2>Giỏ hàng</h2>
    <%
      List<OrderItemDTO> cart = (List<OrderItemDTO>) session.getAttribute("cart");
      if (cart==null || cart.isEmpty()) {
    %>
      <p>Chưa có sản phẩm nào trong giỏ.</p>
    <% } else {
        double grandTotal = 0;
    %>
      <form action="MainController" method="post">
        <input type="hidden" name="action" value="updateCart"/>
        <table>
          <thead>
            <tr>
              <th>Sản phẩm</th>
              <th>Đơn giá</th>
              <th>Số lượng</th>
              <th>Thành tiền</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            <%
              for (OrderItemDTO item : cart) {
                ProductDTO p = new ProductDAO().getProductById(item.getProductId());
                double sub = item.getQuantity() * item.getUnitPrice();
                grandTotal += sub;
            %>
            <tr>
              <td><%= p.getName() %></td>
              <td><%= String.format("%,.0f", item.getUnitPrice()) %> đ</td>
              <td>
                <input type="number" name="qty_<%=p.getProductId()%>" value="<%=item.getQuantity()%>" min="1" style="width:60px"/>
              </td>
              <td><%= String.format("%,.0f", sub) %> đ</td>
              <td class="actions">
                <a href="MainController?action=removeCart&productId=<%=p.getProductId()%>">Xóa</a>
              </td>
            </tr>
            <% } %>
          </tbody>
          <tfoot>
            <tr class="total-row">
              <th colspan="3">Tổng cộng:</th>
              <th colspan="2"><%= String.format("%,.0f", grandTotal) %> đ</th>
            </tr>
          </tfoot>
        </table>
        <button type="submit">Cập nhật giỏ hàng</button>
      </form>

      <form action="OrderController" method="get" style="margin-top:20px;">
        <input type="hidden" name="action" value="checkout"/>
        <button type="submit">Thanh toán</button>
      </form>
    <% } %>
  </div>

  <jsp:include page="footer.jsp"/>
</body>
</html>
