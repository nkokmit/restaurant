<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Đăng nhập</title>
<!-- injected: modern light style -->
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<div class="container"><div class="card">


<h1 class="title">login <span class="pill">JSP</span></h1>
<p class="sub">Trang được tinh chỉnh giao diện nhẹ, giữ nguyên logic.</p>
<h2>Đăng nhập</h2>
<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>
<form method="post" action="<%=ctx%>/auth/login">
  <label>User:</label><input name="u" required class="input">
  <label>Pass:</label><input type="password" name="p" required class="input">
  <button type="submit" class="btn">Đăng nhập</button>
</form>

</div></div>
</body>
</html>
