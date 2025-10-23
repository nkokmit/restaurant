<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Đăng nhập</title></head>
<body>
<h2>Đăng nhập</h2>
<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>
<form method="post" action="<%=ctx%>/auth/login">
  <label>User:</label><input name="u" required>
  <label>Pass:</label><input type="password" name="p" required>
  <button type="submit">Đăng nhập</button>
</form>
</body>
</html>
