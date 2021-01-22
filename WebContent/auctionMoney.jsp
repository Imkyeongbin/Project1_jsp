<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/team1_nav_custom.css">
<title>Insert title here</title>
<style type="text/css">
td {
	text-align: right;
}

</style>
<script type="text/javascript">
function bc_clk(c_num) {
	location.href = "categories.do?sort_state=1&bc_num="+(c_num-(c_num%100))+"&c_num=" + c_num;
}
</script>
</head>
<body>
	<!--시작 -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-2" style="align-items: center">
				<img id="home_logo" src="image/MAuction1.png" alt="홈로고"
					onclick="location.href='main.do'">
			</div>
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form class="form-inline my-2 my-md-0" action="search.do"
					style="padding-top: 50px;">
					<input class="form-control input-append" type="text"
						name="search_word" placeholder="상품명, 제목, 글내용">
					<button type="submit" class="btn btn-info">검색</button>
				</form>
			</div>
		</div>
	</div>

	<nav id="firstMenu">
		<ul>
			<li class="firstMenuLi"><a class="menuLink" href="#">카테고리</a>
				<ul class="submenu">
					<li><a onclick="bc_clk(101)" class="submenuLink longLink">디지털/가전</a></li>
					<li><a onclick="bc_clk(201)" class="submenuLink longLink">가구/인테리어</a></li>
					<li><a onclick="bc_clk(301)" class="submenuLink longLink">스포츠/레저</a></li>
					<li><a onclick="bc_clk(401)" class="submenuLink longLink">뷰티/미용</a></li>
					<li><a onclick="bc_clk(501)" class="submenuLink longLink">여성잡화</a></li>
					<li><a onclick="bc_clk(601)" class="submenuLink longLink">의류</a></li>
				</ul></li>
			<li class="firstMenuLi"><a class="menuLink"
				href="postingForm.jsp">판매하기</a></li>
			<c:if test="${loginState!=1}">
				<li class="firstMenuLi"><a class="menuLink" href="joinForm.jsp">회원가입</a></li>
				<li class="firstMenuLi"><a class="menuLink"
					href="loginForm.jsp">로그인</a></li>
			</c:if>
			<c:if test="${loginState==1}">
				<li class="firstMenuLi"><a class="menuLink"
					href="mysellList.do">판매목록</a></li>
				<li class="firstMenuLi"><a class="menuLink"
					href="biddingList.do">입찰목록</a></li>
				<li class="firstMenuLi"><a class="menuLink" href="#">${user_email }</a>
					<ul class="submenu">
						<li class="firstMenuLi"><a class="submenuLink longLink"
							href="jjimList.do">찜 목록</a></li>
						<li class="firstMenuLi"><a class="submenuLink longLink"
							href="auctionMoney.do">옥션머니</a></li>
						<li class="firstMenuLi"><a class="submenuLink longLink"
							href="editMyInfoForm.do">회원정보 수정</a></li>
					</ul></li>
				<li class="firstMenuLi"><a class="menuLink" href="logoutPro.do">로그아웃</a></li>
			</c:if>
		</ul>
	</nav>
	
	<!-- Here is main container start -->
	<!-- 컨테이너 시작 -->
	<div class="container" style="text-align: center">
	<br>
		<div id="auctionMoney">
		<table class="table table-striped">
			<tr>
				<th colspan="4" class="text-center">옥션머니 입출금 내역</th>
			</tr>
			<tr>
				<th class="text-center">입출금 일시</th>
				<th class="text-center">출금</th>
				<th class="text-center">입금</th>
				<th class="text-center">상품게시물로 이동</th>
			</tr>

			<c:set var="size" value="${fn:length(amoney)}" />
			<c:forEach var="i" begin="1" end="${size}">
				<tr>
					<td class="text-center">${amoney[size - i].time}</td>
					<td><fmt:formatNumber value="${amoney[size - i].withdraw}" pattern="#,###"/>​</td>
					<td>+<fmt:formatNumber value="${amoney[size - i].deposit}" pattern="#,###"/>​</td>
					<td class="text-center"><a
						href="viewCount.do?b_num=${amoney[size - i].b_num}">${amoney[size - i].b_num}
					</a></td>
				</tr>
			</c:forEach>

			<tr>
				<th class="text-center">잔액</th>
				<td></td>
				<td><fmt:formatNumber value="${balance}" pattern="#,###"/></td>
				<td></td>
			</tr>

		</table>

		<input type="button" value="충전하기" class="btn btn-default"
			onclick="location.href='chargeForm.jsp'">
			 <input	type="button" value="출금하기" onclick="location.href='withdrawForm.jsp'" class="btn btn-default">
		</div>
	</div>
	<br><br>
	<div class="footer">
		베이는 마켓플레이스(오픈마켓) 상품, 거래정보 및 거래 등에 대하여 책임을 지지 않습니다. <br> 기타사항에 대한
		문의 : admin@admin.com / Copyright © MarCatBay Corp. 2010-2020 All
		Rights Reserved.
	</div>
</body>
</html>