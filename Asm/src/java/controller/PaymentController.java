package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.DAO.PaymentDAO;
import model.DTO.PaymentDTO;
import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

@WebServlet(name="PaymentController", urlPatterns={"/PaymentController"})
public class PaymentController extends HttpServlet {
  
  // Xử lý GET
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    process(req, resp);
  }
  
  // Xử lý POST
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    process(req, resp);
  }
  
  private void process(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter("action");
    String url = "home.jsp";
    try {
      if ("viewPayments".equals(action)) {
        url = handleViewPayments(req);               // xem thông tin thanh toán
      } else if ("processPayment".equals(action)) {
        url = handleProcessPayment(req, resp);       // thanh toán (ghi vào DB)
        return;                                      // vì chúng ta redirect ở trong
      } else if ("refundPayment".equals(action)) {
        url = handleRefundPayment(req, resp);        // hoàn tiền (xóa hoặc cập nhật)
        return;                                      // redirect luôn để tránh double-submit
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }
    req.getRequestDispatcher(url).forward(req, resp);
  }
  
  /**
   * Hiển thị thông tin Payment cho một order cụ thể.
   * URL mẫu: /PaymentController?action=viewPayments&orderId=123
   */
  private String handleViewPayments(HttpServletRequest req) throws Exception {
    int orderId = Integer.parseInt(req.getParameter("orderId"));
    PaymentDAO dao = new PaymentDAO();
    Optional<PaymentDTO> opt = dao.findByOrder(orderId);
    if (opt.isPresent()) {
      req.setAttribute("payment", opt.get());
    } else {
      req.setAttribute("message", "No payment found for order " + orderId);
    }
    return "paymentDetails.jsp";  // một JSP bạn tự tạo để hiển thị chi tiết
  }
  
  /**
   * Thực hiện thanh toán: ghi bản ghi mới vào bảng Payment.
   * URL mẫu (POST): /PaymentController?action=processPayment
   * với các param: orderId, amount, method, paidAt (định dạng yyyy-MM-dd)
   */
  private String handleProcessPayment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    int orderId   = Integer.parseInt(req.getParameter("orderId"));
    double amount = Double.parseDouble(req.getParameter("amount"));
    String method = req.getParameter("method");
    // nếu không truyền paidAt thì dùng ngày hôm nay
    Date paidAt = req.getParameter("paidAt") != null
      ? Date.valueOf(req.getParameter("paidAt"))
      : new Date(System.currentTimeMillis());
    
    PaymentDTO p = new PaymentDTO();
    p.setOrderId(orderId);
    p.setAmount(amount);
    p.setMethod(method);
    p.setPaidAt(paidAt);
    
    PaymentDAO dao = new PaymentDAO();
    dao.insert(p);
    
    // sau khi insert xong, redirect về trang order của user để tránh double-submit
    resp.sendRedirect(req.getContextPath() + "/OrderController?action=MyOrder");
    return null;  // vì đã redirect rồi
  }
  
  /**
   * Hoàn tiền: xóa bản ghi thanh toán (hoặc bạn có thể update cột status tuỳ requirement).
   * URL mẫu: /PaymentController?action=refundPayment&paymentId=456
   */
  private String handleRefundPayment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    int paymentId = Integer.parseInt(req.getParameter("paymentId"));
    PaymentDAO dao = new PaymentDAO();
    dao.delete(paymentId);
    
    // sau khi xóa, redirect về trang quản lý hoặc danh sách thanh toán
    resp.sendRedirect(req.getContextPath() + "/PaymentController?action=viewPayments&orderId=" 
                      + req.getParameter("orderId"));
    return null;  // đã redirect
  }
}
