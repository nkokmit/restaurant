<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Tìm nhà cung cấp</title></head>
<body>
<h2>Tìm nhà cung cấp</h2>

<form method="get" action="<%=ctx%>/supplier/search">
  <input name="q" placeholder="Tên nhà cung cấp" value="${param.q}">
  <button type="submit">Tìm</button>
  <a href="<%=ctx%>/supplier/addView">Thêm NCC</a>
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
            <button type="submit">Chọn</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:if>

<p><a href="<%=ctx%>/Import.jsp">Quay lại</a></p>
</body>
</html>
