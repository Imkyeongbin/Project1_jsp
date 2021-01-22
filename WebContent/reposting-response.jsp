<%@page import="dao.Board"%>
<%@page import="dao.Product_Info"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매하기-Check</title>
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
	function executeDB() {
		alert('등록완료');
		return true;
		/* location.href = "postingResponse.do"; */
	}
</script>


</head>
<style>
th{
font-family: "Nanum Gothic", gulim;
	font-size: 15px;
	text-align: center;
}
td{
	font-family: "Nanum Gothic", gulim;
	text-align: center;
	font-size: 15px;
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
						name="search_word" placeholder="검색">
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
			<li class="firstMenuLi"><a class="menuLink" href="postingForm.jsp">판매하기</a></li>
			<c:if test="${loginState!=1}">
				<li class="firstMenuLi"><a class="menuLink" href="joinForm.jsp">회원가입</a></li>
				<li class="firstMenuLi"><a class="menuLink" href="loginForm.jsp">로그인</a></li>
			</c:if>
			<c:if test="${loginState==1}">
				<li class="firstMenuLi"><a class="menuLink" href="mysellList.do">판매목록</a></li>
				<li class="firstMenuLi"><a class="menuLink" href="biddingList.do">입찰목록</a></li>
				<li class="firstMenuLi"><a class="menuLink" href="#">${user_email }</a>
					<ul class="submenu">
						<li class="firstMenuLi"><a class="submenuLink longLink" href="jjimList.do">찜 목록</a></li>
						<li class="firstMenuLi"><a class="submenuLink longLink" href="auctionMoney.do">옥션머니</a></li>
						<li class="firstMenuLi"><a class="submenuLink longLink" href="editMyInfoForm.do">회원정보 수정</a></li>
					</ul>
				</li>
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
		<h1 class="sort_type">내가 쓴 글 확인 창</h1><br><br>
		<div class="post_comment">
		<p>글을 한 번 등록하면 다시 수정할 수 없습니다</p>
		<p>신중하게 생각하신 후에 등록확정 버튼을 누르시기 바랍니다.</p>
		</div><br><br>
		<form action="reposting3.do" method="post"
			onsubmit="return executeDB()">
			<table class="table table-striped" style="margin-left: auto; margin-right: auto;">


				<tr>
					<th>글제목</th>
					<th>상품명</th>
					<th>작성일</th>
				</tr>

				<tr>
					<td>${b_title }</td>
					<td>${pd_name }</td>
					<jsp:useBean id="now" class="java.util.Date" />
 					<fmt:formatDate value="${now}" pattern="yyyy/MM/dd HH:mm:ss" var="today" /> 
					<td>${today}</td>
				</tr>

				<tr>
					<th>입찰단위(₩)</th>
					<th>경매 시작 가격(₩)</th>
					<th>상품 상태</th>
				</tr>

				<tr>
					<td>${pd_unit }</td>
					<td>${pd_price }</td>
					<td>${pd_name }</td>

				</tr>
				<tr>
					<th colspan="2">판매 마감일</th>
					<th colspan="2">구매 날짜</th>
				</tr>
				<tr>
					<td colspan="2">${b_expiration }</td>
					<td colspan="2">${pd_buydate }</td>
				</tr>
				<tr>
					<th colspan="3">내용</th>
				</tr>
				<tr>

					<td colspan="4">${b_contents }</td>

				</tr>
				<tr>
					<th colspan="3">이미지</th>

				</tr>
			</table>
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<td><c:forEach var="temp" items="${files}" varStatus="status">

							<c:if test="${status.count%3==0 }">
								<img alt="전송사진" src="${uploadUri}${temp}" width="120"
									height="120"></img>
								<br />
							</c:if>

							<c:if test="${status.count%3!=0 }">
								<img alt="전송사진" src="${uploadUri}${temp}" width="120"
									height="120"></img>
							</c:if>
						</c:forEach></td>
				</tr>

			</table>
		    <input type="submit" class="btn btn-default" value="등록확정">
			<input type="button" class="btn btn-default" value="취소(메인으로)" onclick="location.href='main.do'">	
			
			<input type="hidden" name="pd_unit" value="${pd_unit}">
			<input type="hidden" name="pd_price" value="${pd_price}">
			<input type="hidden" name="b_title" value="${b_title}">
			<input type="hidden" name="b_expiration" value="${b_expiration}">
			<input type="hidden" name="b_contents" value="${b_contents}">
			
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
	<br>
	<br>
	<div class="footer">
		베이는 마켓플레이스(오픈마켓) 상품, 거래정보 및 거래 등에 대하여 책임을 지지 않습니다. <br> 기타사항에 대한
		문의 : admin@admin.com / Copyright © MarCatBay Corp. 2010-2020 All
		Rights Reserved.
	</div>
</body>
</html>




