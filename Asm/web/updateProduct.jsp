<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="model.DTO.*, java.util.*" %>
<%
    ProductDTO product = (ProductDTO) request.getAttribute("product");
    List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
    List<BrandDTO> brands = (List<BrandDTO>) request.getAttribute("brands");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
                <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/header.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/body.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/footer.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/updateProduct.css">
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <form class="form-container" action="ProductController" method="post" enctype="multipart/form-data">
            <h2>Cập nhật sản phẩm</h2>
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="productId" value="<%= product.getProductId() %>">

            Tên: <input type="text" name="name" value="<%= product.getName() %>" required><br>
            Giá: <input type="number" name="price" step="1000" min="1000" value="<%= product.getPrice() %>" required><br>
            Tồn kho: <input type="number" name="stock" min="0" value="<%= product.getStock() %>" required><br>
            Mô tả: <textarea name="description"><%= product.getDescription() %></textarea><br>

            Danh mục:
            <select name="categoryId" required>
                <% for (CategoryDTO c : categories) { %>
                    <option value="<%= c.getCategoryId() %>" 
                            <%= (c.getCategoryId() == product.getCategoryId()) ? "selected" : "" %>>
                        <%= c.getName() %>
                    </option>
                <% } %>
            </select><br>

            Thương hiệu:
            <select name="brandId" required>
                <% for (BrandDTO b : brands) { %>
                    <option value="<%= b.getBrandId() %>" 
                            <%= (b.getBrandId() == product.getBrandId()) ? "selected" : "" %>>
                        <%= b.getName() %>
                    </option>
                <% } %>
            </select><br>

            Image: <input type="file" name="photo" accept="image/*"><br>
            <div class="current-image">(Current Image: <%= product.getImageUrl() %>)</div>

            <input type="submit" value="Cập nhật">
        </form>
            <jsp:include page="footer.jsp"/>
    </body>
</html>
