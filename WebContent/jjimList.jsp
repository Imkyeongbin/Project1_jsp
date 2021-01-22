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
<c:set var="jjimcount" value="${jjimcount}"></c:set>
<script type="text/javascript">
	function bc_clk(c_num) {
		//카테고리 첫번째소분류(num) 정보들을 인기순(state=1)으로 보여줌
		location.href = "categories.do?sort_state=1&bc_num="+(c_num-(c_num%100))+"&c_num=" + c_num;
	}
	
	$(function(){
		for(var i =1; i<=${jjimcount}; i++){
			var id = 'countdown' + i;
			var expid = 'exp'+ i;
			
		 $('#'+id).each(function() {
			 var pid = 'countdown' + i;
			 var exp = document.getElementById(expid).value;
			 var countDownDate = new Date(exp);
				var x = setInterval(
						function(){
				
				// 현재날짜 시간
				var now = new Date().getTime();
				
				// 만료시간과 현재시간 차이 구함
				var distance = countDownDate - now;
				
				// 일, 시, 분, 초 계산
				var days = Math.floor(distance/ (1000 * 60 * 60 * 24));
				var hours = Math.floor((distance % (1000 * 60 * 60 * 24))/ (1000 * 60 * 60));
				var minutes = Math.floor((distance % (1000 * 60 * 60))/ (1000 * 60));
				var seconds = Math.floor((distance % (1000 * 60)) / 1000);
				
				// id="countdown"인 곳에 output
				var result = days + "일 " + hours + "시간 " + minutes + "분 " + seconds + "초 ";
				
				document.getElementById(pid).innerHTML = result;
		         
		         if (distance < 300000 && distance>0) {
		            document.getElementById(pid).style.color='red';         
		         } else if (distance < 0) {
		               clearInterval(x);
		               document.getElementById(pid).innerHTML = "입찰만료";
		               document.getElementById(pid).style.color='black';         
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
				<li class="firstMenuLi"><a class="menuLink"	href="loginForm.jsp">로그인</a></li>
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
		<h1 class="sort_type">찜 목록</h1><br>
			<c:forEach var="jjim1" items="${jjim1}" varStatus="status">
			<table class="table-striped" id="jjimList">
				<tr>
					<td rowspan="5"><img class="img-thumbnail"
						src="image/ct_image/${jjim1.bc_num }/${jjim1.c_num }/${jjim1.pd_image }"
						style="width: 200px; height: 150px;"
						onclick="location.href='viewCount.do?b_num=${jjim1.b_num}'">
					</td>
					<td style="width: 700px" bgcolor="0c0c0c" align="center"><font color="white">상품명 : ${jjim1.b_title}</font></td>
					<td rowspan="5" align="center" style="padding-left: 10px;" bgcolor="white"><input type="button" class="btn btn-danger"
						value="찜 삭제" style="width: 90px; height: 60px;"
						onclick="location.href='jjimdelete.do?b_num=${jjim1.b_num}'">
					</td>
				</tr>
				<tr>
					<td align="center">
						게시글번호 : ${jjim1.b_num }
					<td>
				</tr>
				<tr>
					<td align="center">현재 최고가 : <fmt:formatNumber value="${jjim1.now_maxbid}"
							pattern="#,###" /> (${jjim1.now_win_user_email })
					</td>
				</tr>
				<tr>
					<td align="center"><input type="hidden" id="exp${status.count }"
						value="${jjim1.b_expiration}"></td>
				</tr>
				<tr align="center">
					<td id="countdown${status.count }"></td>
				</tr>
				</table>
				<br>
				<hr>
				<br>
			</c:forEach>
		<c:if test="${jjimcount == 0}">
			<br>
			<br>
			<br>
			<label
				style="font-family: 'Nanum Gothic', gulim; text-align: center; font-size: 20px;">찜목록이 비어있습니다.</label>
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