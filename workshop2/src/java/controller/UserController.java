
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.ExamCategoryDAO;
import model.ExamCategoryDTO;
import model.UserDAO;
import model.UserDTO;
import utils.PasswordUtils;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String WELCOME_PAGE="welcome.jsp";
    private static final String LOGIN_PAGE="login.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        System.out.println("1");
        try{
            String action = request.getParameter("action");
            if("login".equals(action)){
                System.out.println("2");
                url = handleLogin(request, response);
            }else if ("logout".equals(action)){
                url = handleLogout(request,response);
            }else if("viewExamCategories".equals(action)){
                url = handleViewExamCategories(request,response);
            }else{
                request.setAttribute("message","Invalid action:"+action);
                url = LOGIN_PAGE;
            }
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("message","System error occurred");
            url = "error.jsp";
        }finally{
            if (url != null) {
                request.getRequestDispatcher(url).forward(request, response);
            }
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

    private String handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url=LOGIN_PAGE;
        HttpSession session = request.getSession();
        String  strUsername = request.getParameter("username");
        String  strPassword = request.getParameter("password");
    //    strPassword = PasswordUtils.encryptSHA256(strPassword);
        UserDAO userDAO = new UserDAO();
        if(userDAO.login(strUsername, strPassword)){
            UserDTO user = userDAO.getUserName(strUsername);
            
            
            session.setAttribute("user", user);
            response.sendRedirect("MainController?action=welcome");
            return null;
        }else{
            url = LOGIN_PAGE;
            request.setAttribute("message","Username or Password is incorrect ! Please try again ");
        }
        return url;
    }
    
    private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN_PAGE;
        UserDAO userDAO = new UserDAO();
        try{
            HttpSession session = request.getSession();
            if(session !=null){
                Object objUser = session.getAttribute("user");
                UserDTO user = (objUser !=null) ? (UserDTO) objUser : null;
                
                if(user != null ){
                    session.invalidate();
                }
            }
        }catch(Exception e){
            
        }
        return url;
    }

    private String handleViewExamCategories(HttpServletRequest request, HttpServletResponse response) {
        String url =WELCOME_PAGE;
        
        try{
            ExamCategoryDAO examcategoryDAO = new ExamCategoryDAO();
            List<ExamCategoryDTO> categories = examcategoryDAO.getAll();
            request.setAttribute("categoryList",categories);
        }catch(Exception e){
            System.err.println("Error in handleViewExamCategories:"+ e.getMessage());
            e.printStackTrace();
            
        }
        
        return url;
    }
    
}
