<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>In hóa đơn</title>
<style>
  @media print {
    .no-print { display: none; }
  }
</style>

<!-- injected: modern light style -->
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<div class="container"><div class="card">


<h1 class="title">Nhập hàng | In hóa đơn</h1>

<div class="no-print">
  <a href="<%=ctx%>/supplier/search">Nhập tiếp</a>
  <button onclick="window.print()">In</button>
</div>

<h2>HÓA ĐƠN NHẬP</h2>
<p><b>Mã hóa đơn:</b> ${invoice.id}</p>
<p><b>Ngày:</b> ${invoice.dateIn}</p>
<p><b>Trạng thái:</b> <c:choose>
    <c:when test="${invoice.status == 1}">Đã xác nhận</c:when>
    <c:otherwise>Nháp</c:otherwise>
  </c:choose>
</p>

<table border="1" cellpadding="6" cellspacing="0">
  <tr><th>#</th><th>Tên NL</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th></tr>
  <c:forEach var="ln" items="${lines}" varStatus="st">
    <tr>
      <td>${st.index + 1}</td>
      <td>${ln.ingredientName}</td>
      <td>${ln.qty}</td>
      <td>${ln.unitPrice}</td>
      <td>${ln.lineTotal}</td>
    </tr>
  </c:forEach>
</table>

</div></div>
</body>
</html>
