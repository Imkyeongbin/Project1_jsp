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

		if (buydate > date) {
			alert('구매날짜는 오늘 날짜를 넘어갈 수 없습니다.');
			form.pd_buydate.focus();
			form.pd_buydate.value = "";
			return false;
		}

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
		
		//파일 갯수 제한 부분.
		var fileInput = document.getElementById("pd_image");
        var files = fileInput.files;            
    
        if (files.length > 6) {
        	alert('파일은 7개 이상을 첨부할 수 없습니다.');
        	alert('내가 올린 파일갯수:'+files.length);
        	form.pd_image.focus();
			form.pd_image.value = "";
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
<style>
th{
	text-align: center;
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
		<div class="postingForm">

			<%
				String user_email = (String) session.getAttribute("user_email");
			if (user_email == null) {
				response.sendRedirect("./loginForm.jsp");
			}
			%>
			<div class="postingForm">
				<h2 class="sort_type">상품 게시글 등록</h2><br><br>
				<form name="form" action="postingForm.do" method="post"
					onsubmit="return chk()" accept="image/png, image/jpeg, image/jpg"
					enctype="multipart/form-data">
					<table class="table table-striped"
						style="margin-left: auto; margin-right: auto;">

						<tr>
							<th>글제목</th>
							<th>상품명</th>
							<th>카테고리</th>
						</tr>

						<tr>
							<td><input type="text" name="b_title" required="required" /></td>
							<td><input type="text" name="pd_name" required="required" /></td>
							<td><select name="bc_num" required="required"
								onchange="changes(document.form.bc_num.value)">
									<option value="">--대분류를 선택하세요.--</option>
									<option value="100">디지털/가전</option>
									<option value="200">가구/인테리어</option>
									<option value="300">스포츠/레저</option>
									<option value="400">뷰티/미용</option>
									<option value="500">여성잡화</option>
									<option value="600">의류</option>
							</select> <select name="c_num" required="required">

									<option value="">--소분류입니다.--</option>

							</select></td>

						</tr>

						<tr>
							<th>입찰단위(₩)</th>
							<th>경매 시작 가격(₩)</th>
							<th>상품 상태</th>
						</tr>

						<tr>
							<td><input type="text" name="pd_unit" required="required" /></td>
							<td><input type="text" name="pd_price" required="required" /></td>
							<td><select name="pd_condition">
									<option>개봉</option>
									<option>미개봉</option>
									<option>사용감 있음</option>

							</select></td>
						</tr>
						<tr>
							<th>판매 마감일</th>
							<th>구매 날짜</th>
							<th>이미지 올리기(파일갯수제한 6개)</th>
						</tr>

						<tr>
							<td><input type="datetime-local" name="b_expiration"required="required"/></td>
							<td><input type="date" name="pd_buydate" required="required"/></td>
							<td><input type="file" id="pd_image" multiple name="pd_image" size=60
								required="required" accept="image/jpeg,image/gif,image/jpg,image/bmp,image/png"
								onchange=""></td>
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
								value="판매하기"></td>
							<td colspan="2"><input type="button" class="btn btn-default"
								value="뒤로가기"
								style="font-family: 'Nanum Gothic Coding', 'gulim'; text-align: center; font-size: 20px;"
								onclick="location.href='main.do'"></td>
						</tr>

					</table>
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