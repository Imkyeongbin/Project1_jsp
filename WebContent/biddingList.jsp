<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String context = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>1조프로젝트 Home</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/team1_nav_custom.css">
<c:set var="biddingcount" value="${bidlistSize}"></c:set>
<script type="text/javascript">
	function bc_clk(c_num) {
		//카테고리 첫번째소분류(num) 정보들을 인기순(state=1)으로 보여줌
		location.href = "categories.do?sort_state=1&bc_num="
				+ (c_num - (c_num % 100)) + "&c_num=" + c_num;
	}

	$(function() {
		for (var i = 1; i <= ${biddingcount}; i++) {
			var id = 'countdown' + i;
			var expid = 'exp' + i;
			var lastid = 'last' + i;

			$('#' + id)
					.each(
							function() {
								var pid = 'countdown' + i;
								var exp = document.getElementById(expid).value;
								var last = document.getElementById(lastid);
								var countDownDate = new Date(exp);
								var x = setInterval(
										function() {

											// 현재날짜 시간
											var now = new Date().getTime();

											// 만료시간과 현재시간 차이 구함
											var distance = countDownDate - now;

											// 일, 시, 분, 초 계산
											var days = Math.floor(distance
													/ (1000 * 60 * 60 * 24));
											var hours = Math
													.floor((distance % (1000 * 60 * 60 * 24))
															/ (1000 * 60 * 60));
											var minutes = Math
													.floor((distance % (1000 * 60 * 60))
															/ (1000 * 60));
											var seconds = Math
													.floor((distance % (1000 * 60)) / 1000);

											// id="countdown"인 곳에 output
											var result = days + "일 " + hours
													+ "시간 " + minutes + "분 "
													+ seconds + "초 ";

											document.getElementById(pid).innerHTML = result;
											if (distance < 0) {
												clearInterval(x);
												document.getElementById(pid).innerHTML = "입찰만료";
												$(last).attr('disabled', false);

											} else {

												$(last).attr('disabled', true);

											}
										}, 500);
							});
		}
	});
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
						name="search_word" placeholder="검색">
					<button class="btn btn-info" type="submit">검색</button>
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
	<br>
	<br>
	<br>
	<!-- Here is main container start 상품명 : ${bidlist.pd_name}-->
	<!-- 컨테이너 시작 -->
	<div class="container" style="text-align: center">
		<h1
			style="font-family: 'Nanum Gothic Coding', 'gulim'; text-align: left;">입찰
			참여 목록</h1>
		<br>
		<c:forEach var="bidlist" items="${bl }" varStatus="status">
			<table class="table-striped" id="biddingList">
				<tr>
					<td rowspan="5"><img class="img-thumbnail"
						src="image/ct_image/${bidlist.bc_num }/${bidlist.c_num}/${bidlist.pd_image }"
						style="width: 200px; height: 150px"
						onclick="location.href='viewCount.do?b_num=${bidlist.b_num}'">
					</td>
					<td style="width: 700px" bgcolor="0c0c0c" align="center"><a
						href="viewCount.do?b_num=${bidlist.b_num }"><font
							color="white">게시물등록번호: ${bidlist.b_num }</font></a></td>
					<td rowspan="2" align="center" bgcolor="white"
						style="padding-left: 10px;"><input class="btn btn-primary"
						type="button" id="end${status.count }" value="재입찰"
						style="width: 90px; height: 50px;"
						onclick="location.href='viewCount.do?b_num=${bidlist.b_num}'">
					</td>
				<tr>
					<td align="center">내 입찰 가격 : <fmt:formatNumber
							value="${bidlist.bid_price}" pattern="#,###" />
					</td>
				</tr>
				<tr>
					<td align="center">현재 최고 입찰가 : <fmt:formatNumber
							value="${bidlist.now_maxbid}" pattern="#,###" />
					</td>

					<td rowspan="3" align="center" style="padding-left: 10px;"
						bgcolor="white"><c:if
							test="${bidlist.bid_price==bidlist.now_maxbid }">
							<input type="button" id="last${status.count }" value="낙찰"
								class="btn btn-success" style="width: 90px; height: 50px;"
								onclick="location.href='consumerResultPage.do?b_num=${bidlist.b_num}'">
						</c:if></td>
				<tr>
					<td align="center"><input type="hidden" id="exp${status.count }"
						value="${bidlist.b_expiration}"></td>
				</tr>
				<tr>
					<td id="countdown${status.count }" align="center"></td>
				</tr>
			</table>
			<br>
			<hr>
			<br>
		</c:forEach>
		<c:if test="${bidlistSize == 0}">
			<br>
			<br>
			<br>
			<label
				style="font-family: 'Nanum Gothic Coding', 'gulim'; text-align: center; font-size: 20px;">입찰목록이
				비어있습니다.</label>
		</c:if>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div class="footer">
		베이는 마켓플레이스(오픈마켓) 상품, 거래정보 및 거래 등에 대하여 책임을 지지 않습니다. <br> 기타사항에 대한
		문의 : admin@admin.com / Copyright © MarCatBay Corp. 2010-2020 All
		Rights Reserved.
	</div>
</body>
</html>