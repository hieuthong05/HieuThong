<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page import="java.util.List"%>
<%@page import="model.ExamDTO"%>
<%@page import="model.ExamCategoryDTO"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Home Page</title>
        <meta charset="UTF-8">
      
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="assets/css/welcome.css">
        
    </head>
    <body>
        <%
            UserDTO user = AuthUtils.getCurrentUser(request);
            if(!AuthUtils.isLoggedIn(request)){
                response.sendRedirect("MainController");
            }else{
                String keyword = (String) request.getAttribute("keyword");
                
        %>
        
        <div class="container">
            <div class ="header-section">
                <h1>Welcome <%=user.getName()%>!</h1>
                
                <div>
                    <a href="MainController?action=logout" class="logout-btn">Logout</a>
                </div>
                
                <% if(AuthUtils.isInstructor(request)){%>
                <a href="InstructorController?action=loadCreateExamForm" class="create-exam-btn">Create new exam</a>
                <% } %>
                
                <div class="table-container">
                    <h2>Exam Categories</h2>
                    
                    <%
                        List<ExamCategoryDTO> categoryList = (List<ExamCategoryDTO>) request.getAttribute("categoryList");
                        if (categoryList != null) {
                    %>
                    
                        <table>
                            <thead>
                                <tr>
                                    <th>Category Name</th>
                                    <th>Description</th>

                                </tr>

                            </thead>

                            <tbody>
                                <% for (ExamCategoryDTO cat : categoryList) { %>
                                <tr>
                                    <td>
                                        <a href="MainController?action=viewExamsByCategory&categoryId=<%=cat.getCategory_id()%>">
                                            <%= cat.getCategory_name() %>
                                        </a>
                                    </td>
                                     
                                    <td><%= cat.getDescription() %></td>
                                </tr>
                                <% } %>
                            </tbody>

                        </table>
                    <%}%>
                    
                </div>
                
                
            </div>
        </div>
        <% } %>
    </body>
</html>
