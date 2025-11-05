<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Đăng nhập</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css?v=<%=System.currentTimeMillis()%>">

</head>
<body>
<div class="site-brand">RESTAURANT MIT</div>
<div class="container"><div class="card">
<h1 class="title">Đăng nhập</h1>

<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>
<form method="post" action="<%=ctx%>/auth/login">
  <label>Tài khoản</label><input name="u" required class="input">
  <label>Mật khẩu</label><input type="password" name="p" required class="input">
  <button type="submit" class="btn">Đăng nhập</button>
</form>

</div></div>
</body>
</html>
