<%-- 
    Document   : projectForm
    Created on : Jun 24, 2025, 10:00:56 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<%@page import="model.ProjectDTO" %>
<%--<%@page import="java.sql.Date" %>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project Form</title>
    </head>
    <body>
            <%
                if (AuthUtils.isFounder(request))
                {
                    ProjectDTO proj = (ProjectDTO) request.getAttribute("proj");
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    String message = (String) request.getAttribute("message");
                    String keyword = (String) request.getAttribute("keyword");
                    boolean isEdit = request.getAttribute("isEdit") != null;
            %>
                <h1><%=isEdit? "Update Project Status":"Create New Project" %></h1>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="<%=isEdit? "updateProjectStatus":"createProject"%>"/>
                    
                    <% if(keyword != null) { %>
                    <input type="hidden" name="kw" value="<%=keyword%>"/>
                <% } %>
                    
                    <div>
                        <label for="id">Project ID*</label>
                        <input type="text" name="id" required="required" placeholder="Enter project ID..."
                               value="<%=proj != null? proj.getProjId():"" %>"
                               <%=isEdit? "readonly":""%>/>
                    </div> 
                    
                    <div>
                        <label for="name">Project Name*</label>
                        <input type="text" name="name" required="required" placeholder="Enter project name..."
                               value="<%=proj != null? proj.getProjName():"" %>"
                               <%=isEdit? "readonly":""%>/>
                    </div>
                    
                    <div>
                        <label for="description">Description</label>
                        <textarea name="description" placeholder="Enter description..."
                                  <%=isEdit? "readonly":""%>>
                            <%=proj != null? proj.getDescription():""%>
                        </textarea>
                    </div>
                    
                    <div>
                        <label for="status">Status</label>
                        <select id="status" name="status" required="required">
                          <option value="Empty" selected>Choose Status</option>
                          <option value="Ideation" <%=proj != null? proj.getStatus().equals("Ideation")? "selected":"" :"" %>>Ideation</option>
                          <option value="Development" <%=proj != null? proj.getStatus().equals("Development")? "selected":"" :"" %>>Development</option>
                          <option value="Launch" <%=proj != null? proj.getStatus().equals("Launch")? "selected":"" :"" %>>Launch</option>
                          <option value="Scaling" <%=proj != null? proj.getStatus().equals("Scaling")? "selected":"" :"" %>>Scaling</option>
                        </select>
                    </div>
                    
                    <div>
                        <label for="est">Estimated Launch Date</label>
                        <input type="date" id="launch" name="est" required="required"
                               value="<%=proj != null? proj.getEst().toString() :"" %>"
                               <%=isEdit? "readonly":""%>/>               
                    </div>
                    
                    <div>
                        <input type="submit" value="<%=isEdit? "Update Project":"Add Project"%>"/>
                        <%
                            if (!isEdit)
                            {
                        %>
                        <input type="reset" value="Reset Form"/>
                        <%
                            }
                        %>
                    </div>
                </form>
                <%=errorMessage != null? errorMessage:"" %>
                <%=message != null? message:"" %>
            <%
                }
                else
                {
            %>
                <%=AuthUtils.getAccessDeniedMessage("Create Project Form") %>
            <%
                }
            %>
    </body>
</html>
