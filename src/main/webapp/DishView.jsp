<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết: <c:out value="${dish.name}" default="Không tìm thấy món ăn" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<div class="container">
    <div class="card">

        <c:if test="${not empty dish}">
 
            <h1 class="title">Thông tin món ăn | ${dish.name}</h1>

            <p class="sub">${dish.descr}</p>
            
            <div class="section" ></div>

            <div class="grid-2">
                <div>
                    <label>ID Món ăn</label>
                    <p>${dish.id}</p>
                </div>
                <div>
                    <label>Phân loại</label>
                    <p>${dish.type}</p>
                </div>
                <div>
                    <label>Đơn giá</label>
                    <p>
                        <fmt:formatNumber value="${dish.price}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0" />
                    </p>
                </div>
                <div>
                    <label>Giảm giá</label>
                    <p class="success" style="font-size: 1.2em; font-weight: 500;">
                        <c:choose>
                            <c:when test="${dish.sale > 0}">
                                <fmt:formatNumber value="${dish.sale}" type="percent" maxFractionDigits="1" />
                            </c:when>
                            <c:otherwise>
                                Không có
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
           
            <div class="toolbar">
                 <a href="${pageContext.request.contextPath}/dish/search" class="btn secondary">Quay lại danh sách</a>
            </div>
            
        </c:if>

        <c:if test="${empty dish}">
            <h1 class="title danger">Không tìm thấy món ăn</h1>
            <p class="sub">Món ăn bạn yêu cầu không tồn tại trong hệ thống hoặc đã bị xóa.</p>
            <div class="toolbar">
                 <a href="${pageContext.request.contextPath}/dish/search" class="btn">Quay lại tìm kiếm</a>
            </div>
            
        </c:if>

    </div>
</div>

</body>
</html>