<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Confirm hóa đơn</title></head>
<body>
<h2>Xác nhận hóa đơn nhập</h2>

<c:if test="${empty lines}">
  <p>Không có dòng nào. <a href="<%=ctx%>/ingredient/search">Quay lại thêm</a></p>
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
      </tr>
    </c:forEach>
  </table>

  <h3>Tổng tiền: ${sumTotal}</h3>

  <form method="post" action="<%=ctx%>/invoice/commit">
    <button type="submit">Confirm (chốt hóa đơn)</button>
  </form>
  <p><a href="<%=ctx%>/ingredient/search">Quay lại thêm nguyên liệu</a></p>
</c:if>
</body>
</html>
