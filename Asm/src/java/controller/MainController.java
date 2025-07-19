package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// import thêm để xử lý Cart
import model.DAO.ProductDAO;
import model.DTO.ProductDTO;
import model.DTO.OrderItemDTO;

@WebServlet(name="MainController", urlPatterns={"/MainController"})
public class MainController extends HttpServlet {

    // Trang mặc định nếu không có action hợp lệ
    private static final String HOME_PAGE = "home.jsp";

    /*** Xác định các action liên quan đến người dùng ***/
    private boolean isUserAction(String action) {
        return "login".equals(action)
            || "logout".equals(action)
            || "register".equals(action)
            || "editProfile".equals(action)
            || "manageUser".equals(action)
            || "updateProfile".equals(action)
            || "deleteUser".equals(action);
    }

    /*** Xác định các action liên quan đến sản phẩm và danh mục ***/
    private boolean isProductAction(String action) {
        return "displayProducts".equals(action)
            || "viewProductDetails".equals(action)
            || "getProductByCategory".equals(action)
            || "getProductByBrand".equals(action)
            || "search".equals(action)
            || "create".equals(action)
            || "update".equals(action)
            || "delete".equals(action)
            || "displayCategory".equals(action);
    }

    /*** Xác định các action liên quan đến đơn hàng ***/
    private boolean isOrderAction(String action) {
        return "MyOrder".equals(action);
    }

    /*** Xác định các action liên quan đến thanh toán ***/
    private boolean isPaymentAction(String action) {
        return "viewPayments".equals(action)
            || "processPayment".equals(action)
            || "refundPayment".equals(action);
    }

    // Bổ sung: các action của Cart
    private boolean isAddToCart(String action) {
        return "addToCart".equals(action);
    }
    private boolean isBuyNow(String action) {
        return "buyNow".equals(action);
    }
    private boolean isViewCart(String action) {
        return "viewCart".equals(action);
    }
    private boolean isRemoveCart(String action) {
        return "removeCart".equals(action);
    }
    private boolean isUpdateCart(String action) {
        return "updateCart".equals(action);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) Thiết lập charset
        resp.setContentType("text/html;charset=UTF-8");

        // 2) Lấy action, nếu null thì gán chuỗi rỗng
        String action = req.getParameter("action");
        if (action == null) action = "";

        // 3) Mặc định sẽ trả về home
        String url = HOME_PAGE;

        try {
            // 4) Phân luồng dựa trên action
            if (isUserAction(action)) {
                url = "/UserController";
            }
            else if (isProductAction(action)) {
                url = "/ProductController";
            }
            else if (isOrderAction(action)) {
                url = "/OrderController";
            }
            else if (isPaymentAction(action)) {
                url = "/PaymentController";
            }

            //------------------ CART HANDLERS ------------------

            // thêm vào giỏ hàng
            else if (isAddToCart(action)) {
                handleAddToCart(req, resp);
                return;  // đã redirect
            }
            // mua ngay (xóa giỏ cũ, thêm 1 món)
            else if (isBuyNow(action)) {
                handleBuyNow(req, resp);
                return;
            }
            // xem giỏ hàng
            else if (isViewCart(action)) {
                url = "cart.jsp";
            }
            // xóa 1 mặt hàng khỏi giỏ
            else if (isRemoveCart(action)) {
                handleRemoveCart(req, resp);
                return;
            }
            // cập nhật số lượng
            else if (isUpdateCart(action)) {
                handleUpdateCart(req, resp);
                return;
            }

            //---------------------------------------------------

            else {
                req.setAttribute("message", "Invalid action: " + action);
                url = HOME_PAGE;
            }

        } catch (Exception e) {
            // 5) Bắt mọi lỗi không mong muốn, forward sang error.jsp
            req.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            url = "error.jsp";
        }

        // 6) Forward đến servlet hoặc JSP đã xác định
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods">
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
    @Override
    public String getServletInfo() {
        return "Routes all requests to the appropriate controller based on action";
    }
    // </editor-fold>

    // ========== CART METHODS ===========

    /** Thêm sản phẩm vào cart (lưu trên session) */
    @SuppressWarnings("unchecked")
    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        List<OrderItemDTO> cart = (List<OrderItemDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        int qty = req.getParameter("quantity") != null
                ? Integer.parseInt(req.getParameter("quantity"))
                : 1;

        boolean found = false;
        for (OrderItemDTO item : cart) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity() + qty);
                found = true;
                break;
            }
        }
        if (!found) {
            // Lấy thông tin giá trị sản phẩm từ DB
            ProductDTO p = new ProductDAO().getProductById(productId);
            OrderItemDTO newItem = new OrderItemDTO();
            newItem.setProductId(productId);
            newItem.setQuantity(qty);
            newItem.setUnitPrice(p.getPrice());
            cart.add(newItem);
        }

        session.setAttribute("cart", cart);
        // redirect về cart.jsp
        resp.sendRedirect(req.getContextPath() + "/MainController?action=viewCart");
    }

    /** Mua ngay: xóa giỏ cũ, thêm 1 item này, rồi show cart */
    private void handleBuyNow(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // xóa giỏ cũ
        req.getSession().removeAttribute("cart");
        // thêm item mới
        handleAddToCart(req, resp);
    }

    /** Xóa 1 sản phẩm khỏi giỏ */
    @SuppressWarnings("unchecked")
    private void handleRemoveCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        List<OrderItemDTO> cart = (List<OrderItemDTO>) session.getAttribute("cart");
        if (cart != null) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            cart.removeIf(i -> i.getProductId() == productId);
            session.setAttribute("cart", cart);
        }
        resp.sendRedirect(req.getContextPath() + "/MainController?action=viewCart");
    }

    /** Cập nhật số lượng toàn bộ giỏ */
    @SuppressWarnings("unchecked")
    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        List<OrderItemDTO> cart = (List<OrderItemDTO>) session.getAttribute("cart");
        if (cart != null) {
            for (OrderItemDTO item : cart) {
                String param = "qty_" + item.getProductId();
                String v = req.getParameter(param);
                if (v != null) {
                    int q = Integer.parseInt(v);
                    if (q > 0) {
                        item.setQuantity(q);
                    }
                }
            }
            session.setAttribute("cart", cart);
        }
        resp.sendRedirect(req.getContextPath() + "/MainController?action=viewCart");
    }
}
