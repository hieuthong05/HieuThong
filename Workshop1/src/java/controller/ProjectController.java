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
import java.time.LocalDate;
import java.util.List;
import model.ProjectDAO;
import model.ProjectDTO;
import utils.AuthUtils;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProjectController", urlPatterns = {"/ProjectController"})
public class ProjectController extends HttpServlet {

    ProjectDAO pd = new ProjectDAO();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = "";
        try
        {
            String action = request.getParameter("action");
            
            if (action.equals("searchProjectName"))
            {
               url = handleProjectSearching(request, response);
            }
            else if (action.equals("createProject"))
            {
                url = handleProjectAdding(request, response);
            }
            else if (action.equals("editProject"))
            {
                url = handleProjectEditing(request, response);
            }
            else if (action.equals("updateProjectStatus"))
            {
                url = handleProjectUpdating(request, response);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", e.getMessage());
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

    private String handleProjectSearching(HttpServletRequest request, HttpServletResponse response)
    {
        if (AuthUtils.isFounder(request))
        {
            String keyword = request.getParameter("kw");
            List<ProjectDTO> searchList = pd.getProjectsByName(keyword);
            request.setAttribute("searchList", searchList);
            request.setAttribute("keyword", keyword);
            return "dashboard.jsp";
        }
        return "dashboard.jsp";
    }

    private String handleProjectAdding(HttpServletRequest request, HttpServletResponse response)
    {
        String errorMessage = "";
        String message = "";
       if (AuthUtils.isFounder(request))
       {
           String id = request.getParameter("id");
           String name = request.getParameter("name");
           String description = request.getParameter("description");
           String status = request.getParameter("status");
           String estimated_launch = request.getParameter("est");
           
           int id_value = 0;
           try {
               id_value = Integer.parseInt(id);
           } catch (Exception e) {
               errorMessage = "<br/> Invalid ID! ^^";
           }
           
           LocalDate est = null;
           try {
               est = LocalDate.parse(estimated_launch);
           } catch (Exception e) {
               errorMessage += "<br/> Invalid Date! ^^";
           }
           
           ProjectDTO proj = new ProjectDTO(id_value, name, description, status, est);
           request.setAttribute("proj", proj);
           
           if (pd.isProjectExists(id_value) || id_value == 0)
           {
               errorMessage += "<br/> This Project ID is already exists! ^^";            
           }
           if (name == null || name.trim().isEmpty())
           {
               errorMessage += "<br/> Project Name MUST be NON-EMPTY! ^^";          
           }
           if (status.equals("Empty"))
           {
               errorMessage += "<br/> Status MUST be NON-EMPTY! ^^";                        
           }
           if (est.isBefore(LocalDate.now()) || est == null)
           {
               errorMessage += "<br/> Invalid Date! ^^";   
           }
           
           if (errorMessage.isEmpty())
           {
               if (!pd.create(proj))
               {
                   errorMessage += "<br/> Can not add project! ^^";             
               }             
           }
           if (errorMessage.isEmpty())
           {
               message = "Add project successfully! ^^";
           }
           
           request.setAttribute("errorMessage", errorMessage);
           request.setAttribute("message", message);
           return "projectForm.jsp";
       }
       return "projectForm.jsp";
    }

    private String handleProjectEditing(HttpServletRequest request, HttpServletResponse response)
    {
        if (AuthUtils.isFounder(request))
        {
            String keyword = request.getParameter("kw");
            String projId = request.getParameter("projId");
           int id_value = 0;
           try {
               id_value = Integer.parseInt(projId);
           } catch (Exception e) {
               e.printStackTrace();
               System.err.println("Error Id! ^^" + e.getMessage());
           }
           
            ProjectDTO proj = pd.getProjectById(id_value);
            if (proj != null)
            {
                request.setAttribute("proj", proj);
                request.setAttribute("keyword", keyword);
                request.setAttribute("isEdit", true);
                return "projectForm.jsp";
            }
            else
            {
                request.setAttribute("errorMessage", "NOT found this project to update! ^^");
            }
            
        }
        return "dashboard.jsp";
    }

    private String handleProjectUpdating(HttpServletRequest request, HttpServletResponse response)
    {
        String errorMessage = "";
        String message = "";
       if (AuthUtils.isFounder(request))
       {
           String keyword = request.getParameter("kw");
           String id = request.getParameter("id");
           String name = request.getParameter("name");
           String description = request.getParameter("description");
           String status = request.getParameter("status");
           String estimated_launch = request.getParameter("est");
           
           int id_value = 0;
           try {
               id_value = Integer.parseInt(id);
           } catch (Exception e) {
               errorMessage = "<br/> Invalid ID! ^^";
           }
           
           LocalDate est = null;
           try {
               est = LocalDate.parse(estimated_launch);
           } catch (Exception e) {
               errorMessage += "<br/> Invalid Date! ^^";
           }
           
           ProjectDTO proj = new ProjectDTO(id_value, name, description, status, est);
           request.setAttribute("proj", proj);
           
           if (name == null || name.trim().isEmpty())
           {
               errorMessage += "<br/> Project Name MUST be NON-EMPTY! ^^";          
           }
           if (status.equals("Empty"))
           {
               errorMessage += "<br/> Status MUST be NON-EMPTY! ^^";                        
           }
           if (est.isBefore(LocalDate.now()) || est == null)
           {
               errorMessage += "<br/> Invalid Date! ^^";   
           }
           
           if (errorMessage.isEmpty())
           {
               if (pd.updateProjectStatus(proj))
               {
                    message = "Update product successfully! ^^";
                    request.setAttribute("message", message);
                    request.setAttribute("keyword", keyword);
                    return handleProjectSearching(request, response);
               }             
           }
           
           errorMessage += "<br/> Can not Update project! ^^";
           request.setAttribute("isEdit", true);
           request.setAttribute("errorMessage", errorMessage);
           return "projectForm.jsp";
       }
       return "projectForm.jsp";
    }

}
