<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Import Menu</title></head>
<body>
<h2>HOME</h2>
<form method="get" action="<%=ctx%>/supplier/search">
  <button type="submit">Nhập hàng </button>
</form>
<form method="get" action="<%=ctx%>/supplier/search">
  <button type="submit">Tìm kiếm món ăn</button>
</form>
</body>
</html>
