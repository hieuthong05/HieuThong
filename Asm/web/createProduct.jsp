<%-- 
    Document   : createProduct
    Created on : Jul 14, 2025, 7:10:11 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2>Thêm sản phẩm</h2>
        <form action="ProductController" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="create">

            Tên sản phẩm: <input type="text" name="name" required><br>
            Giá: <input type="number" name="price" step="0.01" required><br>
            Tồn kho: <input type="number" name="stock" required><br>
            Mô tả: <textarea name="description"></textarea><br>
            Danh mục ID: <input type="number" name="categoryId" required><br>
            Thương hiệu ID: <input type="number" name="brandId" required><br>

            Hình ảnh: <input type="file" name="photo" accept="image/*" required><br><br>

            <input type="submit" value="Tạo sản phẩm">
        </form>

    </body>
</html>
