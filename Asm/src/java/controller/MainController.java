
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String HOME_PAGE = "home.jsp";
    
    private boolean isUserAction(String action)
    {
        return     action.equals("login")
                || action.equals("logout");
    }
    
    private boolean isProducttAction(String action)
    {
        return     action.equals("displayProduct")
                || action.equals("viewProductDetails")
                || action.equals("getProductByCategory")
                || action.equals("getProductByBrand")
                || action.equals("search")
                
                || action.equals("create")
                || action.equals("update")
                || action.equals("delete");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = HOME_PAGE;
        
        try
        {
            String action = request.getParameter("action");
            
            if (isUserAction(action)){
                url = "/UserController";
            }
            else if(isProducttAction(action)){
                url ="/ProductController";
            }else{
                request.setAttribute("message", "Invalid action: " + action);
                url = HOME_PAGE;
            }
        }
        catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
