<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Thêm nguyên liệu</title></head>
<body>
<h2>Thêm nguyên liệu & ánh xạ với NCC hiện tại</h2>
<form method="post" action="<%=ctx%>/ingredient/addNew"> <!-- TODO: tạo endpoint nếu muốn -->
  <div><label>Tên: </label><input name="name" required></div>
  <div><label>Loại: </label><input name="type"></div>
  <div><label>Đơn vị: </label><input name="unit" required></div>
  <div><label>Giá: </label><input name="price" type="number" step="0.01" min="0" required></div>
  <button type="submit">Lưu</button>
  <a href="<%=ctx%>/ingredient/search">Hủy</a>
</form>
</body>
</html>
