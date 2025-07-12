
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.DAO.BrandDAO;
import model.DAO.CategoryDAO;
import model.DAO.ProductDAO;
import model.DTO.BrandDTO;
import model.DTO.CategoryDTO;
import model.DTO.ProductDTO;

@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";
        try{
            String action = request.getParameter("action");
            
            //----action cua User -----
            if(action.equals("displayCategory")){
                url = handleDisplayCategory(request,response);
            }
            
            if(action.equals("displayProducts")){
                url = handleDisplayProducts(request,response);
            }else if(action.equals("viewProductDetails")){
                url = handleViewProductDetails(request,response);
            }else if(action.equals("getProductByCategory")){
                url = handleGetProductByCategory(request,response);
            }else if(action.equals("getProductByBrand")){
                url = handleGetProductByBrand(request,response);
            }else if(action.equals("search")){
                url = handleSearch(request,response);
            }else if(action.equals("create")){              //---action cua ADMIN ---
                url = handleCreate(request,response);
            }else if(action.equals("update")){
                url = handleUpdate(request,response);
            }else if(action.equals("delete")){
                url = handleDelete(request,response);
            }
            
        }catch (Exception e){
            
        }finally{
            System.err.println(url);
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

    private String handleDisplayProducts(HttpServletRequest request, HttpServletResponse response) {
        try{
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> list = dao.getAll();
            request.setAttribute("products", list);
            
            CategoryDAO cdao = new CategoryDAO();
            List<CategoryDTO> categories = cdao.getAll();
            request.setAttribute("categories", categories);
            
            BrandDAO bdao = new BrandDAO();
            List<BrandDTO> brands = bdao.getAll();
            request.setAttribute("brands", brands);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return "home.jsp";
    }

    private String handleViewProductDetails(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String handleGetProductByCategory(HttpServletRequest request, HttpServletResponse response) {
    try {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        ProductDAO dao = new ProductDAO();
        List<ProductDTO> products = dao.getProductsByCategoryId(categoryId);
        request.setAttribute("products", products);

        CategoryDAO cdao = new CategoryDAO();
        List<CategoryDTO> categories = cdao.getAll();
        request.setAttribute("categories", categories);

    } catch (Exception e) {
        e.printStackTrace();
    }
    return "home.jsp";
}


    private String handleGetProductByBrand(HttpServletRequest request, HttpServletResponse response) {
        try {
            int brandId = Integer.parseInt(request.getParameter("brandId"));
            ProductDAO dao = new ProductDAO();
            request.setAttribute("products", dao.getProductsByBrandId(brandId));

            CategoryDAO cdao = new CategoryDAO();
            request.setAttribute("categories", cdao.getAll());

            BrandDAO bdao = new BrandDAO();
            request.setAttribute("brands", bdao.getAll());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home.jsp";
    }


    private String handleSearch(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String handleCreate(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String handleUpdate(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String handleDelete(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String handleDisplayCategory(HttpServletRequest request, HttpServletResponse response) {
        try{
            CategoryDAO dao = new CategoryDAO();
            List<CategoryDTO> list = dao.getAll();
            request.setAttribute("categories", list);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "home.jsp";
    }

}
