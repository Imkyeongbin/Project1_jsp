<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript">
	function bc_clk(c_num) {
		location.href = "categories.do?sort_state=1&bc_num=" + (c_num-(c_num%100)) + "&c_num=" + c_num;
	}
</script>

<script type="text/javascript">
	function select_Jjim(vpd_num){
		var pd_num = vpd_num;
		location.href="jjimButton.do?pd_num="+pd_num;
	}
	
	function doLike(b_num,id_num1,id_num2){
		var loginState = "${loginState }";
		var b_num = b_num;
		var id_num1 = id_num1;
		var id_num2 = id_num2;
		var ljcntid = 'ljcnt'+id_num1+id_num2;
		var djcntid = 'djcnt'+id_num1+id_num2;
		var ljcnt = Number(document.getElementById(ljcntid).innerHTML);
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
			},
			dataType : 'text', /* json text xml */
			success : function(data) {
				if(data==ljcntm){	
					document.getElementById(djcntid).innerHTML=data;
					document.getElementById(ljcntid).innerHTML=data;
					$("#like"+id_num1+id_num2).hide();
					$("#dislike"+id_num1+id_num2).show();
					
				} else if(data==ljcntp){
					document.getElementById(ljcntid).innerHTML=data;
					document.getElementById(djcntid).innerHTML=data;
					$("#like"+id_num1+id_num2).show();
					$("#dislike"+id_num1+id_num2).hide();
				}
			}

		});
	}
	;
};

//카운트다운
$(function(){
	
	for(var i =1; i<=${list_size}; i++){
		var id = 'countdown'+ i;
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
		<h2 class="sort_type">${search_word} - "${list_size} 건"을 찾았습니다.</h2>
		<br> <br>
		<c:if test="${result==0 }">
			<h2>검색 결과를 찾을 수 없습니다.</h2>
		</c:if>

		<c:if test="${result==1}">
			<div class="row">
				<script type="text/javascript">var idx =0; var jjim_list = new Array(); </script>
				<c:forEach var="bd" items="${board_list}" varStatus="status">
					<div class="thumbnails">
						<div class="col-md-4">
							<a href="viewCount.do?b_num=${bd.b_num}"> <img
								class="img-rounded"
								src="image/ct_image/${bd.bc_num}/${bd.c_num}/${bd.pd_image}"
								alt="이미지">
							</a> <br>
							<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
								width="300px">
								<font color="white">${bd.b_title}</font>
							</Marquee>
							<br>경매마감일 : ${bd.b_expiration} <br>

							<!-- 카운트다운 -->
							<input type="hidden" id="exp${status.count }"
								value="${bd.b_expiration}">
							<p id="countdown${status.count }"></p>

							입찰 최고 금액 : ${bd.now_maxbid}원<br> 입찰자 수 : ${bd.b_bidders_num}
							<button type="button" class="btn btn-default btn-xs"
								id="like${status.count}"
								onclick="doLike('${bd.b_num}','','${status.count}')">
								<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
								<span id="ljcnt${status.count }">${bd.b_like_count}</span>
							</button>

							<button type="button" class="btn btn-default btn-xs"
								id="dislike${status.count}"
								onclick="doLike('${bd.b_num}','','${status.count}')">
								<span class="glyphicon glyphicon-heart-empty" aria-hidden="true"></span>
								<span id="djcnt${status.count }">${bd.b_like_count}</span>
							</button>

							<c:forEach var="jjim_list2" items="${jjim_list2 }">
								<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
							</c:forEach>

							<script type="text/javascript">
									idx ++;
									if($.inArray("${bd.b_num}", jjim_list) > -1){
										$("#like"+idx).show();
										$("#dislike"+idx).hide();
									}else{
									 	$("#like"+idx).hide();
										$("#dislike"+idx).show(); 
									};
									var jjim_list = new Array();
									</script>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
	<!-- 컨테이너 종료 -->
	<!-- footer -->
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