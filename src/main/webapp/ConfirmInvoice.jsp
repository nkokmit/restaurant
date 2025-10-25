<%-- ConfirmInvoice.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Confirm hóa đơn</title>
<!-- injected: modern light style -->
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<div class="container"><div class="card">


<h1 class="title">ConfirmInvoice <span class="pill">JSP</span></h1>
<p class="sub">Trang được tinh chỉnh giao diện nhẹ, giữ nguyên logic.</p>
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
      <textarea id="note-input" name="note" rows="4" cols="50" placeholder="Nhập ghi chú nếu có..." class="input"></textarea>
    </p>
    
    <button type="submit" class="btn">Confirm (chốt hóa đơn)</button>
  </form>
  <p><a href="<%=ctx%>/ingredient/search">Quay lại thêm nguyên liệu</a></p>
</c:if>

</div></div>
</body>
</html>