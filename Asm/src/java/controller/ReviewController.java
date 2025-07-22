/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.DAO.ProductDAO;
import model.DAO.ReviewDAO;
import model.DTO.ProductDTO;
import model.DTO.ReviewDTO;
import utils.AuthUtils;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ReviewController", urlPatterns = {"/ReviewController"})
public class ReviewController extends HttpServlet {

    ReviewDAO rdao = new ReviewDAO();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = "";
        try
        {
            String action = request.getParameter("action");
            if (action.equals("createReview"))
            {
                url = handleCreate(request, response);
            }
            else if (action.equals("editReview"))
            {
                url = handleEdit(request, response);
            }
            else if (action.equals("updateReview"))
            {
                url = handleUpdate(request, response);
            }
            else if (action.equals("deleteReview"))
            {
                url = handleDelete(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error Process Review Request: " + e.getMessage());
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

    private String handleCreate(HttpServletRequest request, HttpServletResponse response)
    {
       if (AuthUtils.isLoggedIn(request))
       {
           String errorMessage = "";
           String message = "";
           
           String userId = request.getParameter("userId");
           String productId = request.getParameter("productId");
           String rate = request.getParameter("rate");
           String comment = request.getParameter("comment");
           
           ReviewDTO review = new ReviewDTO(userId, productId, rate, comment);
           request.setAttribute("review", review);
           
           if (userId == null || userId.trim().isEmpty())
           {
               errorMessage = "<br/> userId is NULL or EMPTY!";
           }
           if (productId == null || productId.trim().isEmpty())
           {
               errorMessage += "<br/> productId is NULL or EMPTY!";
           }
           if (rate == null || rate.equals("empty"))
           {
               errorMessage += "<br/> Rate Must Be NOT NULL!";
           }
           
            if (errorMessage.isEmpty())
            {
                if (!rdao.create(review))
                {
                    errorMessage += "<br/> Fail To Post Review!";
                }
            }
            if (errorMessage.isEmpty())
            {
                int productId_value = Integer.parseInt(productId);
                request.setAttribute("message", "Post Review Successfully. ^^");
                ProductDAO productDAO = new ProductDAO();
                ProductDTO product = productDAO.getProductById(productId_value); 
                List<ReviewDTO> list = rdao.getReviewByProductId(productId);
                request.setAttribute("product", product); 
                request.setAttribute("list", list);
                return "productDetails.jsp";
            }
            else
            {
                request.setAttribute("errorMessage", errorMessage);
                return "createReview.jsp";
            }
       }
       else
       {
           request.setAttribute("errorMessage", "NOT ALLOW CREATE REVIEW!!!");
           return "error.jsp";
       }
    }

    private String handleEdit(HttpServletRequest request, HttpServletResponse response)
    {
            String reviewId = request.getParameter("reviewId");
            ReviewDTO review = rdao.getReviewById(reviewId);
            if (review != null)
            {
                if ( (AuthUtils.isReviewOfUser(request, review.getUserId())) || (AuthUtils.isAdmin(request)) )
                {
                    request.setAttribute("review", review);
                    request.setAttribute("isEdit", true);
                    return "createReview.jsp";
                }
                else
                {
                    request.setAttribute("errorMessage", "NOT ALLOW EDIT REVIEW!!");
                    return "error.jsp";
                }
            }
            else
            {
                request.setAttribute("errorMessage", "Not Exists Review!");
                request.setAttribute("isEdit", true);
                return "createReview.jsp";
            }
    }

    private String handleUpdate(HttpServletRequest request, HttpServletResponse response)
    {
        String reviewId = request.getParameter("reviewId");
        ReviewDTO rv = rdao.getReviewById(reviewId);
        if ((AuthUtils.isReviewOfUser(request, rv.getUserId())) || (AuthUtils.isAdmin(request)) )
        {
           String errorMessage = "";
           String message = "";
           
           
           String userId = request.getParameter("userId");
           String productId = request.getParameter("productId");
           String rate = request.getParameter("rate");
           String comment = request.getParameter("comment");
           
           ReviewDTO review = new ReviewDTO(reviewId, rate, comment);
           request.setAttribute("review", review);
           
           if (userId == null || userId.trim().isEmpty())
           {
               errorMessage = "<br/> userId is NULL or EMPTY!";
           }
           if (productId == null || productId.trim().isEmpty())
           {
               errorMessage += "<br/> productId is NULL or EMPTY!";
           }
           if (rate == null || rate.equals("empty"))
           {
               errorMessage += "<br/> Rate Must Be NOT NULL!";
           }
           if (errorMessage.isEmpty())
            {
                if (rdao.update(review))
                {
                    int productId_value = Integer.parseInt(productId);
                    request.setAttribute("message", "Update Review Successfully. ^^");
                    ProductDAO productDAO = new ProductDAO();
                    ProductDTO product = productDAO.getProductById(productId_value); 
                    List<ReviewDTO> list = rdao.getReviewByProductId(productId);
                    request.setAttribute("product", product); 
                    request.setAttribute("list", list);
                    return "productDetails.jsp";
                }
            }
               errorMessage += "<br/> Can not Update Review! ^^";
               request.setAttribute("isEdit", true);
               request.setAttribute("errorMessage", errorMessage);
               return "createReview.jsp";
        }
        else
        {
            request.setAttribute("errorMessage", "NOT ALLOW UPDATE REVIEW!!");
            return "error.jsp";
        }
    }

    private String handleDelete(HttpServletRequest request, HttpServletResponse response)
    {
        String reviewId = request.getParameter("reviewId");
        ReviewDTO rv = rdao.getReviewById(reviewId);
        if ((AuthUtils.isReviewOfUser(request, rv.getUserId())) || (AuthUtils.isAdmin(request)))
        {
            
            boolean check = rdao.updateReviewStatus(reviewId, false);
            if (check)
            {
                request.setAttribute("message", "Delete Review Successfully. ^^");
                return "home.jsp";
            }
            else
            {
                request.setAttribute("errorMessage", "Fail To Delete Review!!!");
               return "error.jsp";
            }
            
        }
        else
        {
            request.setAttribute("errorMessage", "NOT ALLOW DELETE REVIEW!!");
            return "error.jsp";
        }
    }

}
