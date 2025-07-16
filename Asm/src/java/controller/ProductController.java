
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import model.DAO.BrandDAO;
import model.DAO.CategoryDAO;
import model.DAO.ProductDAO;
import model.DAO.ReviewDAO;
import model.DTO.BrandDTO;
import model.DTO.CategoryDTO;
import model.DTO.ProductDTO;
import model.DTO.ReviewDTO;
import utils.AuthUtils;

@MultipartConfig
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
            }else if(action.equals("showCreateForm")){
                url = handleShowCreateForm(request, response);
            }else if (action.equals("showUpdateForm")) {
                url = handleShowUpdateForm(request, response);
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
        try {
            
            String proId = request.getParameter("productId");
            int productId = Integer.parseInt(proId);

            ProductDAO productDAO = new ProductDAO();
            ProductDTO product = productDAO.getProductById(productId); 
            ReviewDAO rdao = new ReviewDAO();
            List<ReviewDTO> list = rdao.getReviewByProductId(proId);

            if (product != null) {
                request.setAttribute("product", product); 
                request.setAttribute("list", list);
                return "productDetails.jsp"; 
            } else {
                request.setAttribute("error", "Product not found!");
                return "error.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading product details.");
            return "error.jsp";
        }
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
        try {
            String keyword = request.getParameter("keyword");
            request.setAttribute("keyword", keyword);
            
            ProductDAO productDAO = new ProductDAO();
            List<ProductDTO> products = productDAO.getProductByName(keyword);
            request.setAttribute("products", products);

            
            CategoryDAO cdao = new CategoryDAO();
            request.setAttribute("categories", cdao.getAll());

            BrandDAO bdao = new BrandDAO();
            request.setAttribute("brands", bdao.getAll());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Search error: " + e.getMessage());
            return "error.jsp";
        }

        return "home.jsp";
    }

    private String handleCreate(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        if (AuthUtils.isAdmin(request)) {
            try {
                // Upload ảnh
                Part filePart = request.getPart("photo");
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                // String fileName = System.currentTimeMillis() + "_" + originalFileName;
                String fileName = originalFileName;
                String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String filePath = uploadDir + File.separator + fileName;
                filePart.write(filePath);

                // Lấy dữ liệu từ form
                String name = request.getParameter("name");
                int brandId = Integer.parseInt(request.getParameter("brandId"));
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                String description = request.getParameter("description");
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                String imageUrl = "images/" + fileName;


                ProductDTO product = new ProductDTO(0, name, brandId, price, stock, description, imageUrl, categoryId);
                ProductDAO dao = new ProductDAO();
                dao.create(product); 

                request.setAttribute("message", "Thêm sản phẩm thành công!");
                return handleDisplayProducts(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Create product failed: " + e.getMessage());
                return "error.jsp";
            }
        }
        CategoryDAO cdao = new CategoryDAO();
        BrandDAO bdao = new BrandDAO();
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        
        request.setAttribute("categories", cdao.getAll());
        request.setAttribute("brands", bdao.getAll());
        return "createProduct.jsp";
    }

    private String handleUpdate(HttpServletRequest request, HttpServletResponse response) {
        if (!AuthUtils.isAdmin(request)) {
            request.setAttribute("error", "Permission denied.");
            return "error.jsp";
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String name = request.getParameter("name");
            int brandId = Integer.parseInt(request.getParameter("brandId"));
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            // Kiểm tra có upload ảnh mới không
            Part filePart = request.getPart("photo");
            String imageUrl = null;
            if (filePart != null && filePart.getSize() > 0) {
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String fileName = System.currentTimeMillis() + "_" + originalFileName;
                String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();
                String filePath = uploadDir + File.separator + fileName;
                filePart.write(filePath);
                imageUrl = "images/" + fileName;
            }

            ProductDAO dao = new ProductDAO();
            ProductDTO oldProduct = dao.getProductById(productId);

            if (oldProduct == null) {
                request.setAttribute("error", "Product not found.");
                return "error.jsp";
            }

            // Nếu không upload ảnh mới thì giữ lại ảnh cũ
            if (imageUrl == null) {
                imageUrl = oldProduct.getImageUrl();
            }

            ProductDTO updated = new ProductDTO(productId, name, brandId, price, stock, description, imageUrl, categoryId);
            dao.update(updated);

            return handleDisplayProducts(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Update failed: " + e.getMessage());
            return "error.jsp";
        }
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

    private String handleShowCreateForm(HttpServletRequest request, HttpServletResponse response) {
        try {
            CategoryDAO cdao = new CategoryDAO();
            List<CategoryDTO> categories = cdao.getAll();
            request.setAttribute("categories", categories);

            BrandDAO bdao = new BrandDAO();
            List<BrandDTO> brands = bdao.getAll();
            request.setAttribute("brands", brands);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "createProduct.jsp";
    }
    
    private String handleShowUpdateForm(HttpServletRequest request, HttpServletResponse response) {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductDAO productDAO = new ProductDAO();
            ProductDTO product = productDAO.getProductById(productId);

            if (product == null) {
                request.setAttribute("error", "Không tìm thấy sản phẩm.");
                return "error.jsp";
            }

            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryDTO> categories = categoryDAO.getAll();
            BrandDAO brandDAO = new BrandDAO();
            List<BrandDTO> brands = brandDAO.getAll();

            request.setAttribute("product", product);
            request.setAttribute("categories", categories);
            request.setAttribute("brands", brands);

            return "updateProduct.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi hiển thị form cập nhật: " + e.getMessage());
            return "error.jsp";
        }
    }


}
