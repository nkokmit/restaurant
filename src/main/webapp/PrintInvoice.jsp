<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Nhập hàng | In hóa đơn</title>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <style>@media print{ .no-print{ display:none } }</style>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<%=ctx%>/css/style.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
<div class="site-brand">RESTAURANT MIT</div>
<div class="container"><div class="card">

  

  <h1 class="title">Nhập hàng | In hóa đơn</h1>

  <h2 style="margin: center">HÓA ĐƠN NHẬP</h2>
  <p><b>Mã hóa đơn:</b> ${invoice.id}</p>
  <p><b>Ngày:</b> <fmt:formatDate value="${invoice.dateIn}" pattern="yyyy-MM-dd" /></p>
  <p><b>Trạng thái:</b>
    <c:choose>
      <c:when test="${invoice.status == 1}">Đã xác nhận</c:when>
      <c:otherwise>Nháp</c:otherwise>
    </c:choose>
  </p>

  <table>
    <thead>
      <tr><th>#</th><th>Tên NL</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th></tr>
    </thead>
    <tbody>
    <c:forEach var="ln" items="${lines}" varStatus="st">
      <tr>
        <td>${st.index + 1}</td>
        <td>${ln.ingredientName}</td>
        <td>${ln.qty}</td>
        <td>
            <fmt:formatNumber value="${ln.unitPrice}" maxFractionDigits="0" />
        </td>
        <td>
            <fmt:formatNumber value="${ln.lineTotal}" maxFractionDigits="0" />
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <h3>Tổng tiền: <fmt:formatNumber value="${total}" maxFractionDigits="0" /></h3>

  
 <div class="actions between" style="margin-top:16px">
    <a class="btn secondary" href="<%=ctx%>/supplier/search">Nhập tiếp</a>
    <button class="btn" type="button" onclick="window.print()">In</button>
</div>      
</div></div>
</body>
</html>
