<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Home Menu</title>
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


<h1 class="title">HOME </h1>

<div class="button-container">
  <form method="get" action="<%=ctx%>/supplier/search">
    <button type="submit" class="btn">Nhập hàng</button>
  </form>
  <form method="get" action="<%=ctx%>/dish/search">
    <button type="submit" class="btn">Tìm kiếm món ăn</button>
  </form>
</div>
<div style="margin-top:40px; display:flex; justify-content:flex-end">
    <form method="post" action="<%=ctx%>/auth/logout">
        <button type="submit" class="btn secondary">Đăng xuất</button>
    </form>
</div>
</div>
</div>
</body>
</html>
