<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>경매 결과</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/team1_nav_custom.css">

<style type="text/css">
.image {
	width: 300px;
	height: 300px;
	border: 1px solid #555;
}

th {
	padding-left: 10px;
	padding-right: 10px;
	padding-top: 5px;
	padding-bottom: 5px;
	text-align: center;
}

td {
	padding-left: 10px;
	padding-right: 10px;
	padding-top: 5px;
	padding-bottom: 5px;
	text-align: center;
}
</style>
<script type="text/javascript">
	function bc_clk(c_num) {
		//카테고리 첫번째소분류(num) 정보들을 인기순(state=1)으로 보여줌
		location.href = "categories.do?sort_state=1&bc_num="
				+ (c_num - (c_num % 100)) + "&c_num=" + c_num;
	}
	var countDownDate = new Date("${b_expirationAfterWeek}");
	var x = setInterval(function() {

		// 현재날짜 시간
		var now = new Date().getTime();

		// 만료시간과 현재시간 차이 구함
		var distance = countDownDate - now;

		// 일, 시, 분, 초 계산
		var days = Math.floor(distance / (1000 * 60 * 60 * 24));
		var hours = Math.floor((distance % (1000 * 60 * 60 * 24))
				/ (1000 * 60 * 60));
		var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
		var seconds = Math.floor((distance % (1000 * 60)) / 1000);

		// id="countdown"인 곳에 output
		document.getElementById("countdown").innerHTML = days + "일 " + hours
				+ "시간 " + minutes + "분 " + seconds + "초<br>후 자동으로 구매확정됩니다";

		if (distance < 0) {
			clearInterval(x);
			document.getElementById("countdown").innerHTML = "구매확정";
		}
	}, 500);
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
	<!-- Here is main container start -->
	<!-- 컨테이너 시작 -->
	<div class="container" align="center">
		<div class="sellerResultPage" align="center">
			<h3 class="sort_type">게시물 > ${b_title }</h3>
			<br> <br>
			<div class="sort_type" style="text-align: center;">
				등록번호 [ ${b_num} ]이(가)
				<c:if test="${max_bid_price == 0 }">유찰</c:if>
				<c:if test="${max_bid_price != 0 }">낙찰</c:if>
				되셨습니다.
			</div>
			<br> <br>
			<table class="table-responsive" style="margin-left: 200px;">
				<tr>
					<!-- 사진 -->
					<td><c:forEach var="bi" items="${images}" varStatus="status">
							<c:if test="${status.index==0 }">
								<img class="img-thumbnail"
									src="image/ct_image/${board.bc_num}/${board.c_num}/${bi}"
									alt="상품 이미지" style="width: 400px; height: 400px;">
							</c:if>
						</c:forEach></td>
					<!-- 입찰결과 -->
					<td style="vertical-align: top;">
						<table class="table-bordered table-striped">
							<tr>
								<th>입찰자</th>
								<th>일자</th>
								<th>입찰가격</th>
							</tr>
							<c:if test="${totCnt >0 }">
								<c:forEach var="bid" items="${list }">
									<tr>
										<td style="padding: 20px; margin: 20px">${bid.user_email }</td>
										<td>${bid.bid_timestamp }</td>
										<td><fmt:formatNumber value="${bid.bid_price }"
												pattern="#,###" /></td>
									</tr>
									<c:set var="startNum" value="${startNum - 1}" />
								</c:forEach>
							</c:if>
							<c:if test="${totCnt == 0 }">
								<tr>
									<td colspan=5>데이터가 없습니다</td>
								</tr>
							</c:if>
						</table>

						<div style="text-align: center;">
							<c:if test="${startPage > blockSize }">
								<a
									href='sellerResultPage.do?b_num=${b_num }&pageNum=${startPage-blockSize }'>[이전]</a>
							</c:if>
							<c:forEach var="i" begin="${startPage }" end="${endPage }">
								<a href='sellerResultPage.do?b_num=${b_num }&pageNum=${i}'>[${i}]</a>
							</c:forEach>
							<c:if test="${endPage < pageCnt }">
								<a
									href='sellerResultPage.do?b_num=${b_num }&pageNum=${startPage+blockSize }'>[다음]</a>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<!-- 구매자정보 -->
					<td>
						<table class="table-bordered" style="width:100;">
							<tr>
								<th colspan="2" align="center">구매자 정보 <%-- <div align="center">
											<c:if test="${max_bid_price == 0 }">
												<img src="image/profile_image/img01.png" width="200"
													height="200" alt="프로필 이미지">
											</c:if>
											<c:if test="${max_bid_price != 0 }">
												<img
													src="image/profile_image/${win_user_info.consumer_image }"
													width="200" height="200" alt="프로필 이미지">
											</c:if>
										</div> --%>
								</th>
							</tr>
							<tr>
								<td>구매자 주소</td>
								<td><c:if test="${address != '0' }">우편번호 : ${zip }<br> ${address } ${addressInDetail }
									${referenceItem }</c:if> <c:if test="${address == '0' }">조회할 권한이 없습니다.</c:if>
								</td>
							</tr>
							<tr>
								<td>구매자 email</td>
								<td>${win_user_info.consumer_email }</td>
							</tr>
							<tr>
								<td>구매자 전화번호</td>
								<td><c:if test="${win_user_info.consumer_phone != '0' }">${win_user_info.consumer_phone }</c:if>
									<c:if test="${win_user_info.consumer_phone == '0' }">조회할 권한이 없습니다.</c:if></td>
							</tr>
						</table>
					</td>
					<td style="vertical-align: middle;">
						<div>
							<c:if test="${confirm_status==1}">구매 확정 되었습니다.</c:if>
							<c:if test="${confirm_status==0&&max_bid_price == 0 }">유찰되었습니다.</c:if>
							<c:if test="${confirm_status==0&&max_bid_price != 0 }">
								<p id="countdown"></p>
							</c:if>
						</div>
						<br>
						<div align="center">
							<input type="button" class="btn btn-primary"
								style="background-color: #000000; border-color: #A2A2A2;"
								value="상품페이지로"
								onclick="location.href='viewCount.do?b_num=${b_num }'">
						</div>
				</tr>
			</table>
			<br> <br>

				<div class="sort_type" style="text-align: center;">상품사진</div>
				<br>
				<div class="row">
					<div class="thumbnails">
						<c:forEach var="bi" items="${images }" varStatus="status">
							<div class="col-md-4 ">
								<img class="img-thumbnail"
									src="image/ct_image/${board.bc_num}/${board.c_num}/${bi}"
									alt="이미지">
							</div>
						</c:forEach>
					</div>
				</div>
		</div>
	</div>
	<br>
	<br>
	<div class="footer">
		베이는 마켓플레이스(오픈마켓) 상품, 거래정보 및 거래 등에 대하여 책임을 지지 않습니다. <br> 기타사항에 대한
		문의 : admin@admin.com / Copyright © MarCatBay Corp. 2010-2020 All
		Rights Reserved.
	</div>
</body>
</html>