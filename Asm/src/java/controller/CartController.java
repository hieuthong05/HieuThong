package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.DTO.OrderItemDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {

    /* ============================================================ */
    /*  Helpers                                                     */
    /* ============================================================ */

    /** Lấy (hoặc tạo) giỏ hàng */
    @SuppressWarnings("unchecked")
    private List<OrderItemDTO> getCart(HttpSession ses) {
        List<OrderItemDTO> cart = (List<OrderItemDTO>) ses.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            ses.setAttribute("cart", cart);
        }
        return cart;
    }

    /** Ép int an toàn */
    private int toInt(String s, int d) {
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return d; }
    }

    /** Trở lại trang trước (nếu có Referer) – dùng cho addToCart */
    private void back(HttpServletRequest r, HttpServletResponse p) throws IOException {
        String ref = r.getHeader("Referer");
        p.sendRedirect(ref != null ? ref : r.getContextPath() + "/CartController?action=viewCart");
    }

    /** Luôn reload giỏ hàng */
    private void backToCart(HttpServletRequest r, HttpServletResponse p) throws IOException {
        p.sendRedirect(r.getContextPath() + "/CartController?action=viewCart");
    }

    /* ============================================================ */
    /*  Dispatcher                                                  */
    /* ============================================================ */

    @Override protected void doGet (HttpServletRequest r,HttpServletResponse p) throws ServletException,IOException { process(r,p); }
    @Override protected void doPost(HttpServletRequest r,HttpServletResponse p) throws ServletException,IOException { process(r,p); }

    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "viewCart";

        HttpSession        ses  = req.getSession();
        List<OrderItemDTO> cart = getCart(ses);

        switch (action) {

            /* ------------ Hiển thị giỏ ------------ */
            case "viewCart":
                req.setAttribute("cart", cart);
                req.getRequestDispatcher("cart.jsp").forward(req, resp);
                break;

            /* ------------ Thêm sản phẩm ------------ */
            case "addToCart": {
    int pid   = toInt(req.getParameter("productId"), 0);
    double price = Double.parseDouble(req.getParameter("unitPrice"));
    int qty   = Math.max(1, toInt(req.getParameter("qty"), 1));

    OrderItemDTO tgt = null;
    for (OrderItemDTO it : cart)
        if (it.getProductId() == pid) { tgt = it; break; }

    if (tgt == null) {
        tgt = new OrderItemDTO();
        tgt.setProductId(pid);
        tgt.setUnitPrice(price);
        tgt.setQuantity(0);
        cart.add(tgt);
    }
    tgt.setQuantity(tgt.getQuantity() + qty);

    /* =======================================================
       Nếu form gửi kèm ?next=checkout  →  nhảy thẳng sang tạo đơn
       ======================================================= */
    String next = req.getParameter("next");
    if ("checkout".equals(next)) {
        resp.sendRedirect(req.getContextPath()
                + "/OrderController?action=checkout");
    } else {
        back(req, resp);      // logic cũ: quay về trang trước
    }
    break;
}

            /* ------------ +/- số lượng ------------ */
            case "updateQuantity": {            // index + delta
                int idx   = toInt(req.getParameter("index"),  -1);
                int delta = toInt(req.getParameter("delta"),   0);

                if (idx >= 0 && idx < cart.size() && delta != 0) {
                    OrderItemDTO it = cart.get(idx);
                    int q = it.getQuantity() + delta;
                    if (q <= 0) cart.remove(idx); else it.setQuantity(q);
                }
                backToCart(req, resp);
                break;
            }

            /* ----- Cập nhật tuyệt đối (index + qty) ----- */
            case "setQuantity": {
                int idx = toInt(req.getParameter("index"), -1);
                int q   = toInt(req.getParameter("qty"),   1);

                if (idx >= 0 && idx < cart.size()) {
                    if (q <= 0) cart.remove(idx); else cart.get(idx).setQuantity(q);
                }
                backToCart(req, resp);
                break;
            }

            /* ------------ Xoá một item ------------ */
            case "removeFromCart": {
                int idx = toInt(req.getParameter("index"), -1);
                if (idx >= 0 && idx < cart.size()) cart.remove(idx);
                backToCart(req, resp);
                break;
            }

            /* ------------ Xoá toàn bộ ------------ */
            case "clearCart":
                ses.removeAttribute("cart");
                backToCart(req, resp);
                break;

            /* ------------ Action không hợp lệ ------------ */
            default:
                resp.sendRedirect(req.getContextPath() + "/home.jsp");
        }
    }
}
