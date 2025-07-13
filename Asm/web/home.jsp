<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.DTO.*, model.DAO.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FPT Shop - Trang chủ</title>   
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- CSS Home Page -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">   
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}//assets/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">



  <meta charset="UTF-8">
  <title>FPT Shop - Trang chủ</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <!-- CSS chung -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
</head>
<body>

  <jsp:include page="header.jsp"/>

  <%
    // Lấy dữ liệu từ request (đã set trong ProductController)
    List<ProductDTO> products = (List<ProductDTO>) request.getAttribute("products");
    if (products==null) products = new ProductDAO().getAll();
    List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
    if (categories==null) categories = new CategoryDAO().getAll();
    List<BrandDTO> brands = (List<BrandDTO>) request.getAttribute("brands");
    if (brands==null) brands = new BrandDAO().getAll();
  %>

  <div class="main-layout">
    <div class="sidebar">
      <h4><i class="fa-solid fa-bullhorn"></i> Danh mục</h4>
      <%
        for (CategoryDTO c: categories) {
      %>
        <form action="ProductController" method="get">
          <input type="hidden" name="action" value="getProductByCategory"/>
          <input type="hidden" name="categoryId" value="<%=c.getCategoryId()%>"/>
          <input type="submit" value="<%=c.getName()%>"/>
        </form>
      <% } %>

      <h4><i class="fa-solid fa-bullhorn"></i> Thương hiệu</h4>
      <%
        for (BrandDTO b: brands) {
      %>
        <form action="ProductController" method="get">
          <input type="hidden" name="action" value="getProductByBrand"/>
          <input type="hidden" name="brandId" value="<%=b.getBrandId()%>"/>
          <input type="submit" value="<%=b.getName()%>"/>
        </form>
      <% } %>
    </div>

    <div class="container">
      <h3 class="section-title"><i class="fa-solid fa-laptop"></i> Sản phẩm nổi bật</h3>
      <div class="product-grid">
        <% for (ProductDTO p: products) { %>
          <div class="product-card">
            <div class="product-img-wrapper">
              <img src="<%=p.getImageUrl()%>" alt="<%=p.getName()%>"/>
            </div>
            <div class="product-info">
              <h3><%=p.getName()%></h3>
              <p><strong>Giá:</strong> <%=String.format("%,.0f",p.getPrice())%> đ</p>
              <p><strong>Stock:</strong> <%=p.getStock()%></p>
              <form action="MainController" method="get">
                <input type="hidden" name="action" value="viewProductDetails"/>
                <input type="hidden" name="productId" value="<%=p.getProductId()%>"/>
                <input type="submit" value="Xem chi tiết"/>
              </form>
            </div>
          </div>
        <% } %>
      </div>
      <% if (products.isEmpty()) { %>
        <p>Không tìm thấy sản phẩm nào phù hợp.</p>
      <% } %>
    </div>
  </div>

  <jsp:include page="footer.jsp"/>

</body>
</html>
