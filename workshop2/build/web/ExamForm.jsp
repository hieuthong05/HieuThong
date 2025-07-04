
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.ExamCategoryDTO" %>
<%@ page import="model.ExamDTO" %>
<%@ page import="utils.AuthUtils" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Exam </title>
        <link rel="stylesheet" href="assets/css/examForm.css">
        
    </head>
    <%
            if(AuthUtils.isInstructor(request)){
                ExamDTO exam = (ExamDTO) request.getAttribute("exam");
                String checkError = (String) request.getAttribute("checkError");
                String message = (String) request.getAttribute("message");
        %>
        <h1>Exam Form</h1>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="createNewExams"/>
            
            <div>
                <label for="exam_title">Exam title*</label> 
                <input type="text" name="exam_title" id="examtitle" required="required"
                       value="<%=exam!=null?exam.getExam_title():""%>"/>
            </div>
            <div>
                <label for="subject">Subject*</label> 
                <input type="text" id="subject" name="subject" required />

            </div>
           <div>
                <label for="category_id">Category*</label>
                <select id="category_id" name="category_id" required>
                    <%
                        List<ExamCategoryDTO> categoryList = (List<ExamCategoryDTO>) request.getAttribute("categoryList");
                        if (categoryList != null) {
                            for (ExamCategoryDTO cat : categoryList) {
                    %>
                    <option value="<%= cat.getCategory_id() %>"><%= cat.getCategory_name() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div>
                <label for="total_marks">Total Marks *</label>
                <input type="number" id="total_marks" name="total_marks" min="1" required />
            </div>

            <div>
                <label for="duration">Duration (minutes) *</label>
                <input type="number" id="duration" name="duration" min="1" required />
            </div>
                
            <div>
                <input type="submit" value="Create Exam" />
                <input type="reset" value="Reset" />
            </div>
                
        </form>
            
        <% if (checkError != null && !checkError.isEmpty()) { %>
            <div class="error"><%= checkError %></div>
        <% } %>
        
        <% if (message != null && !message.isEmpty()) { %>
            <div class="message"><%= message %></div>
        <% } %>

        <%
    }else{
        %>
        <%=AuthUtils.getAccessDeniedMessage(" Exam form page ")%>
        <%
    }
        %>
</html>
