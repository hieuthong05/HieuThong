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
import model.DAO.ReviewDAO;
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
//            if (action.equals("createReview"))
//            {
//                url = handleCreate(request, response);
//            }
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

//    private String handleCreate(HttpServletRequest request, HttpServletResponse response)
//    {
//       if (AuthUtils.isLoggedIn(request))
//       {
//           String errorMessage = "";
//           String message = "";
//           
//           String userId = request.getParameter("userId");
//           String productId = request.getParameter("productId");
//           String rate = request.getParameter("rate");
//           String comment = request.getParameter("comment");
//           
//           ReviewDTO review = new ReviewDTO(userId, productId, rate, comment);
//           request.setAttribute("review", review);
//       }
//       else
//       {
//           request.setAttribute("errorMessage", "NOT ALLOW CREATE REVIEW!!!");
//           return "error.jsp";
//       }
//    }

}
