<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.DTO.*, model.DAO.*" %>
<%@ page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>FPT Shop – Trang chủ</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Icon & CSS chung -->
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/header.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/body.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/footer.css">
    </head>

<body>

    <body>


        <!--HEADER CHUNG  -->
        <jsp:include page="header.jsp"/>

        <%
          /* --------- Lấy dữ liệu hiển thị ---------- */
          List<ProductDTO> products   =
                  (List<ProductDTO>) request.getAttribute("products");
          if (products == null) products = new ProductDAO().getAll();

          List<CategoryDTO> categories =
                  (List<CategoryDTO>) request.getAttribute("categories");
          if (categories == null) categories = new CategoryDAO().getAll();

          List<BrandDTO> brands =
                  (List<BrandDTO>) request.getAttribute("brands");
          if (brands == null) brands = new BrandDAO().getAll();
        %>

        <div class="main-layout">
            <!-- ===== SIDEBAR ===== -->
            <aside class="sidebar">
                <h4><i class="fa-solid fa-bullhorn"></i> Danh mục</h4>
                <%
                  for (CategoryDTO c : categories) {
                %>
                <form action="${pageContext.request.contextPath}/ProductController"
                      method="get">
                    <input type="hidden" name="action" value="getProductByCategory">
                    <input type="hidden" name="categoryId" value="<%= c.getCategoryId() %>">
                    <input type="submit" value="<%= c.getName() %>">
                </form>
                <% } %>

                <h4><i class="fa-solid fa-bullhorn"></i> Thương hiệu</h4>
                <%
                  for (BrandDTO b : brands) {
                %>
                <form action="${pageContext.request.contextPath}/ProductController"
                      method="get">
                    <input type="hidden" name="action" value="getProductByBrand">
                    <input type="hidden" name="brandId" value="<%= b.getBrandId() %>">
                    <input type="submit" value="<%= b.getName() %>">
                </form>
                <% } %>
            </aside>

            <!-- ===== DANH SÁCH SẢN PHẨM ===== -->
            <main class="container">
                <h3 class="section-title">
                    <i class="fa-solid fa-laptop"></i> Sản phẩm nổi bật
                </h3>
                <!--create -->
                <% 
                    Object user = session.getAttribute("user");
                    if (user != null && AuthUtils.isAdmin(request)) {
                %>
                    <form action="ProductController" method="get">
                        <input type="hidden" name="action" value="showCreateForm">
                        <button type="submit" style="padding: 8px 16px; font-weight: bold; background: #4CAF50; color: white; border: none; border-radius: 5px;">
                            <i class="fa fa-plus"></i> Tạo sản phẩm mới
                        </button>
                    </form>
                <% } %>

                <div class="product-grid">
                    <% for (ProductDTO p : products) { %>
                    <div class="product-card">
                        <div class="product-img-wrapper">
                            <img src="<%= p.getImageUrl() %>" alt="<%= p.getName() %>">
                        </div>

                        <div class="product-info">
                            <h3><%= p.getName() %></h3>
                            <p><strong>Giá:</strong>
                                <%= String.format("%,.0f", p.getPrice()) %> đ</p>
                            <p><strong>Stock:</strong> <%= p.getStock() %></p>

                            <!-- ==== NÚT HÀNH ĐỘNG ==== -->
                            <div class="product-actions">
                                <!-- Thêm giỏ -->
                                <form action="${pageContext.request.contextPath}/CartController"
                                      method="post" style="display:inline;">
                                    <input type="hidden" name="action"    value="addToCart">
                                    <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                                    <input type="hidden" name="unitPrice" value="<%= p.getPrice() %>">
                                    <button type="submit" class="btn-cart"
                                            title="Thêm vào giỏ hàng">
                                        <i class="fas fa-shopping-cart"></i>
                                    </button>
                                </form>

                                <!--Chi tiết -->
                                <form action="${pageContext.request.contextPath}/MainController"
                                      method="get" style="display:inline;">
                                    <input type="hidden" name="action"    value="viewProductDetails">
                                    <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                                    <button type="submit" class="btn-detail">Chi tiết</button>
                                </form>
                                
                                 <% 
                                    
                                    if (user != null && AuthUtils.isAdmin(request)) {
                                %>
                                    <form action="ProductController" method="get" style="display:inline;">
                                        <input type="hidden" name="action" value="showUpdateForm">
                                        <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                                        <button type="submit" class="btn-edit"
                                                style="background: #FFA500; color: white; border: none; padding: 6px 12px; border-radius: 4px; margin-left: 4px;">
                                            Cập nhật
                                        </button>
                                    </form>

                                <% } %>    
                                    
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>

                <% if (products.isEmpty()) { %>
                <p>Không tìm thấy sản phẩm nào phù hợp.</p>
                <% } %>
            </main>
        </div>

        <!-- FOOTER CHUNG -->
        <jsp:include page="footer.jsp"/>

    </body>
</html>
