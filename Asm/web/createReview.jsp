<%-- 
    Document   : createReview
    Created on : Jul 19, 2025, 2:24:01 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DTO.ReviewDTO" %>
<%@page import="model.DTO.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comment</title>
    </head>
    <body>
        <%
            UserDTO user = AuthUtils.getCurrentUser(request);
            String productId = request.getParameter("productId");
            ReviewDTO review = (ReviewDTO) request.getAttribute("review");
            String errorMessage = (String) request.getAttribute("errorMessage");
            String message = (String) request.getAttribute("message");
            boolean isEdit = request.getAttribute("isEdit") != null;
            if (AuthUtils.isLoggedIn(request))
            {
        %>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="<%= isEdit ? "updateReview":"createReview"%>"/>
            <input type="hidden" name="userId" value="<%= (user!=null)?user.getUserId():""%>"/>
            <input type="hidden" name="productId" value="<%= (productId!=null)?productId:""%>"/>
            <label for="rate">Rate</label>
            <select id="rate" name="rate" required="required">
                <option value="empty" <%= (review == null) ? "selected" : "" %>>1*:Very Bad --> 5*:Very Good</option>
                <option value="1" <%= review != null && "1".equals(review.getRating()) ? "selected" : "" %>>1*</option>
                <option value="2" <%= review != null && "2".equals(review.getRating()) ? "selected" : "" %>>2*</option>
                <option value="3" <%= review != null && "3".equals(review.getRating()) ? "selected" : "" %>>3*</option>
                <option value="4" <%= review != null && "4".equals(review.getRating()) ? "selected" : "" %>>4*</option>
                <option value="5" <%= review != null && "5".equals(review.getRating()) ? "selected" : "" %>>5*</option>
            </select>
            <div>
                <label for="comment">Comment</label>
            <textarea name="comment" placeholder="Comment...">
                <%=review != null ? review.getComment() : ""%>
            </textarea>
            </div>
            
            <div> <input type="submit" value="<%= isEdit ? "Save" : "Post" %>"/></div>
            <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <%= errorMessage %>
        <% } %>

        <% if (message != null && !message.isEmpty()) { %>
            <%= message %>
        <% } %>
        </form>
            <%}else{response.sendRedirect("MainController");}%>
    </body>
</html>
