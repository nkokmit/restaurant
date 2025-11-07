<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Nhập hàng | Tìm kiếm nguyên liệu</title>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<%=ctx%>/css/style.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
  <div class="site-brand">RESTAURANT MIT</div>

  <div class="layout-main">

    <div class="card">
      <h1 class="title">Nhập hàng | Tìm kiếm nguyên liệu</h1>

      <h2>Tìm theo nhà cung cấp đã chọn</h2>
      <form method="get" action="<%=ctx%>/ingredient/search" class="toolbar">
        <input name="q" placeholder="Tên nguyên liệu" value="${param.q}" class="input">
        <button type="submit" class="btn">Tìm</button>
        <a class="btn" href="<%=ctx%>/AddIngredient.jsp">Thêm nguyên liệu mới</a>
      </form>

      <h3>Kết quả tìm</h3>
      <c:if test="${empty items}">
        <p class="muted"><i>Không có nguyên liệu phù hợp.</i></p>
      </c:if>

      <c:if test="${not empty items}">
        <table>
          <thead>
            <tr><th>Tên</th><th>Loại</th><th>Đơn vị</th><th>Giá NCC(VNĐ)</th><th style="width:260px">Thêm vào đơn</th></tr>
          </thead>
          <tbody>
            <c:forEach var="it" items="${items}">
              <tr>
                <td>${it.name}</td>
                <td>${it.type}</td>
                <td>${it.unit}</td>
                <td>
                    <fmt:formatNumber value="${it.price}" maxFractionDigits="0" />
                </td>
                <td>
                  <form method="post" action="<%=ctx%>/ingredient/addLine" class="toolbar">
                    <input type="hidden" name="ingSupId" value="${it.ingredientSupId}">
                    <input type="number" name="qty" class="input" step="0.01" min="0.01" placeholder="SL" required>
                    <input type="hidden" name="price" value="${it.price}">
                    <input type="hidden" name="ingName" value="${it.name}">
                    <button type="submit" class="btn btn-sm">Thêm</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

      <div class="actions between" style="margin-top:14px">
        <a class="btn secondary" href="<%=ctx%>/supplier/search">Đổi nhà cung cấp</a>
        <a class="btn" href="<%=ctx%>/invoice/confirm">Tiếp tục</a>
      </div>
    </div><!-- /.card -->

    <aside class="side-panel">
      <h3>Các dòng đã thêm</h3>

      <c:if test="${empty lines}">
        <p class="muted"><i>Chưa có dòng nào.</i></p>
      </c:if>

      <c:if test="${not empty lines}">
        <table>
          <thead>
            <tr><th>#</th><th>Tên NL</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th><th></th></tr>
          </thead>
          <tbody>
            <c:forEach var="ln" items="${lines}" varStatus="st">
              <tr>
                <td>${st.index + 1}</td>
                <td>${ln.ingredientName}</td>
                <td>${ln.quantity}</td>
                <td>
                    <fmt:formatNumber value="${ln.unitPrice}" maxFractionDigits="0" />
                </td>
                <td>
                    <fmt:formatNumber value="${ln.lineTotal}" maxFractionDigits="0" />
                </td>
                <td>
                  <form method="post" action="<%=ctx%>/ingredient/removeLine">
                    <input type="hidden" name="idx" value="${st.index}">
                    <button type="submit" class="btn btn-sm danger">Xóa</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>
    </aside>

  </div><!-- /.layout-main -->
</body>

</html>
