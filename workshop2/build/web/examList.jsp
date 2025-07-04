
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.ExamDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Exam List:</title>
        <link rel="stylesheet" href="assets/css/welcome.css">
    </head>
    <body>
        <h1>Category:</h1>
        
        <%
          List<ExamDTO> examList = (List<ExamDTO>) request.getAttribute("examList");
          if(examList != null && !examList.isEmpty()){
        %>    
        
        <table>
            <thead>
                <tr>
                    <th>Exam Title</th>
                    <th>Subject</th>
                    <th>Total Marks</th>
                    <th>Duration (Minutes)</th>
                </tr>
            </thead>
            
            <tbody>
                <% for (ExamDTO exam : examList){%>
                <tr>
                    <td><%= exam.getExam_title() %></td>
                    <td><%= exam.getSubject() %></td>
                    <td><%= exam.getTotal_marks() %></td>
                    <td><%= exam.getDuration() %></td>
                </tr>
                <% } %>
            </tbody>
            
        </table>
        
        
        <%
            }else{
        %>
        <p> No exam available !</p>
        <%
            } 
        %>
        
        <a class="back-link" href="MainController?action=welcome">Back</a>
        
    </body>
</html>
