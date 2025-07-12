<%@ page import="model.DTO.ProductDTO" %>
<%
    ProductDTO product = (ProductDTO) request.getAttribute("product");
%>

<h2>Product Details</h2>
<% if (product != null) { %>
    <p><strong>Name:</strong> <%= product.getName() %></p>
    <p><strong>Brand ID:</strong> <%= product.getBrandId() %></p>
    <p><strong>Category ID:</strong> <%= product.getCategoryId() %></p>
    <p><strong>Price:</strong> $<%= product.getPrice() %></p>
    <p><strong>Stock:</strong> <%= product.getStock() %> units</p>
    <p><strong>Description:</strong> <%= product.getDescription() %></p>
    <img src="<%= product.getImageUrl() %>" alt="Product Image" width="300px"/>
<% } else { %>
    <p>Product not found.</p>
<% } %>
