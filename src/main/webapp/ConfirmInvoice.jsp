<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Nhập hàng | Xác nhận hóa đơn</title>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<%=ctx%>/css/style.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
<div class="site-brand">RESTAURANT MIT</div>
<div class="container"><div class="card">

  <h1 class="title">Nhập hàng | Xác nhận hóa đơn</h1>

  <c:if test="${empty lines}">
    <p>Không có dòng nào. <a class="btn" href="<%=ctx%>/ingredient/search">Quay lại thêm</a></p>
  </c:if>

  <c:if test="${not empty lines}">
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

    <form method="post" action="<%=ctx%>/invoice/confirm">
      <!-- nếu sau này bạn muốn lưu ghi chú vào DB thì thêm cột/logic tương ứng -->
      <p>
        <label for="note-input"><b>Ghi chú (tuỳ chọn):</b></label><br>
        <textarea id="note-input" name="note" rows="3" class="input" placeholder="Nhập ghi chú..."></textarea>
      </p>
   
     
       <div class="actions between">
        <a class="btn secondary" href="<%=ctx%>/ingredient/search">Quay lại thêm</a>
        <button type="submit" class="btn">Chốt hóa đơn</button>
        </div>
    </form>
  </c:if>

</div></div>
</body>
</html>
