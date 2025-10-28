<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Tìm nguyên liệu</title>
<!-- injected: modern light style -->
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<div class="container"><div class="card">


<h1 class="title">Nhập hàng | Tìm kiếm món ăn</h1>

<h2>Tìm nguyên liệu của NCC đã chọn</h2>

<form method="get" action="<%=ctx%>/ingredient/search">
  <input name="q" placeholder="Tên nguyên liệu" value="${param.q}" class="input">
  <button type="submit" class="btn">Tìm</button>
  <a href="<%=ctx%>/AddIngredient.jsp">Thêm nguyên liệu mới</a> 
</form>

<h3>Kết quả tìm</h3>
<c:if test="${empty items}">
  <p><i>Không có nguyên liệu phù hợp.</i></p>
</c:if>
  
<c:if test="${not empty items}">
  <table border="1" cellpadding="6" cellspacing="0">
    <tr><th>Tên</th><th>Loại</th><th>Đơn vị</th><th>Giá</th><th>Số lượng</th><th>Thêm</th></tr>
    <c:forEach var="it" items="${items}">
      <tr>
        <td>${it.name}</td>
        <td>${it.type}</td>
        <td>${it.unit}</td>
        <td>${it.price}</td>
        <td>
          <form method="post" action="<%=ctx%>/ingredient/addLine">
            <input type="hidden" name="ingSupId" value="${it.ingredientSupId}">
            <input type="hidden" name="price" value="${it.price}">
            <input name="qty" type="number" step="0.01" min="0.01" required class="input">
        </td>
        <td>
            <button type="submit" class="btn">Thêm dòng</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:if>

<h3>Các dòng đã thêm</h3>
<c:if test="${empty lines}">
  <p><i>Chưa có dòng nào.</i></p>
</c:if>
<c:if test="${not empty lines}">
  <table border="1" cellpadding="6" cellspacing="0">
    <tr><th>#</th><th>Tên NL</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th></tr>
    <c:forEach var="ln" items="${lines}" varStatus="st">
      <tr>
        <td>${st.index + 1}</td>
        <td>${ln.ingredientName}</td>
        <td>${ln.qty}</td>
        <td>${ln.unitPrice}</td>
        <td>${ln.lineTotal}</td>
        <td>
            <form method="post" action="<%=ctx%>/ingredient/removeLine">
                <input type="hidden" name="detailId" value="${ln.detailId}">
                <button type="submit" class="btn">Xóa</button>
        </td>
      </tr>
    </c:forEach>
  </table>
  <p>
    <a href="<%=ctx%>/invoice/confirm">Tiếp tục (Confirm)</a>
  </p>
</c:if>

<p><a href="<%=ctx%>/supplier/search">Đổi nhà cung cấp</a></p>

</div></div>
</body>
</html>
