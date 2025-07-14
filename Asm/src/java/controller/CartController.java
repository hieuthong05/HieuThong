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

  
    @SuppressWarnings("unchecked")
    private List<OrderItemDTO> getCart(HttpSession session) {
        List<OrderItemDTO> cart = (List<OrderItemDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private void redirectBack(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer != null ? referer : req.getContextPath() + "/home.jsp");
    }

    /* ===== dispatcher ===== */
    @Override protected void doGet (HttpServletRequest r,HttpServletResponse p)throws ServletException,IOException{process(r,p);}
    @Override protected void doPost(HttpServletRequest r,HttpServletResponse p)throws ServletException,IOException{process(r,p);}

    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "viewCart";

        switch (action) {

            case "viewCart": {                                      
                List<OrderItemDTO> cart = getCart(req.getSession());
                req.setAttribute("cart", cart);
                req.getRequestDispatcher("cart.jsp").forward(req, resp);
                break;
            }

            case "addToCart": {                                     
                HttpSession session = req.getSession();
                List<OrderItemDTO> cart = getCart(session);

                int    productId = Integer.parseInt(req.getParameter("productId"));
                double unitPrice = Double.parseDouble(req.getParameter("unitPrice"));
                int    qty       = 1;
                try {
                    qty = Integer.parseInt(req.getParameter("qty"));
                    if (qty <= 0) qty = 1;
                } catch (NumberFormatException ignore) {}

                boolean found = false;
                for (OrderItemDTO item : cart) {
                    if (item.getProductId() == productId) {
                        item.setQuantity(item.getQuantity() + qty);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    OrderItemDTO newItem = new OrderItemDTO();
                    newItem.setProductId(productId);
                    newItem.setQuantity(qty);
                    newItem.setUnitPrice(unitPrice);
                    cart.add(newItem);
                }

                session.setAttribute("cartMessage", "Đã thêm sản phẩm vào giỏ hàng!");

                redirectBack(req, resp);
                break;
            }

            case "removeFromCart": {                                
                List<OrderItemDTO> cart = getCart(req.getSession());
                try {
                    int idx = Integer.parseInt(req.getParameter("index"));
                    if (idx >= 0 && idx < cart.size()) cart.remove(idx);
                } catch (NumberFormatException ignore) {}
                redirectBack(req, resp);
                break;
            }

            case "clearCart": {                                     
                req.getSession().removeAttribute("cart");
                redirectBack(req, resp);
                break;
            }

            default:                                               
                resp.sendRedirect(req.getContextPath() + "/home.jsp");
        }
    }
}
