<%-- 
    Document   : createProduct
    Created on : Jul 14, 2025, 7:10:11 AM
    Author     : ASUS
--%>
<%@ page import="model.DTO.*, java.util.*" %>
<%
    List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
    List<BrandDTO> brands = (List<BrandDTO>) request.getAttribute("brands");
%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/header.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/body.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/footer.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/createProduct.css">
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        
        <div class="form-container"> 
            <h2>Add product</h2>
            <form action="ProductController" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="create">

                Tên: <input type="text" name="name" required><br>
                Giá: <input type="number" name="price" step="1000" min="1000" required><br>
                Tồn kho: <input type="number" name="stock" min="0" required><br>
                Mô tả: <textarea name="description"></textarea><br>


                Danh mục:
                <select name="categoryId" required>
                    <% for (CategoryDTO c : categories) { %>
                        <option value="<%= c.getCategoryId() %>"><%= c.getName() %></option>
                    <% } %>
                </select><br>

                Thương hiệu:
                <select name="brandId" required>
                    <% for (BrandDTO b : brands) { %>
                        <option value="<%= b.getBrandId() %>"><%= b.getName() %></option>
                    <% } %>
                </select><br>

                Ảnh: <input type="file" name="photo" accept="image/*" required><br><br>

                <input type="submit" value="Thêm sản phẩm">
            </form>
        </div>
            <jsp:include page="footer.jsp"/>
    </body>
</html>
