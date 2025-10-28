<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Thêm nguyên liệu</title>
<!-- injected: modern light style -->
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<div class="container"><div class="card">


<h1 class="title">Nhập hàng | Thêm nguyên liệu</h1>
<h2>Nhập thông tin nguyên liệu</h2>
<form method="post" action="<%=ctx%>/ingredient/addNewIngredient"> 
  <div><label>Tên: </label><input name="name" required class="input"></div>
  <div>
      <label>Loại: </label>
      <select name="type" required class="input">
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
      <select name="unit" required class="input">
        <option value="kg">kg</option>
        <option value="lít">lít</option>
        <option value="chai">chai</option>
        <option value="vỉ">vỉ</option>
        <option value="bó">bó</option>
        <option value="hộp">hộp</option>
    </select>
  </div>
  <div><label>Giá: </label><input name="price" type="number" step="0.01" min="0" required class="input"></div>
  <button type="submit" class="btn">Lưu</button>
  <a href="<%=ctx%>/ingredient/search">Hủy</a>
</form>

</div></div>
</body>
</html>
