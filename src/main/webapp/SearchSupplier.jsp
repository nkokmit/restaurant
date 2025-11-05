<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Tìm nhà cung cấp</title>
<!-- injected: modern light style -->
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css?v=<%=System.currentTimeMillis()%>">

</head>
<body>
<div class="site-brand">RESTAURANT MIT</div>    
<div class="container"><div class="card">


<h1 class="title">Nhập hàng | Tìm nhà cung cấp </h1>
<form method="get" action="<%=ctx%>/supplier/search" class="toolbar">
  <input name="q" placeholder="Tên nhà cung cấp" value="${param.q}" class="input">
  <button type="submit" class="btn">Tìm</button>
  <a class="btn" href="<%=ctx%>/supplier/addView">Thêm NCC</a>
  
</form>

<c:if test="${empty suppliers}">
  <p><i>Không có kết quả.</i></p>
</c:if>

<c:if test="${not empty suppliers}">
  <table border="1" cellpadding="6" cellspacing="0">
    <tr><th>ID</th><th>Tên</th><th>Địa chỉ</th><th>Điện thoại</th><th>Email</th><th>Chọn</th></tr>
    <c:forEach var="s" items="${suppliers}">
      <tr>
        <td>${s.id}</td>
        <td>${s.name}</td>
        <td>${s.addr}</td>
        <td>${s.tel}</td>
        <td>${s.email}</td>
        <td>
          <form method="post" action="<%=ctx%>/ingredient/start">
            <input type="hidden" name="supplierId" value="${s.id}">
            <button type="submit" class="btn">Chọn</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:if>

 <div class="actions between">
  <a class="btn secondary" href="<%=ctx%>/Home.jsp">Quay lại</a>
  
</div>
</div>

</body>
</html>
