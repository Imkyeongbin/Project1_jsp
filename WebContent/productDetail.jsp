<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String context = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/team1_nav_custom.css">
<title>상품상세정보창</title>

<style type="text/css">
.image {
	width: 300px;
	height: 300px;
	border: 1px solid #555;
}

table {
	margin: 50px auto;
	text-align: center;
}

td {
	text-align: center;
}
</style>
<!-- 부트스트랩 -->
<script src="js/jquery.js"></script>
<c:set var="b_expiration" value='${b_expiration }'></c:set>
<c:set var="b_num" value='${b_num }'></c:set>
<script type="text/javascript">
		function bc_clk(c_num) {
			//카테고리 첫번째소분류(num) 정보들을 인기순(state=1)으로 보여줌
			location.href = "categories.do?sort_state=1&bc_num="+(c_num-(c_num%100))+"&c_num=" + c_num;
		}
			
		var countDownDate = new Date("${b_expiration}");
		var x = setInterval(function() {

			// 현재날짜 시간
			var now = new Date().getTime();

			// 만료시간과 현재시간 차이 구함
			var distance = countDownDate - now;

			// 일, 시, 분, 초 계산
			var days = Math.floor(distance / (1000 * 60 * 60 * 24));
			var hours = Math.floor((distance % (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60));
			var minutes = Math.floor((distance % (1000 * 60 * 60))
					/ (1000 * 60));
			var seconds = Math.floor((distance % (1000 * 60)) / 1000);

			// id="countdown"인 곳에 output
			document.getElementById("countdown").innerHTML = days + "일 "
					+ hours + "시간 " + minutes + "분 " + seconds + "초 ";
			
			
			if (distance < 300000 && distance>0) {
	            
	            document.getElementById("countdown").style.color='red';      
	               
	         } else if (distance < 0) {
	               clearInterval(x);
	               document.getElementById("countdown").innerHTML = "입찰만료";
	               document.getElementById("countdown").style.color='black';
	               document.getElementById("submit").disabled = true;   
	         }
	   }, 500);

		
		function doLike(b_num){
			var loginState = "${loginState }";
			var jjim_state = "${jjim_state }";
			var b_num = b_num;
			var ljcnt = Number(document.getElementById("ljcnt").innerHTML);
			var ljcntm = ljcnt - 1;
			var ljcntp = ljcnt + 1;
			
			if (loginState == "0") {
				
				location.href="loginForm.jsp";
			}else{
				
			$.ajax({
				url:"<%=context%>/jjimButton.do",
				data : {
					b_num : b_num,
					loginState : loginState,
					jjim_state : jjim_state
				},
				dataType : 'text', /* json text xml */
				success : function(data) {
					if(data==ljcntm){	
						document.getElementById("djcnt").innerHTML=data;
						document.getElementById("ljcnt").innerHTML=data;
						$("#like").hide();
						$("#dislike").show();
						
					} else if(data==ljcntp){
						document.getElementById("ljcnt").innerHTML=data;
						document.getElementById("djcnt").innerHTML=data;
						$("#like").show();
						$("#dislike").hide();
					}
				}

			});
		}
		;
	};
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
	<div class="container" style="text-align: center">
		<div class="row">
			<!-- 입찰참여하기 버튼 누르면 "loginCheck.do"로 이동해서 확인 -->
			<form action="loginCheck.do">
				<c:set var="images" value="${images }"></c:set>
				<c:forEach var="b" items="${board_info}">
					<c:forEach var="pi" items="${product_info}">
						<div class="table-responsive">
							<input type="hidden" name="b_num" value="${b_num }">


							<%-- <table>
								<tr>
									<c:forEach var="images" items="${images }" varStatus="status">
										<td><img class="img-thumbnail"
											src="image/ct_image/${b.bc_num}/${b.c_num}/${images}"
											alt="이미지"></td>
									</c:forEach>

								</tr>
							</table> --%>
							<span
								style="margin-left: 5%; float: left; font-weight: bold; font-size: 20px">${b.b_title }</span>
							<span style="float: right">조회수: ${b_view_count }</span>
							<p></p>
							<table class="table table-striped">
								<tr>
									<td style="width: 50%;" rowspan="9"><img
										class="img-thumbnail"
										src="image/ct_image/${b.bc_num}/${b.c_num}/${images[0]}"
										style="width: 400px; height: 400px;" alt="이미지"> <br>
										<button type="button" class="btn btn-default btn" id="like"
											onclick="doLike('${b.b_num}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt">${b.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn" id="dislike"
											onclick="doLike('${b.b_num}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt">${b.b_like_count}</span>
										</button></td>
									<th>게시물번호</th>
									<td>${b_num}</td>
								</tr>
								<tr>
									<th>상품번호</th>
									<td>${pi.pd_num }</td>
								</tr>
								<tr>
									<th>상품분류</th>
									<td>${subject }</td>
								</tr>
								<tr>
									<th>시작가</th>
									<td>${pi.pd_price }원</td>
								</tr>
								<tr>
									<th>입찰단위</th>
									<td>${pi.pd_unit }원</td>
								</tr>
								<tr>
									<th>입찰 수</th>
									<td>${b.b_bids }</td>
								</tr>
								<tr>
									<th>입찰자 수</th>
									<td>${b.b_bidders_num }</td>
								</tr>
								<tr>
									<th>현재가</th>
									<td>${max_bid_price }원</td>
								</tr>
								<tr>
									<th>남은시간</th>
									<td><span id="countdown"></span></td>
								</tr>
							</table>


							<script type="text/javascript">
							var jjim_state = "${jjim_state }";
							if (jjim_state == "0") {

								$("#like").hide();
								$("#dislike").show();

							} else if (jjim_state = "1") {

								$("#like").show();
								$("#dislike").hide();
							}
						</script>

							<table class="table table-bordered">
								<tr>
									<th colspan="4" class="active" style="text-align: center">상품정보</th>
								</tr>
								<tr>
									<th class="active" style="text-align: center">상품상태</th>
									<td>${pi.pd_condition }</td>
									<th class="active" style="text-align: center">상품구입일자</th>
									<td>${pi.pd_buydate }</td>
								</tr>
								<tr>
									<th colspan="4" class="active" style="text-align: center">상품설명</th>
								</tr>
								<tr>
									<td colspan="4">${b.b_contents }</td>
								</tr>

							</table>

							<table class="table table-striped">
								<tr>
									<th colspan="4" class="text-center">입찰진행현황</th>
								</tr>
								<tr>
									<th class="text-center">#</th>
									<th class="text-center">입찰가격</th>
									<th class="text-center">입찰자</th>
									<th class="text-center">입찰시간</th>
								</tr>
								<c:forEach var="bl" items="${bidList}" varStatus="status">
									<tr>
										<td>${fn:length(bidList)- status.index}</td>
										<td>${bl.bid_price }원</td>
										<td>${bl.user_email }</td>
										<td>${bl.bid_timestamp }</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<div>
							<input type="submit" id="submit" value="입찰참여하기">
						</div>
						<table>
							<tr>
								<th style="text-align: center">상품사진</th>
							</tr>
							<c:forEach var="images" items="${images }" varStatus="status">
								<tr>
									<td><img class="img-thumbnail"
										src="image/ct_image/${b.bc_num}/${b.c_num}/${images}"
										alt="이미지"></td>
								</tr>
							</c:forEach>
						</table>
					</c:forEach>
				</c:forEach>
			</form>
		</div>
	</div>
	<!-- 컨테이너 종료 -->
	<!-- footer -->
	<br>
	<br>
	<div class="footer">
		베이는 마켓플레이스(오픈마켓) 상품, 거래정보 및 거래 등에 대하여 책임을 지지 않습니다. <br> 기타사항에 대한
		문의 : admin@admin.com / Copyright © MarCatBay Corp. 2010-2020 All
		Rights Reserved.
	</div>
</body>
</html>