<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Thêm nguyên liệu</title></head>
<body>
<h2>Thêm nguyên liệu & ánh xạ với NCC hiện tại</h2>
<form method="post" action="<%=ctx%>/ingredient/addNewIngredient"> <!-- TODO: tạo endpoint nếu muốn -->
  <div><label>Tên: </label><input name="name" required></div>
  <div>
      <label>Loại: </label>
      <select name="type" required>
        <option value="Lương thực">Lương thực</option>
        <option value="Gia vị">Gia vị</option>
        <option value="Thực phẩm">Thực phẩm</option>
        <option value="Tươi sống">Tươi sống</option>
        <option value="Rau củ">Rau củ</option>
        <option value="Khô">Khô</option>
        <option value="Đồ uống">Đồ uống</option>
    </select>
  </div>
  <div>
      <label>Đơn vị: </label>
      <select name="unit" required>
        <option value="kg">kg</option>
        <option value="lít">lít</option>
        <option value="chai">chai</option>
        <option value="vỉ">vỉ</option>
        <option value="bó">bó</option>
        <option value="hộp">hộp</option>
    </select>
  </div>
  <div><label>Giá: </label><input name="price" type="number" step="0.01" min="0" required></div>
  <button type="submit">Lưu</button>
  <a href="<%=ctx%>/ingredient/search">Hủy</a>
</form>
</body>
</html>
