package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.DAO.OrderDAO;
import model.DAO.OrderItemDAO;
import model.DTO.OrderDTO;
import model.DTO.OrderItemDTO;
import model.DTO.UserDTO;
import utils.AuthUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name="OrderController", urlPatterns={"/OrderController"})
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Trung tâm điều phối: phân quyền, bắt action, chuyển tiếp hoặc redirect
     */
    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1. Kiểm tra đã login chưa
        UserDTO currentUser = AuthUtils.getCurrentUser(req);
        if (currentUser == null) {
            // chưa login → ép về trang login
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            if ("checkout".equals(action)) {
                // tạo order mới từ giỏ hàng
                handleCheckout(req, resp, currentUser);
                return; // handleCheckout đã redirect
            }
            String url;
            if ("viewOrder".equals(action)) {
                url = handleViewOrder(req, resp, currentUser);
                if (url == null) {
                    // đã forward bên trong handleViewOrder (ví dụ lỗi permission)
                    return;
                }
            } else if ("MyOrder".equals(action)) {
                url = handleMyOrder(req, currentUser);
            } else {
                req.setAttribute("message", "Unknown action: " + action);
                url = "home.jsp";
            }
            // Kết thúc: forward về JSP
            req.getRequestDispatcher(url).forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Order processing error: " + e.getMessage(), e);
        }
    }

    /**
     * Chuyển toàn bộ items trong session cart thành một đơn mới:
     *  1) tạo Order
     *  2) insert từng OrderItem
     *  3) remove cart khỏi session
     *  4) redirect về xem chi tiết order mới
     */
    @SuppressWarnings("unchecked")
    private void handleCheckout(HttpServletRequest req,
                                HttpServletResponse resp,
                                UserDTO currentUser) throws Exception {
        HttpSession session = req.getSession();
        List<OrderItemDTO> cart = (List<OrderItemDTO>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            // cart trống → quay về home có message
            session.setAttribute("message", "Your cart is empty.");
            resp.sendRedirect(req.getContextPath() + "/home.jsp");
            return;
        }

        // 1. Tạo order mới
        OrderDAO orderDao = new OrderDAO();
        int orderId = orderDao.createNewOrder(currentUser.getUserId());

        // 2. Lưu từng item
        OrderItemDAO itemDao = new OrderItemDAO();
        for (OrderItemDTO oi : cart) {
            oi.setOrderId(orderId);
            itemDao.insert(oi);
        }

        // 3. Xóa cart
        session.removeAttribute("cart");

        // 4. Redirect sang xem chi tiết đơn
        resp.sendRedirect(req.getContextPath()
                + "/OrderController?action=viewOrder&orderId=" + orderId);
    }

    private String handleViewOrder(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   UserDTO currentUser)
            throws Exception {
        // parse
        String idParam = req.getParameter("orderId");
        int orderId;
        try {
            orderId = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            // orderId không hợp lệ
            req.setAttribute("errorMessage", "Invalid orderId: " + idParam);
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return null;
        }

        // load order
        OrderDAO orderDao = new OrderDAO();
        Optional<OrderDTO> optOrder = orderDao.findById(orderId);
        if (!optOrder.isPresent()) {
            req.setAttribute("errorMessage", "Order not found: " + orderId);
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return null;
        }
        OrderDTO order = optOrder.get();

        // phân quyền
        boolean isOwner = order.getUserId().equals(currentUser.getUserId());
        boolean isAdmin = AuthUtils.isAdmin(req);
        if (!isOwner && !isAdmin) {
            // không có quyền xem
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                           "You do not have permission to view this order");
            return null;
        }

        // ready dữ liệu cho view
        req.setAttribute("order", order);
        return "orderDetails.jsp";
    }

    /**
     * Hiển thị danh sách các đơn của user đang login
     */
    private String handleMyOrder(HttpServletRequest req,
                                 UserDTO currentUser) throws Exception {
        OrderDAO orderDao = new OrderDAO();
        List<OrderDTO> orders = orderDao.findByUser(currentUser.getUserId());
        req.setAttribute("orders", orders);
        return "myOrders.jsp";
    }
}
