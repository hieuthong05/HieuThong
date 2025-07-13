<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Error</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/body.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
</head>
<body>
  <jsp:include page="header.jsp"/>

  <div style="max-width:600px;margin:50px auto;background:#fff;padding:20px;border-radius:8px;">
    <h2>Đã có lỗi xảy ra</h2>
    <p style="color:red;"><%= request.getAttribute("errorMessage") %></p>
    <a href="home.jsp">Quay về trang chủ</a>
  </div>

  <jsp:include page="footer.jsp"/>
</body>
</html>
