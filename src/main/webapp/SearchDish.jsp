<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Tìm món ăn</title>
<!-- injected: modern light style -->
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<div class="container">
    <div class="card">
        <h1 class="title">Xem món ăn | Tìm kiếm món ăn </h1>
        <form method="get" action="<%=ctx%>/dish/search">
          <input name="q" placeholder="Tên món ăn" value="${param.q}" class="input">
          <button type="submit" class="btn">Tìm</button>
        </form>

        <c:if test="${empty dishes}">
          <p><i>Không có kết quả.</i></p>
        </c:if>

        <c:if test="${not empty dishes}">
          <table border="1" cellpadding="6" cellspacing="0">
            <tr><th>ID</th><th>Tên</th><th>Loại</th><th>Giá</th><th>Miêu tả</th><th>Giảm giá</th></tr>
            <c:forEach var="d" items="${dishes}">
              <tr>
                <td>${d.id}</td>
                <td>${d.name}</td>
                <td>${d.type}</td>
                <td>${d.price}</td>
                <td>${d.descr}</td>
                <td><fmt:formatNumber value="${d.sale}" type="percent" maxFractionDigits="1" /></td>
                <td>
                  <form method="get" action="<%=ctx%>/dish/view">
                    <input type="hidden" name="dishId" value="${d.id}">
                    <button type="submit" class="btn">Xem</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </table>
        </c:if>

        <p><a href="<%=ctx%>/Home.jsp">Quay lại</a></p>

    </div>
</div>
</body>
</html>
