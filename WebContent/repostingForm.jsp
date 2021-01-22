<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="dao.Product_Info"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매하기</title>
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
	function chk() {
		
		var date = new Date();
		var buydate = new Date(form.pd_buydate.value);
		var expiration = new Date(form.b_expiration.value);
		
		//날짜 체크 부분.

		if (expiration < date) {
			alert('마감날짜는 현재시각보다 뒤로 설정해야합니다.');
			form.b_expiration.focus();
			form.b_expiration.value = "";
			return false;
		}
		
		//숫자만 입력받게 체크하는 부분.
		
		if(isNaN(form.pd_price.value)){
			   alert('판매 가격은 숫자만 입력하여 주세요');
			   form.pd_price.focus();
			   form.pd_price.value=""; 
			   return false;
		     }
		
		if(isNaN(form.pd_unit.value)){
			   alert('입찰 단위는 숫자만 입력하여 주세요');
			   form.pd_unit.focus();
			   form.pd_unit.value=""; 
			   return false;
		     }

		return true;
	}

	function changes(fr) {
		if (fr == 100) {
			//뿌려줄값을 배열로정렬
			num = new Array("노트북", "핸드폰", "이어폰", "게임기");
			vnum = new Array("101", "102", "103", "104");
		} else if (fr == 200) {
			num = new Array("의자", "책상", "옷장", "침구류");
			vnum = new Array("201", "202", "203", "204");
		} else if (fr == 300) {
			num = new Array("아웃도어", "홈트레이닝", "캠핑", "기타스포츠용품");
			vnum = new Array("301", "302", "303", "304");
		} else if (fr == 400) {
			num = new Array("염색", "얼굴", "바디", "미용도구");
			vnum = new Array("401", "402", "403", "404");
		} else if (fr == 500) {
			num = new Array("지갑", "가방", "양말/스타킹", "쥬얼리/시계");
			vnum = new Array("501", "502", "503", "504");
		} else if (fr == 600) {
			num = new Array("아우터", "상의", "하의", "신발");
			vnum = new Array("601", "602", "603", "604");
		}
		// 셀렉트안의 리스트를 기본값으로 한다..

		for (i = 0; i < form.c_num.length; i++) {
			form.c_num.options[0] = null;
		}
		//포문을 이용하여 두번째(c_num)셀렉트 박스에 값을 뿌려줍니당)
		for (i = 0; i < num.length; i++) {
			17.
			document.form.c_num.options[i] = new Option(num[i], vnum[i]);
		}
	}
</script>
</head>
<style>
th{
	text-align: center;
}
</style>
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
		<div class="postingForm">

			<%
				String user_email = (String) session.getAttribute("user_email");
			if (user_email == null) {
				response.sendRedirect("./loginForm.jsp");
			}
			%>
			
			 <div class="postingForm">


				<h2 class="sort_type">상품 게시글 등록</h2><br><br>

				<form name="form" action="reposting2.do" method="post"
					onsubmit="return chk()">
					<table class="table table-striped"
						style="margin-left: auto; margin-right: auto;">

						<tr>
							<th>글제목</th>
							<th>상품명</th>
							<th>카테고리</th>
						</tr>

						<tr>
							<td><input type="text" name="b_title" required="required" /></td>
							<td>${pd_name}</td>
							<td>
							<c:choose>
							<c:when test="${c_num==101}">노트북</c:when>
							<c:when test="${c_num==102}">핸드폰</c:when>
							<c:when test="${c_num==103}">이어폰</c:when>
							<c:when test="${c_num==104}">게임기</c:when>
							<c:when test="${c_num==201}">의자</c:when>
							<c:when test="${c_num==202}">책상</c:when>
							<c:when test="${c_num==203}">옷장</c:when>
							<c:when test="${c_num==204}">침구류</c:when>
							<c:when test="${c_num==301}">아웃도어</c:when>
							<c:when test="${c_num==302}">홈트레이닝</c:when>
							<c:when test="${c_num==303}">캠핑</c:when>
							<c:when test="${c_num==304}">기타스포츠용품</c:when>
							<c:when test="${c_num==401}">염색</c:when>
							<c:when test="${c_num==402}">얼굴</c:when>
							<c:when test="${c_num==403}">바디</c:when>
							<c:when test="${c_num==404}">미용도구</c:when>
							<c:when test="${c_num==501}">지갑</c:when>
							<c:when test="${c_num==502}">가방</c:when>
							<c:when test="${c_num==503}">양말/스타킹</c:when>
							<c:when test="${c_num==504}">쥬얼리/시계</c:when>
							<c:when test="${c_num==601}">아우터</c:when>
							<c:when test="${c_num==602}">상의</c:when>
							<c:when test="${c_num==603}">하의</c:when>
							<c:when test="${c_num==604}">신발</c:when>
							</c:choose>

							
							</td>

						</tr>

						<tr>
							<th>입찰단위(₩)</th>
							<th>경매 시작 가격(₩)</th>
							<th>상품 상태</th>
						</tr>

						<tr>
							<td><input type="text" name="pd_unit" required="required" /></td>
							<td><input type="text" name="pd_price" required="required" /></td>
							<td>${pd_condition}</td>
						</tr>
						<tr>
							<th>판매 마감일</th>
							<th>구매 날짜</th>
							<th>내가 올린 이미지 파일 목록</th>
						</tr>

						<tr>
							<td><input type="datetime-local" name="b_expiration"required="required"/></td>
							<td>${pd_buydate}</td>
							<td>
							
							<c:forEach var="temp" items="${files}" varStatus="status">
								${status.count}. ${temp}
								<br/>
							</c:forEach>
						
							</td>
						</tr>
						<tr>
							<th colspan="3">내용</th>
						</tr>
						<tr>

							<td colspan="4"><textarea cols="80" rows="5"
									name="b_contents" required="required"></textarea></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" class="btn btn-success"
								style="font-family: 'Nanum Gothic Coding', 'gulim'; text-align: center; font-size: 20px;"
								value="재등록하기"></td>
							<td colspan="2"><input type="button" class="btn btn-default"
								value="뒤로가기"
								style="font-family: 'Nanum Gothic Coding', 'gulim'; text-align: center; font-size: 20px;"
								onclick="location.href='main.do'"></td>
						</tr>

					</table>
					<input type="hidden" name="pd_num" value="${pd_num}">
					<input type="hidden" name="b_num" value="${b_num}">
					<input type="hidden" name="bc_num" value="${bc_num}">
					<input type="hidden" name="c_num" value="${c_num}">
					<input type="hidden" name="seller_nickname" value="${seller_nickname}">
					<input type="hidden" name="seller_email" value="${seller_email}">
					<input type="hidden" name="pd_image" value="${pd_image}">
					<input type="hidden" name="pd_name" value="${pd_name}">
					<input type="hidden" name="pd_condition" value="${pd_condition}">
					<input type="hidden" name="pd_buydate" value="${pd_buydate}">
					
				</form>
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