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

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    // Trang mặc định nếu không có action hợp lệ
    private static final String HOME_PAGE = "home.jsp";

    /* ============================================================= */
    /* =============== 1. CÁC NHÓM ACTION ========================== */
    /* ============================================================= */
    private boolean isUserAction(String action) {
        return "login".equals(action)
            || "logout".equals(action)
            || "register".equals(action)
            || "editProfile".equals(action)
            || "manageUser".equals(action)
            || "updateProfile".equals(action)
            || "deleteUser".equals(action);
    }

    private boolean isReviewAction(String action) {
        return "createReview".equals(action)

                || "editReview".equals(action)
                || "updateReview".equals(action)
                || "deleteReview".equals(action)

            || "editReview".equals(action)
            || "updateReview".equals(action);

    }

    private boolean isProductAction(String action) {
        return "displayProducts".equals(action)
            || "viewProductDetails".equals(action)
            || "getProductByCategory".equals(action)
            || "getProductByBrand".equals(action)
            || "search".equals(action)
            || "create".equals(action)
            || "update".equals(action)
            || "deleteProduct".equals(action)
            || "displayCategory".equals(action);
    }

    private boolean isOrderAction(String action) {
        return "MyOrder".equals(action);
    }

    private boolean isPaymentAction(String action) {
        return "viewPayments".equals(action)
            || "processPayment".equals(action)
            || "refundPayment".equals(action);
    }

    /* ---------- Cart action flags ---------- */
    private boolean isAddToCart (String a){ return "addToCart".equals(a);}
    private boolean isBuyNow    (String a){ return "buyNow".equals(a);}
    private boolean isViewCart  (String a){ return "viewCart".equals(a);}
    private boolean isRemoveCart(String a){ return "removeCart".equals(a);}
    private boolean isUpdateCart(String a){ return "updateCart".equals(a);}

    /* ============================================================= */
    /* =============== 2. DISPATCHER =============================== */
    /* ============================================================= */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        String url = HOME_PAGE;

        try {
            if      (isUserAction   (action)) url = "/UserController";
            else if (isProductAction(action)) url = "/ProductController";
            else if (isOrderAction  (action)) url = "/OrderController";
            else if (isPaymentAction(action)) url = "/PaymentController";
            else if (isReviewAction (action)) url = "/ReviewController";

            /* --------------- CART --------------- */
            else if (isAddToCart (action)) { handleAddToCart(req, resp); return; }
            else if (isBuyNow    (action)) { handleBuyNow   (req, resp); return; }
            else if (isViewCart  (action)) { url = "cart.jsp"; }
            else if (isRemoveCart(action)) { handleRemoveCart(req, resp); return; }
            else if (isUpdateCart(action)) { handleUpdateCart(req, resp); return; }

            /* fallback */
            else {
                resp.sendRedirect(req.getContextPath() + "/" + HOME_PAGE);
                return;
            }

        } catch (Exception e) {
            req.setAttribute("errorMessage",
                    "Error processing request: " + e.getMessage());
            url = "error.jsp";
        }

        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }

    /* ---------------- HttpServlet overrides ---------------- */
    @Override protected void doGet (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException { processRequest(req, resp); }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException { processRequest(req, resp); }

    @Override public String getServletInfo() { return "Main front-controller"; }

    /* ============================================================= */
    /* =============== 3. CART UTILITIES =========================== */
    /* ============================================================= */
    @SuppressWarnings("unchecked")
    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession();
        List<OrderItemDTO> cart =
            (List<OrderItemDTO>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        int productId = Integer.parseInt(req.getParameter("productId"));

        /* ---------- ★ Sửa A: lấy qty + unitPrice linh hoạt ---------- */
        int qty = 1;
        String qRaw = req.getParameter("qty");
        if (qRaw == null) qRaw = req.getParameter("quantity");
        if (qRaw != null) try { qty = Integer.parseInt(qRaw); } catch (NumberFormatException ignored) {}
        if (qty <= 0) qty = 1;

        double unitPrice;
        String uRaw = req.getParameter("unitPrice");
        if (uRaw != null) {
            unitPrice = Double.parseDouble(uRaw);
        } else {
            unitPrice = new ProductDAO().getProductById(productId).getPrice();
        }
        /* ---------- ★ End Sửa A ------------------------------------ */

        /* thêm / cập nhật trong giỏ */
        OrderItemDTO target = null;
        for (OrderItemDTO it : cart) {
            if (it.getProductId() == productId) { target = it; break; }
        }
        if (target == null) {
            target = new OrderItemDTO();
            target.setProductId(productId);
            target.setUnitPrice(unitPrice);
            target.setQuantity(0);
            cart.add(target);
        }
        target.setQuantity(target.getQuantity() + qty);

        session.setAttribute("cart", cart);
        resp.sendRedirect(req.getContextPath()
                + "/MainController?action=viewCart");
    }

    /* ---------------- BUY-NOW ---------------- */
    private void handleBuyNow(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        /* ---------- ★ Sửa B: quy trình buy-now hoàn chỉnh ---------- */
        HttpSession session = req.getSession();

        /* reset giỏ */
        List<OrderItemDTO> cart = new ArrayList<>();
        session.setAttribute("cart", cart);

        /* lấy sản phẩm 1 món */
        int productId    = Integer.parseInt(req.getParameter("productId"));
        double unitPrice = Double.parseDouble(req.getParameter("unitPrice"));

        OrderItemDTO it = new OrderItemDTO();
        it.setProductId(productId);
        it.setQuantity(1);
        it.setUnitPrice(unitPrice);
        cart.add(it);

        /* bắt buộc đăng nhập */
        if (session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath()
                    + "/login.jsp?msg=Vui+lòng+đăng+nhập+để+thanh+toán");
            return;
        }

        /* đã login → sang checkout */
        resp.sendRedirect(req.getContextPath()
                + "/OrderController?action=checkout");
        /* ---------- ★ End Sửa B ------------------------------------ */
    }

    /* ---------------- REMOVE ---------------- */
    @SuppressWarnings("unchecked")
    private void handleRemoveCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession();
        List<OrderItemDTO> cart =
            (List<OrderItemDTO>) session.getAttribute("cart");

        if (cart != null) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            cart.removeIf(i -> i.getProductId() == productId);
            session.setAttribute("cart", cart);
        }
        resp.sendRedirect(req.getContextPath()
                + "/MainController?action=viewCart");
    }

    /* ---------------- UPDATE ---------------- */
    @SuppressWarnings("unchecked")
    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession();
        List<OrderItemDTO> cart =
            (List<OrderItemDTO>) session.getAttribute("cart");

        if (cart != null) {
            for (OrderItemDTO item : cart) {
                String v = req.getParameter("qty_" + item.getProductId());
                if (v != null) {
                    int q = Integer.parseInt(v);
                    if (q > 0) item.setQuantity(q);
                }
            }
            session.setAttribute("cart", cart);
        }
        resp.sendRedirect(req.getContextPath()
                + "/MainController?action=viewCart");
    }
}
