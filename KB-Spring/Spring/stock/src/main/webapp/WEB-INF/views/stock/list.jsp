<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<%@include file="../layouts/header.jsp"%>
<h1 class="page-header my-4"><i class="fa-solid fa-arrow-trend-up"></i> 주식 목록</h1>
<table class="table table-hover">
  <thead>
  <tr>
    <th style="width: 60px">No</th>
    <th>종목 코드</th>
    <th style="width: 100px">종목 이름</th>
    <th style="width: 130px">현재가</th>
    <th style="width: 130px">등록일</th>
  </tr>
  </thead>
  <body>
  <c:forEach var="stock" items="${list}">
    <tr>
      <td>${stock.no}</td>
      <td>
        ${stock.stockCode}
      </td>
      <td>
          <a href="get?no=${stock.no}">${stock.stockName}</td>
      <td>
        ${stock.price}
      </td>
      <td>
        <fmt:formatDate pattern="yyyy-MM-dd" value="${stock.regDate}"/>
      </td>
    </tr>
  </c:forEach>
  </body>
</table>
<div class="text-end">
  <a href="create" class="btn btn-primary">
    <i class="far fa-edit"></i>
    새 종목 등록
  </a>
</div>
<%@include file="../layouts/footer.jsp"%>