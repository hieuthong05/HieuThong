
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ExamCategoryDAO;
import model.ExamCategoryDTO;
import model.ExamDAO;
import model.ExamDTO;
import utils.AuthUtils;

@WebServlet(name = "InstructorController", urlPatterns = {"/InstructorController"})
public class InstructorController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url ="error.jsp";
        try  {
            if ("loadCreateExamForm".equals(action)) {
                List<ExamCategoryDTO> categoryList = ExamCategoryDAO.getCategoryList();
                request.setAttribute("categoryList", categoryList);
                url = "ExamForm.jsp";
            } else if ("createNewExams".equals(action)) {
                url = handleCreateExam(request, response);
            }
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("message","Error occurred in StudentController");
        }finally{
            request.getRequestDispatcher(url).forward(request,response);
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

    private String handleCreateExam(HttpServletRequest request, HttpServletResponse response) {
        String checkError ="";
        String message  = "";
        if(AuthUtils.isInstructor(request)){
            String exam_title = request.getParameter("exam_title");
            String subject = request.getParameter("subject");
            String category_id = request.getParameter("category_id");
            String total_mark = request.getParameter("total_marks");
            String duration = request.getParameter("duration");
            
            int duration_value = 0;
            try{
                duration_value = Integer.parseInt(duration);
            } catch(Exception e){

            }
            
            int category_id_value = 0;
            try{
                category_id_value = Integer.parseInt(category_id);
            } catch(Exception e){

            }
            
            int total_mark_value = 0;
            try{
                total_mark_value = Integer.parseInt(total_mark);
            } catch(Exception e){

            }
            
            if(total_mark_value <0){
                checkError += "<br/> Mark must be greater than zero!!";
            }
            
            if(duration_value < 0){
                checkError += "<br/>Duration must be greater than zero!";
            }
            if(category_id_value <0){
                checkError += "<br/> Category must be choose!";
            }
            ExamDTO exam = new ExamDTO(exam_title, subject, category_id_value, total_mark_value, duration_value);
            List<ExamCategoryDTO> categoryList = ExamCategoryDAO.getCategoryList();
            request.setAttribute("categoryList", categoryList);
            request.setAttribute("exam",exam);
            
            if(checkError.isEmpty()){
                ExamDAO dao = new ExamDAO();
                boolean success = dao.createExam(exam);

                if (success) {
                    message = "Create Exam successfully!";
                } else {
                    checkError = "Failed to create Exam in database.";
                }
            }
            
            
        }else{
            checkError = "Access denied!";
        }
        
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        return "ExamForm.jsp";
        
    }

}
