<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Thêm nhà cung cấp</title>
<!-- injected: modern light style -->
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container"><div class="card">
<h1 class="title">AddSupplier <span class="pill">JSP</span></h1>
<p class="sub">Trang được tinh chỉnh giao diện nhẹ, giữ nguyên logic.</p>
<h2>Thêm nhà cung cấp</h2>
<form method="post" action="<%=ctx%>/supplier/add">
  <div><label>Tên: </label><input name="name" required class="input"></div>
  <div><label>Địa chỉ: </label><input name="addr" class="input"></div>
  <div><label>Điện thoại: </label><input name="tel" class="input"></div>
  <div><label>Email: </label><input name="email" class="input"></div>
  <button type="submit" class="btn">Lưu</button>
  <a href="<%=ctx%>/supplier/search">Hủy</a>
</form>

</div></div>
</body>
</html>
