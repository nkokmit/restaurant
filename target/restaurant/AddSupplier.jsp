<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Thêm nhà cung cấp</title></head>
<body>
<h2>Thêm nhà cung cấp</h2>
<form method="post" action="<%=ctx%>/supplier/add">
  <div><label>Tên: </label><input name="name" required></div>
  <div><label>Địa chỉ: </label><input name="addr"></div>
  <div><label>Điện thoại: </label><input name="tel"></div>
  <div><label>Email: </label><input name="email"></div>
  <button type="submit">Lưu</button>
  <a href="<%=ctx%>/supplier/search">Hủy</a>
</form>
</body>
</html>
