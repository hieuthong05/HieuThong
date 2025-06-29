<%-- 
    Document   : dashboard
    Created on : Jun 20, 2025, 7:23:23 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="java.util.List"%>
<%@page import="model.ProjectDAO"%>
<%@page import="model.ProjectDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project Dashboard</title>
        <style>
            table {
                border-collapse: collapse;
            }

            table, th, td {
                border: 1px solid black;
            }

            th, td {
                padding: 8px;
            }
        </style>
    </head>
    <body>
        
        <%  
            UserDTO user = AuthUtils.getCurrentUser(request);
            Object objList = session.getAttribute("projects");
            List<ProjectDTO> list = (objList != null)? (List<ProjectDTO>)objList:null;
            String message = (String) request.getAttribute("message");
            
            if (AuthUtils.isLoggedIn(request))
            {
        %>
        <h1>Startup Project Dashboard</h1>
        <h2 style="color: purple">Hi <%=user.getName()%> ^^</h2>
        
        <%=message != null? message:""%>
             
        <%
            if (list == null || list.isEmpty())
            {
        %>
            <p>No Projects.</p>
        <%
            }
            else
            {
        %>
        <br/>
        <h3>View Startup Projects</h3>       
        <table>
            
            <thead>
                <th>Project Name</th>
                <th>Description</th>
                <th>Status</th>
                <th>Estimated Launch</th>
            </thead>
            
            <tbody>
                <%
                    for (ProjectDTO proj : list)
                    {
                %>
                    <tr>
                        <td> <%=proj.getProjName()%> </td>
                        <td> <%=proj.getDescription() %> </td>
                        <td> <%=proj.getStatus() %> </td>
                        <td> <%=proj.getEst() %> </td>
                    </tr> 
                <%
                    }
                %>
            </tbody>
            
        </table>
        <br/>    
        <%                
            }
        %>
        
            <form action="MainController" method="post"> 
            <input type="hidden" name="action" value="logout"/>
            <input type="submit" value="Logout"/>
        </form>
        <hr/>
            <%
                if (AuthUtils.isFounder(request))
                {
                String keyword = (String) request.getAttribute("keyword");
            %>
            <div>
            <form action="projectForm.jsp" method="post">
                <input type="submit" value="Create New Project"/>
            </form>
            </div>    
                <br/>
                <div>
                   Search project by name:
                   <form action="MainController" method="post">
                       <input type="hidden" name="action" value="searchProjectName" />
                       <input type="text" name="kw" value="<%=keyword != null? keyword:"" %>" placeholder="Enter project name..."/>
                       <input type="submit" value="Search"/>
                   </form>
                </div>
                
                <%
                   List<ProjectDTO> searchList = (List<ProjectDTO>) request.getAttribute("searchList");
                   
                   if ((searchList != null && searchList.isEmpty()))
                   {
                %>
                   No Projects match the keyword! ^^
                <%
                   }
                   else if (searchList != null && !searchList.isEmpty())
                   {
                %>
                    <br/>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th>Estimated Launch</th>
                                <%
                                    if (AuthUtils.isFounder(request))
                                    {
                                %>
                                        <th>Update Project Status</th>
                                <%
                                    }
                                %>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            for (ProjectDTO proj : searchList)
                            {
                        %>
                            <tr>
                                <td> <%=proj.getProjId()%> </td>
                                <td> <%=proj.getProjName()%> </td>
                                <td> <%=proj.getDescription() %> </td>
                                <td> <%=proj.getStatus() %> </td>
                                <td> <%=proj.getEst() %> </td>
                                <%
                                    if (AuthUtils.isFounder(request))
                                    {
                                %>
                                <td>
                                    <div>
                                        <form action="MainController" method="post">
                                            <input type="hidden" name="action" value="editProject"/>
                                            <input type="hidden" name="projId" value="<%=proj.getProjId()%>"/>
                                            <input type="hidden" name="kw" value="<%=keyword != null? keyword:"" %>"/>
                                            <input type="submit" value="Edit"/>
                                        </form>
                                    </div>
                                </td>  
                                <%
                                    }
                                %>
                            </tr> 
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                
                <%
                   }
                %>
                
                
            <%
                }
            %>
            
        <%
            }
            else
            {
                response.sendRedirect("MainController");
            }
        %>

    </body>
</html>

