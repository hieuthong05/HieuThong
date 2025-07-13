<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.DTO.OrderItemDTO" %>
<jsp:include page="header.jsp"/>
<div class="container">
  <h3 class="section-title"><i class="fas fa-shopping-cart"></i> Giỏ hàng</h3>
  <%
    List<OrderItemDTO> cart = (List<OrderItemDTO>) request.getAttribute("cart");
    if (cart == null || cart.isEmpty()) {
  %>
    <p>Giỏ hàng trống.</p>
  <% } else { %>
    <table class="table">
      <thead>
        <tr>
          <th>#</th><th>Product ID</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th><th>Thao tác</th>
        </tr>
      </thead>
      <tbody>
        <%
          double total = 0;
          for (int i = 0; i < cart.size(); i++) {
              OrderItemDTO item = cart.get(i);
              double sub = item.getQuantity() * item.getUnitPrice();
              total += sub;
        %>
        <tr>
          <td><%= i+1 %></td>
          <td><%= item.getProductId() %></td>
          <td><%= item.getQuantity() %></td>
          <td><%= String.format("%,.0f", item.getUnitPrice()) %> đ</td>
          <td><%= String.format("%,.0f", sub) %> đ</td>
          <td>
            <form action="CartController" method="post" style="display:inline">
              <input type="hidden" name="action" value="removeFromCart"/>
              <input type="hidden" name="index"  value="<%= i %>"/>
              <button type="submit">Xóa</button>
            </form>
          </td>
        </tr>
        <% } %>
      </tbody>
      <tfoot>
        <tr>
          <th colspan="4">Tổng cộng</th>
          <th colspan="2"><%= String.format("%,.0f", total) %> đ</th>
        </tr>
      </tfoot>
    </table>
  <% } %>
</div>
<jsp:include page="footer.jsp"/>
