<%-- ConfirmInvoice.jsp --%>
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
    <%-- ... phần bảng giữ nguyên ... --%>
  </table>

  <h3>Tổng tiền: ${sumTotal}</h3>

  <form method="post" action="<%=ctx%>/invoice/commit">
    <%-- THÊM KHUNG NHẬP NOTE Ở ĐÂY --%>
    <p>
      <label for="note-input"><b>Ghi chú:</b></label><br>
      <textarea id="note-input" name="note" rows="4" cols="50" placeholder="Nhập ghi chú nếu có..."></textarea>
    </p>
    
    <button type="submit">Confirm (chốt hóa đơn)</button>
  </form>
  <p><a href="<%=ctx%>/ingredient/search">Quay lại thêm nguyên liệu</a></p>
</c:if>
</body>
</html>