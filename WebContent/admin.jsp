<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/team1_nav_custom.css">

<script type="text/javascript">
	function bc_clk(c_num) {
		//카테고리 첫번째소분류(num) 정보들을 인기순(state=1)으로 보여줌
		location.href = "categories.do?sort_state=1&bc_num="
				+ (c_num - (c_num % 100)) + "&c_num=" + c_num;
	}
</script>

<style>
.img-rounded {
	width: 300px;
	height: 200px;
}

.footer {
	text-decoration: white;
}
</style>

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
					<button class="btn" type="submit">검색</button>
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
					href="sellerResultPage.do?b_num=b5">판매목록</a></li>
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
	<a href="admin.do?adminState=${adminState }">게시물관리</a>
	<a href="adminMember.do?adminState=${adminState }">회원관리</a>
	<script type="text/javascript"> var idx =0;</script>
	<table class="table table-striped">
		<tr>
			<th colspan="12" class="text-center">입찰진행현황</th>
		</tr>
		<tr>
			<th class="text-center">#</th>
			<th class="text-center">게시물번호</th>
			<th class="text-center">판매자</th>
			<th class="text-center">경매마감일</th>
			<th class="text-center">상품번호</th>
			<th class="text-center">현재최고입찰가격</th>
			<th class="text-center">현재최고입찰자</th>
			<th class="text-center">경매상태</th>
			<th class="text-center">최종낙찰가격</th>
			<th class="text-center">최종낙찰자</th>
			<th class="text-center">구매확정상태</th>
			<th class="text-center">관리</th>
		</tr>
		
		<c:forEach var="ab" items="${admin_board}" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${ab.b_num }</td>
				<td>${ab.seller_email }</td>
				<td>${ab.b_expiration }</td>
				<td>${ab.pd_num}</td>
				<td>${ab.now_maxbid }원</td>
				<td>${ab.now_win_user_email }</td>
				<td>${ab.b_state }</td>
				<td>${ab.max_bid_price }원</td>
				<td>${ab.win_user_email }</td>
				<td>${ab.confirm_status }</td>
				<td><input type="button" id="submit1${status.count}" onclick="location.href='adminStopAuction.do?b_num=${ab.b_num }'"  value="경매중단하기"> 
				<input type="button" id="submit2${status.count}" onclick="location.href='adminStopMoney.do?b_num=${ab.b_num }'" value="구매확정중단하기"></td>
			</tr>
			<script type="text/javascript">
				idx ++;
				var b_state = "${ab.b_state }";
				var confirm_status = "${ab.confirm_status }";
				if (b_state == "0" && confirm_status=="0") {
					$("#submit1"+idx).hide();
					$("#submit2"+idx).show();
				} else if (b_state == "0" && confirm_status=="1") {
					$("#submit1"+idx).hide();
					$("#submit2"+idx).hide();
				}else if (b_state == "0" && confirm_status=="-1") {
					$("#submit1"+idx).hide();
					$("#submit2"+idx).hide();
				}else if (b_state == "1") {
					$("#submit1"+idx).show();
					$("#submit2"+idx).hide();
				}else if (b_state == "-1") {
					$("#submit1"+idx).hide();
					$("#submit2"+idx).hide();
				} 
			</script>
		</c:forEach>
	</table>


</body>
</html>