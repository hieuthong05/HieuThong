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
import jakarta.servlet.http.HttpSession;
import model.DAO.UserDAO;
import model.DTO.UserDTO;
import utils.AuthUtils;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String HOME_PAGE = "home.jsp";
    UserDAO udao = new UserDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        String url = "";
        try
        {
            String action = request.getParameter("action");
            
            if (action.equals("login"))
            {
                url = handleLogin(request, response);
            }
            else if (action.equals("logout"))
            {
                url = handleLogout(request, response);
            }
            else if (action.equals("register"))
            {
                url = handleRegister(request, response);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "System error occurred!");
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

    private String handleLogin(HttpServletRequest request, HttpServletResponse response)
    {
        String url = LOGIN_PAGE;
       HttpSession session = request.getSession();
       
       String username = request.getParameter("un");
       String password = request.getParameter("pw");

       UserDAO ud = new UserDAO();

       if (ud.login(username, password))
       {
           UserDTO user = ud.getUserByUsername(username);
           
           url = HOME_PAGE;
           session.setAttribute("user", user);
       }
       else
       {
           url = LOGIN_PAGE;
           request.setAttribute("message", "Username or Password Incorrect! ^^");
       }
       
       return url;
    }

    private String handleLogout(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        if (AuthUtils.isLoggedIn(request))
        {   
            session.invalidate();
        }
        return HOME_PAGE;
    }

    private String handleRegister(HttpServletRequest request, HttpServletResponse response)
    {
        String errorMessage = "";
        String message = "";
        
        String userName = request.getParameter("userName");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String cfPassword = request.getParameter("cfPassword");
        
        UserDTO us = new UserDTO(userName, fullName, email, password);
        request.setAttribute("us", us);
<<<<<<< HEAD
        return "registerForm";

=======
        
        if (udao.isUserExists(userName))
        {
            errorMessage = "<br/> User Name is already exists!";
        }
        if (userName == null || userName.trim().isEmpty())
        {
            errorMessage += "<br/> Username Must Be NON-EMPTY!";
        }
        if (fullName == null || fullName.trim().isEmpty())
        {
            errorMessage += "<br/> Full Name Must Be NON-EMPTY!";
        }
        if (email == null || email.trim().isEmpty())
        {
            errorMessage += "<br/> Email Must Be NON-EMPTY!";
        }
        if (password == null || password.trim().isEmpty())
        {
            errorMessage += "<br/> Password Must Be NON-EMPTY!";
        }
        if (cfPassword == null || cfPassword.trim().isEmpty())
        {
            errorMessage += "<br/> Confirm Password Must Be NON-EMPTY!";
        }    
        if (!password.equals(cfPassword))
        {
            errorMessage += "<br/> Fail Confirm Password (Not Match)!";
        }
        if (errorMessage.isEmpty())
        {
            if (!udao.create(us))
            {
                errorMessage += "<br/> Sign Up Failed!";
            }
        }
        if (errorMessage.isEmpty())
        {
            message = "Sign Up Successfully. ^^";
        }
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("message", message);
        return "registerForm.jsp";
>>>>>>> b32aee3a31d61c6a1f9a8cb8fd227333605b8a51
    }

}
