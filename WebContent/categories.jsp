<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	//카테고리 첫번째소분류(num) 정보들을 인기순(state=1)으로 보여줌
	location.href = "categories.do?sort_state=1&bc_num="+(c_num-(c_num%100))+"&c_num=" + c_num;
}
function sort_select(sort_state,c_num){
	location.href = "categories.do?sort_state="+sort_state+"&bc_num="+(c_num-(c_num%100))+"&c_num=" + c_num;
}

function doLike(b_num,id_num1,id_num2){
	var loginState = "${loginState}";
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
};

//카운트다운
$(function(){
	for(var j=1;j<=18; j++){
	for(var i =1; i<=1000; i++){
		var id = 'countdown' + j + i;
		var expid = 'exp' + j + i;
		
	 $('#'+id).each(function() {
		 var pid = 'countdown' + j + i;
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
	<br>
	<br>
	<br>
	<!-- Here is main container start -->
	<!-- 컨테이너 시작 -->
	<div class="container" style="text-align: center">
		<!-- 카테고리 선택 출력 시작-->
		<div class="categories" style="text-align: left">
			<!-- 대분류 출력 -->
			<c:forEach var="ct" items="${categories_list}">
				<c:if test="${ct.bc_num == bc_num && ct.c_num==0}">
					<label id="bct">${ct.subject}</label>
				</c:if>
			</c:forEach>
			<br>
			<!-- 소분류 출력 : clickable -->
			<c:forEach var="ct" items="${categories_list}">
				<c:if test="${ct.bc_num == bc_num && ct.c_num!=0 }">
					<a class="ct"
						href="categories.do?sort_state=1&bc_num=${ct.bc_num}&c_num=${ct.c_num}">${ct.subject }</a>&nbsp;&nbsp;&nbsp;
				</c:if>
			</c:forEach>
			<select id="selectBox_sort"
				onchange="sort_select(this.value,${c_num})" style="float: right">
				<option value="1" <c:if test="${sort_state==1}">selected</c:if>>인기순</option>
				<option value="2" <c:if test="${sort_state==2}">selected</c:if>>경매마감순</option>
				<option value="3" <c:if test="${sort_state==3}">selected</c:if>>조회순</option>
			</select> <br>
			<hr>
			<!-- 정렬방식 -->
			<!-- categories.do?bc_num=${bc_num}&c_num${c_num}$sort_state=${sort.sort_code} -->
		</div>
		<br>

		<!-- 인기 순으로 출력 -->
		<c:if test="${sort_state==1}">
			<h4 class="sort_type">인기 순</h4>
			<c:choose>
				<c:when test="${bc_num == 100}">
					<div class="row">
						<div class="thumbnails">
							<script type="text/javascript"> var jjim_list = new Array(); </script>
							<c:set var="count" value="0"></c:set>
							<c:forEach var="lkc_bd" items="${lkc_bd_list100}"
								varStatus="status">
								<c:if test="${lkc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${lkc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${lkc_bd.bc_num}/${lkc_bd.c_num}/${lkc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${lkc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${lkc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp1${cnt }"
											value="${lkc_bd.b_expiration}">
										<p id="countdown1${cnt }"></p>

										입찰 최고 금액 : ${lkc_bd.now_maxbid}원<br> 입찰자 수 :
										${lkc_bd.b_bidders_num}
										<button type="button" class="btn btn-default btn-xs"
											id="like1${status.count}"
											onclick="doLike('${lkc_bd.b_num}','1','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt1${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike1${status.count}"
											onclick="doLike('${lkc_bd.b_num}','1','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt1${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${lkc_bd.b_num}", jjim_list) > -1){
										$("#like1"+"${status.count}").show();
										$("#dislike1"+"${status.count}").hide();
									}else{
									 	$("#like1"+"${status.count}").hide();
										$("#dislike1"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 200}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="lkc_bd" items="${lkc_bd_list200}"
								varStatus="status">
								<c:if test="${lkc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${lkc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${lkc_bd.bc_num}/${lkc_bd.c_num}/${lkc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${lkc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${lkc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp2${cnt }"
											value="${lkc_bd.b_expiration}">
										<p id="countdown2${cnt }"></p>

										입찰 최고 금액 : ${lkc_bd.now_maxbid}원<br> 입찰자 수 :
										${lkc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like2${status.count}"
											onclick="doLike('${lkc_bd.b_num}','2','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt2${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike2${status.count}"
											onclick="doLike('${lkc_bd.b_num}','2','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt2${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${lkc_bd.b_num}", jjim_list) > -1){
										$("#like2"+"${status.count}").show();
										$("#dislike2"+"${status.count}").hide();
									}else{
									 	$("#like2"+"${status.count}").hide();
										$("#dislike2"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 300}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="lkc_bd" items="${lkc_bd_list300}"
								varStatus="status">
								<c:if test="${lkc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${lkc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${lkc_bd.bc_num}/${lkc_bd.c_num}/${lkc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${lkc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${lkc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp3${cnt }"
											value="${lkc_bd.b_expiration}">
										<p id="countdown3${cnt }"></p>

										입찰 최고 금액 : ${lkc_bd.now_maxbid}원<br> 입찰자 수 :
										${lkc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like3${status.count}"
											onclick="doLike('${lkc_bd.b_num}','3','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt3${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike3${status.count}"
											onclick="doLike('${lkc_bd.b_num}','3','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt3${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${lkc_bd.b_num}", jjim_list) > -1){
										$("#like3"+"${status.count}").show();
										$("#dislike3"+"${status.count}").hide();
									}else{
									 	$("#like3"+"${status.count}").hide();
										$("#dislike3"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 400}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="lkc_bd" items="${lkc_bd_list400}"
								varStatus="status">
								<c:if test="${lkc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${lkc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${lkc_bd.bc_num}/${lkc_bd.c_num}/${lkc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${lkc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${lkc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp4${cnt }"
											value="${lkc_bd.b_expiration}">
										<p id="countdown4${cnt }"></p>

										입찰 최고 금액 : ${lkc_bd.now_maxbid}원<br> 입찰자 수 :
										${lkc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like4${status.count}"
											onclick="doLike('${lkc_bd.b_num}','4','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt4${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike4${status.count}"
											onclick="doLike('${lkc_bd.b_num}','4','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt4${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${lkc_bd.b_num}", jjim_list) > -1){
										$("#like4"+"${status.count}").show();
										$("#dislike4"+"${status.count}").hide();
									}else{
									 	$("#like4"+"${status.count}").hide();
										$("#dislike4"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 500}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="lkc_bd" items="${lkc_bd_list500}"
								varStatus="status">
								<c:if test="${lkc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${lkc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${lkc_bd.bc_num}/${lkc_bd.c_num}/${lkc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${lkc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${lkc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp5${cnt }"
											value="${lkc_bd.b_expiration}">
										<p id="countdown5${cnt }"></p>

										입찰 최고 금액 : ${lkc_bd.now_maxbid}원<br> 입찰자 수 :
										${lkc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like5${status.count}"
											onclick="doLike('${lkc_bd.b_num}','5','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt5${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike5${status.count}"
											onclick="doLike('${lkc_bd.b_num}','5','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt5${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${lkc_bd.b_num}", jjim_list) > -1){
										$("#like5"+"${status.count}").show();
										$("#dislike5"+"${status.count}").hide();
									}else{
									 	$("#like5"+"${status.count}").hide();
										$("#dislike5"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 600}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="lkc_bd" items="${lkc_bd_list600}"
								varStatus="status">
								<c:if test="${lkc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${lkc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${lkc_bd.bc_num}/${lkc_bd.c_num}/${lkc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${lkc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${lkc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp6${cnt }"
											value="${lkc_bd.b_expiration}">
										<p id="countdown6${cnt }"></p>

										입찰 최고 금액 : ${lkc_bd.now_maxbid}원<br> 입찰자 수 :
										${lkc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like6${status.count}"
											onclick="doLike('${lkc_bd.b_num}','6','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt6${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike6${status.count}"
											onclick="doLike('${lkc_bd.b_num}','6','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt6${status.count }">${lkc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${lkc_bd.b_num}", jjim_list) > -1){
										$("#like6"+"${status.count}").show();
										$("#dislike6"+"${status.count}").hide();
									}else{
									 	$("#like6"+"${status.count}").hide();
										$("#dislike6"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
			</c:choose>
			<br>
			<br>
			<br>
			<hr>
		</c:if>

		<!-- 경매 마감순 -->
		<c:if test="${sort_state==2}">

			<h4 class="sort_type">경매마감 순</h4>

			<c:choose>

				<c:when test="${bc_num == 100}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="exp_bd" items="${exp_bd_list100}"
								varStatus="status">
								<c:if test="${exp_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${exp_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${exp_bd.bc_num}/${exp_bd.c_num}/${exp_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${exp_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${exp_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp7${cnt }"
											value="${exp_bd.b_expiration}">
										<p id="countdown7${cnt }"></p>

										입찰 최고 금액 : ${exp_bd.now_maxbid}원<br> 입찰자 수 :
										${exp_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like7${status.count}"
											onclick="doLike('${exp_bd.b_num}','7','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt7${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike7${status.count}"
											onclick="doLike('${exp_bd.b_num}','7','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt7${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${exp_bd.b_num}", jjim_list) > -1){
										$("#like7"+"${status.count}").show();
										$("#dislike7"+"${status.count}").hide();
									}else{
									 	$("#like7"+"${status.count}").hide();
										$("#dislike7"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 200}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="exp_bd" items="${exp_bd_list200}"
								varStatus="status">
								<c:if test="${exp_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${exp_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${exp_bd.bc_num}/${exp_bd.c_num}/${exp_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${exp_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${exp_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp8${cnt }"
											value="${exp_bd.b_expiration}">
										<p id="countdown8${cnt }"></p>

										입찰 최고 금액 : ${exp_bd.now_maxbid}원<br> 입찰자 수 :
										${exp_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like8${status.count}"
											onclick="doLike('${exp_bd.b_num}','8','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt8${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike8${status.count}"
											onclick="doLike('${exp_bd.b_num}','8','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt8${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${exp_bd.b_num}", jjim_list) > -1){
										$("#like8"+"${status.count}").show();
										$("#dislike8"+"${status.count}").hide();
									}else{
									 	$("#like8"+"${status.count}").hide();
										$("#dislike8"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 300}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="exp_bd" items="${exp_bd_list300}"
								varStatus="status">
								<c:if test="${exp_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${exp_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${exp_bd.bc_num}/${exp_bd.c_num}/${exp_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${exp_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${exp_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp9${cnt }"
											value="${exp_bd.b_expiration}">
										<p id="countdown9${cnt }"></p>

										입찰 최고 금액 : ${exp_bd.now_maxbid}원<br> 입찰자 수 :
										${exp_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like9${status.count}"
											onclick="doLike('${exp_bd.b_num}','9','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt9${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike9${status.count}"
											onclick="doLike('${exp_bd.b_num}','9','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span id="djcnt9${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${exp_bd.b_num}", jjim_list) > -1){
										$("#like9"+"${status.count}").show();
										$("#dislike9"+"${status.count}").hide();
									}else{
									 	$("#like9"+"${status.count}").hide();
										$("#dislike9"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 400}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="exp_bd" items="${exp_bd_list400}"
								varStatus="status">
								<c:if test="${exp_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${exp_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${exp_bd.bc_num}/${exp_bd.c_num}/${exp_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${exp_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${exp_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp10${cnt }"
											value="${exp_bd.b_expiration}">
										<p id="countdown10${cnt }"></p>

										입찰 최고 금액 : ${exp_bd.now_maxbid}원<br> 입찰자 수 :
										${exp_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like10${status.count}"
											onclick="doLike('${exp_bd.b_num}','10','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt10${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike10${status.count}"
											onclick="doLike('${exp_bd.b_num}','10','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt10${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${exp_bd.b_num}", jjim_list) > -1){
										$("#like10"+"${status.count}").show();
										$("#dislike10"+"${status.count}").hide();
									}else{
									 	$("#like10"+"${status.count}").hide();
										$("#dislike10"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 500}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="exp_bd" items="${exp_bd_list500}"
								varStatus="status">
								<c:if test="${exp_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${exp_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${exp_bd.bc_num}/${exp_bd.c_num}/${exp_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${exp_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${exp_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp11${cnt }"
											value="${exp_bd.b_expiration}">
										<p id="countdown11${cnt }"></p>

										입찰 최고 금액 : ${exp_bd.now_maxbid}원<br> 입찰자 수 :
										${exp_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like11${status.count}"
											onclick="doLike('${exp_bd.b_num}','11','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt11${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike11${status.count}"
											onclick="doLike('${exp_bd.b_num}','11','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt11${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${exp_bd.b_num}", jjim_list) > -1){
										$("#like11"+"${status.count}").show();
										$("#dislike11"+"${status.count}").hide();
									}else{
									 	$("#like11"+"${status.count}").hide();
										$("#dislike11"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 600}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="exp_bd" items="${exp_bd_list600}"
								varStatus="status">
								<c:if test="${exp_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${exp_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${exp_bd.bc_num}/${exp_bd.c_num}/${exp_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${exp_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${exp_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp12${cnt }"
											value="${exp_bd.b_expiration}">
										<p id="countdown12${cnt }"></p>

										입찰 최고 금액 : ${exp_bd.now_maxbid}원<br> 입찰자 수 :
										${exp_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like12${status.count}"
											onclick="doLike('${exp_bd.b_num}','12','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt12${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike12${status.count}"
											onclick="doLike('${exp_bd.b_num}','12','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt12${status.count }">${exp_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${exp_bd.b_num}", jjim_list) > -1){
										$("#like12"+"${status.count}").show();
										$("#dislike12"+"${status.count}").hide();
									}else{
									 	$("#like12"+"${status.count}").hide();
										$("#dislike12"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
			</c:choose>
			<br>
			<br>
			<br>
			<hr>
		</c:if>
		<!-- 조회 순 -->
		<c:if test="${sort_state==3}">

			<h4 class="sort_type">조회 순</h4>

			<c:choose>
				<c:when test="${bc_num == 100}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="ovc_bd" items="${ovc_bd_list100}"
								varStatus="status">
								<c:if test="${ovc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${ovc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${ovc_bd.bc_num}/${ovc_bd.c_num}/${ovc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${ovc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${ovc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp13${cnt }"
											value="${ovc_bd.b_expiration}">
										<p id="countdown13${cnt }"></p>

										입찰 최고 금액 : ${ovc_bd.now_maxbid}원<br> 입찰자 수 :
										${ovc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like13${status.count}"
											onclick="doLike('${ovc_bd.b_num}','13','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt13${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike13${status.count}"
											onclick="doLike('${ovc_bd.b_num}','13','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt13${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${ovc_bd.b_num}", jjim_list) > -1){
										$("#like13"+"${status.count}").show();
										$("#dislike13"+"${status.count}").hide();
									}else{
									 	$("#like13"+"${status.count}").hide();
										$("#dislike13"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 200}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="ovc_bd" items="${ovc_bd_list200}"
								varStatus="status">
								<c:if test="${ovc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${ovc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${ovc_bd.bc_num}/${ovc_bd.c_num}/${ovc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${ovc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${ovc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp14${cnt }"
											value="${ovc_bd.b_expiration}">
										<p id="countdown14${cnt }"></p>

										입찰 최고 금액 : ${ovc_bd.now_maxbid}원<br> 입찰자 수 :
										${ovc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like14${status.count}"
											onclick="doLike('${ovc_bd.b_num}','14','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt14${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike14${status.count}"
											onclick="doLike('${ovc_bd.b_num}','14','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt14${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${ovc_bd.b_num}", jjim_list) > -1){
										$("#like14"+"${status.count}").show();
										$("#dislike14"+"${status.count}").hide();
									}else{
									 	$("#like14"+"${status.count}").hide();
										$("#dislike14"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 300}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="ovc_bd" items="${ovc_bd_list300}"
								varStatus="status">
								<c:if test="${ovc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${ovc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${ovc_bd.bc_num}/${ovc_bd.c_num}/${ovc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${ovc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${ovc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp15${cnt }"
											value="${ovc_bd.b_expiration}">
										<p id="countdown15${cnt }"></p>

										입찰 최고 금액 : ${ovc_bd.now_maxbid}원<br> 입찰자 수 :
										${ovc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like15${status.count}"
											onclick="doLike('${ovc_bd.b_num}','15','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt15${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike15${status.count}"
											onclick="doLike('${ovc_bd.b_num}','15','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt15${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${ovc_bd.b_num}", jjim_list) > -1){
										$("#like15"+"${status.count}").show();
										$("#dislike15"+"${status.count}").hide();
									}else{
									 	$("#like15"+"${status.count}").hide();
										$("#dislike15"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 400}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="ovc_bd" items="${ovc_bd_list400}"
								varStatus="status">
								<c:if test="${ovc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${ovc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${ovc_bd.bc_num}/${ovc_bd.c_num}/${ovc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${ovc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${ovc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp16${cnt }"
											value="${ovc_bd.b_expiration}">
										<p id="countdown16${cnt }"></p>

										입찰 최고 금액 : ${ovc_bd.now_maxbid}원<br> 입찰자 수 :
										${ovc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like16${status.count}"
											onclick="doLike('${ovc_bd.b_num}','16','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt16${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike16${status.count}"
											onclick="doLike('${ovc_bd.b_num}','16','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt16${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${ovc_bd.b_num}", jjim_list) > -1){
										$("#like16"+"${status.count}").show();
										$("#dislike16"+"${status.count}").hide();
									}else{
									 	$("#like16"+"${status.count}").hide();
										$("#dislike16"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 500}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="ovc_bd" items="${ovc_bd_list500}"
								varStatus="status">
								<c:if test="${ovc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${ovc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${ovc_bd.bc_num}/${ovc_bd.c_num}/${ovc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${ovc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${ovc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp17${cnt }"
											value="${ovc_bd.b_expiration}">
										<p id="countdown17${cnt }"></p>

										입찰 최고 금액 : ${ovc_bd.now_maxbid}원<br> 입찰자 수 :
										${ovc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like17${status.count}"
											onclick="doLike('${ovc_bd.b_num}','17','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt17${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike17${status.count}"
											onclick="doLike('${ovc_bd.b_num}','17','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt17${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${ovc_bd.b_num}", jjim_list) > -1){
										$("#like17"+"${status.count}").show();
										$("#dislike17"+"${status.count}").hide();
									}else{
									 	$("#like17"+"${status.count}").hide();
										$("#dislike17"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:when test="${bc_num == 600}">
					<div class="row">
						<div class="thumbnails">
							<c:set var="count" value="0"></c:set>
							<c:forEach var="ovc_bd" items="${ovc_bd_list600}"
								varStatus="status">
								<c:if test="${ovc_bd.c_num == c_num }">
									<c:set var="cnt" value="${count+1 }"></c:set>
									<div class="col-md-4">
										<a href="viewCount.do?b_num=${ovc_bd.b_num}"> <img
											class="img-rounded"
											src="image/ct_image/${ovc_bd.bc_num}/${ovc_bd.c_num}/${ovc_bd.pd_image}"
											alt="이미지">
										</a> <br>
										<Marquee behavior=left scrollamount=left bgcolor="0c0c0c"
											width="300px">
											<font color="white">${ovc_bd.b_title}</font>
										</Marquee>
										<br>경매마감일 : ${ovc_bd.b_expiration} <br>

										<!-- 카운트다운 -->
										<input type="hidden" id="exp18${cnt }"
											value="${ovc_bd.b_expiration}">
										<p id="countdown18${cnt }"></p>

										입찰 최고 금액 : ${ovc_bd.now_maxbid}원<br> 입찰자 수 :
										${ovc_bd.b_bidders_num}

										<button type="button" class="btn btn-default btn-xs"
											id="like18${status.count}"
											onclick="doLike('${ovc_bd.b_num}','18','${status.count}')">
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
											<span id="ljcnt18${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<button type="button" class="btn btn-default btn-xs"
											id="dislike18${status.count}"
											onclick="doLike('${ovc_bd.b_num}','18','${status.count}')">
											<span class="glyphicon glyphicon-heart-empty"
												aria-hidden="true"></span> <span
												id="djcnt18${status.count }">${ovc_bd.b_like_count}</span>
										</button>

										<c:forEach var="jjim_list2" items="${jjim_list2 }">
											<script type="text/javascript">
									jjim_list.push("${jjim_list2.b_num}");
									</script>
										</c:forEach>

										<script type="text/javascript">
									
									if($.inArray("${ovc_bd.b_num}", jjim_list) > -1){
										$("#like18"+"${status.count}").show();
										$("#dislike18"+"${status.count}").hide();
									}else{
									 	$("#like18"+"${status.count}").hide();
										$("#dislike18"+"${status.count}").show(); 
									};
									var jjim_list = new Array();
									</script>
									</div>
									<c:set var="count" value="${cnt }"></c:set>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:when>
			</c:choose>
			<br>
			<br>
			<br>
			<hr>
		</c:if>
	</div>
	<!-- 해당항목 전체 출력 출력 끝-->

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