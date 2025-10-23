<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Import Menu</title></head>
<body>
<h2>Quản lý nhập hàng</h2>
<form method="get" action="<%=ctx%>/supplier/search">
  <button type="submit">Nhập hàng (tìm nhà cung cấp)</button>
</form>
</body>
</html>
