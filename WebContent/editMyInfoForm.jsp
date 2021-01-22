<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String context = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
input {
	style ="background-color: #000000;
	border-color: #A2A2A2;
	"
}

.display_table {
	display: table;
}

.display_table_cell {
    vertical-align: center;
    padding: 85px;
}
</style>
<link rel="stylesheet" href="css/team1_nav_custom.css">
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
	function bc_clk(c_num) {
		//카테고리 첫번째소분류(num) 정보들을 인기순(state=1)으로 보여줌
		location.href = "categories.do?sort_state=1&bc_num="+(c_num-(c_num%100))+"&c_num=" + c_num;
	}
	function doOpenCheck(chk) {
		var obj = document.getElementsByName("check_bc_num");
		for (var i = 0; i < obj.length; i++) {
			if (obj[i] != chk) {
				obj[i].checked = false;
			}
		}
	}
	
	$(function() {
		$(".alert-success").hide();
		$(".alert-different").hide();
		$(".pwd1,.pwd2").keyup(function() {
			var pwd1 = $(".pwd1").val();
			var pwd2 = $(".pwd2").val();
			if (pwd1 != "" || pwd2 != "") {
				if (pwd1 == pwd2) {
					$(".alert-success").show();
					$(".alert-different").hide();
					$("#submit").removeAttr("disabled");
				} else {
					$(".alert-success").hide();
					$(".alert-different").show();
					$("#submit").attr("disabled", "disabled");
				}
			}
		});
	});
	
	$(function() {
		$(".pin_alert-success").hide();
		$(".pin_alert-different").hide();
		$(".ppwd1,.ppwd2").keyup(function() {
			var ppwd1 = $(".ppwd1").val();
			var ppwd2 = $(".ppwd2").val();
			if (ppwd1 != "" || ppwd2 != "") {
				if (ppwd1 == ppwd2) {
					$(".pin_alert-success").show();
					$(".pin_alert-different").hide();
					$("#submit").removeAttr("disabled");
				} else {
					$(".pin_alert-success").hide();
					$(".pin_alert-different").show();
					$("#submit").attr("disabled", "disabled");
				}
			}
		});
	});
	
	// 닉네임 중복체크
    function nickNameChk(){
		 var user_nickname = $("#user_nickname").val();
		 console.log(user_nickname);
		$.ajax({
			url:"<%=context%>/ajaxNickNameCheck_EditMyInfo.do",
			data : {
				user_nickname : user_nickname
			},
			dataType : 'text',
			success : function(data) {
				dataq = this.data;
				/* alert("ajaxNickNameCheck_EditMyInfo->" + data); */
				if (data == '사용 가능한 닉네임입니다') {
					$('#msg2').html(data);
					$('#user_nicknameStatus').val("1");
					$("#submit").removeAttr("disabled");

				} else {
					$('#msg2').html(data);
					$('#user_nicknameStatus').val("0");
					$("#submit").attr("disabled", "disabled");

				}
			}
		});
	}
	function disableSubmit() {
		$("#submit").attr("disabled", "disabled");
	}

	function sample6_execDaumPostcode() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
						// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

						// 각 주소의 노출 규칙에 따라 주소를 조합한다.
						// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						var addr = ''; // 주소 변수
						var extraAddr = ''; // 참고항목 변수

						//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							addr = data.roadAddress;
						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							addr = data.jibunAddress;
						}

						// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
						if (data.userSelectedType === 'R') {
							// 법정동명이 있을 경우 추가한다. (법정리는 제외)
							// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
							if (data.bname !== ''
									&& /[동|로|가]$/g.test(data.bname)) {
								extraAddr += data.bname;
							}
							// 건물명이 있고, 공동주택일 경우 추가한다.
							if (data.buildingName !== ''
									&& data.apartment === 'Y') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
							if (extraAddr !== '') {
								extraAddr = ' (' + extraAddr + ')';
							}
							// 조합된 참고항목을 해당 필드에 넣는다.
							document.getElementById("sample6_extraAddress").value = extraAddr;

						} else {
							document.getElementById("sample6_extraAddress").value = '';
						}

						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('sample6_postcode').value = data.zonecode;
						document.getElementById("sample6_address").value = addr;
						// 커서를 상세주소 필드로 이동한다.
						document.getElementById("sample6_detailAddress")
								.focus();
					}
				}).open();
	}

	function fileUpload(fis) {
		// 이미지를 변경한다.
		var reader = new FileReader();
		reader.onload = function(e) {
			$('#loadImg').attr('src', e.target.result);
		}
		reader.readAsDataURL(fis.files[0]);
	}
	
	//관심항목 체크 안된 경우 서브밋 비활성화 
	$(document).ready(function(){
		if($("input[name='check_bc_num']:checked").length==0){
			$("#submit").attr("disabled", "disabled");
		}
	    $(".checkBoxId").change(function(){
	    	if($("input[name='check_bc_num']:checked").length==0){
	    		$("#submit").attr("disabled", "disabled");
	    	}
	    });
	});
	function checkCheck_bc_num(){
		if($("input[name='check_bc_num']:checked").length==0){
			alert("관심항목을 체크해주세요")
			return false;
		}
	}

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
	
	<!-- Here is main container start -->
	<!-- 컨테이너 시작 -->
	<div class="container" style="text-align : center;">
		<div class="editMyInfoForm">
		<h2 class="sort_type" style="text-align: center;">회원정보 수정</h2>
		<form action="editMyInfoPro.do" method="post"
			enctype="multipart/form-data" name="frm">
			<table class="table table-bordered"
				style="text-align: left;">
				<tr>
					<td>email</td>
					<td>${user_info.user_email }<input type="hidden"
						name="user_email" value="${user_info.user_email}">
					</td>

					<td align="center" rowspan="7">
						<div class="display_table">
							<div class="display_table_cell" align="center">
								<c:if test="${user_info.user_image!=null }">
									<img src="image/profile_image/${user_info.user_image }" class="img-thumbnail"
										id="loadImg" width="200" height="200" alt="user_image">
									<input type="hidden" name="original_image"
										value="${user_info.user_image}">
								</c:if>
								<c:if test="${user_info.user_image==null }">
									<img src="image/profile_image/img01.png" id="loadImg"
										width="200" height="200" alt="user_image">
								</c:if>
								<br> <input type="file" class="btn btn-primary"
									style="background-color: #000000; border-color: #A2A2A2;"
									id="img" name="user_image" accept=".jpg,.png,.jpeg,.gif"
									onchange="fileUpload(this);">
								<h6>그림 파일은 5MB이하로 올려주셔야 업로드가 됩니다.</h6>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>암호</td>
					<td><input type="password" name="user_password" class="pwd1"
						maxlength="50" required="required"></td>
				</tr>
				<tr>
					<td>암호 확인</td>
					<td><input type="password" name="user_password2" class="pwd2"
						maxlength="50" required="required">
						<div class="alert-success">비밀번호가 일치합니다.</div>
						<div class="alert-different">비밀번호가 불일치합니다.</div></td>
				</tr>
				<tr>
					<td>닉네임</td>
					<td><input type="text" id="user_nickname" name="user_nickname"
						onkeyup="disableSubmit()" onchange="disableSubmit()"
						value="${user_info.user_nickname }" maxlength="20"
						required="required"> <input type="hidden"
						name="user_nicknameStatus" id="user_nicknameStatus" value="0">
						<button id="chk" class="btn btn-primary"
							style="background-color: #000000; border-color: #A2A2A2;"
							type="button" onclick="nickNameChk()">중복체크</button> <br> <span
						id="msg2"></span>

						<p></td>
				</tr>
				<tr>
					<td>유저 이름</td>
					<td>${user_info.user_name }</td>
				</tr>

				<tr>
					<td>전화 번호</td>
					<td><input type="text" name="user_phone" required="required"
						pattern="\d{2,3}-\d{3,4}-\d{4}" placeholder="xxx-xxxx-xxxx"
						title="2,3자리-3,4자리-4자리" value="${user_info.user_phone }">
					</td>
				</tr>
				<tr>
					<td>생년 월일</td>
					<td>${user_info.user_birth }</td>
				</tr>
				<tr>
					<td>성별</td>
					<td><c:if test="${user_info.user_gender == 'female'}">여성</c:if>
						<c:if test="${user_info.user_gender == 'male'}">남성</c:if></td>
					<td rowspan="3">
						<table style="text-align: left;">
							<tr>
								<th colspan="3">관심항목
								<input type="hidden" name="interest_original" value="${interest.bc_num}"></th>
							</tr>
							<tr>
								<td><c:if test="${interest.bc_num == 100 }">
										<input type="checkbox" name="check_bc_num" value="100"
											checked="checked" onclick="doOpenCheck(this);">
									</c:if> <c:if test="${interest.bc_num != 100 }">
										<input type="checkbox" name="check_bc_num" value="100"
											onclick="doOpenCheck(this);">
									</c:if>디지털/가전</td>
								<td><c:if test="${interest.bc_num == 200 }">
										<input type="checkbox" name="check_bc_num" value="200"
											checked="checked" onclick="doOpenCheck(this);">
									</c:if> <c:if test="${interest.bc_num != 200 }">
										<input type="checkbox" name="check_bc_num" value="200"
											onclick="doOpenCheck(this);">
									</c:if>가구/인테리어</td>
								<td><c:if test="${interest.bc_num == 300 }">
										<input type="checkbox" name="check_bc_num" value="300"
											checked="checked" onclick="doOpenCheck(this);">
									</c:if> <c:if test="${interest.bc_num != 300 }">
										<input type="checkbox" name="check_bc_num" value="300"
											onclick="doOpenCheck(this);">
									</c:if>스포츠/레저</td>
							</tr>

							<tr>
								<td><c:if test="${interest.bc_num == 400 }">
										<input type="checkbox" name="check_bc_num" value="400"
											checked="checked" onclick="doOpenCheck(this);">
									</c:if> <c:if test="${interest.bc_num != 400 }">
										<input type="checkbox" name="check_bc_num" value="400"
											onclick="doOpenCheck(this);">
									</c:if>뷰티/미용</td>
								<td><c:if test="${interest.bc_num == 500 }">
										<input type="checkbox" name="check_bc_num" value="500"
											checked="checked" onclick="doOpenCheck(this);">
									</c:if> <c:if test="${interest.bc_num != 500 }">
										<input type="checkbox" name="check_bc_num" value="500"
											onclick="doOpenCheck(this);">
									</c:if>여성잡화</td>
								<td><c:if test="${interest.bc_num == 600 }">
										<input type="checkbox" name="check_bc_num" value="600"
											checked="checked" onclick="doOpenCheck(this);">
									</c:if> <c:if test="${interest.bc_num != 600 }">
										<input type="checkbox" name="check_bc_num" value="600"
											onclick="doOpenCheck(this);">
									</c:if>의류</td>
							</tr>
						</table>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" id="sample6_postcode"
						placeholder="우편번호" name="zip" value="${zip }"> <input
						type="button" class="btn btn-primary"
						style="background-color: #000000; border-color: #A2A2A2;"
						onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
						<input type="text" id="sample6_address" placeholder="주소"
						name="address" value="${address }"><br> <input
						type="text" id="sample6_detailAddress" placeholder="상세주소"
						name="addressInDetail" value="${addressInDetail }"> <input
						type="text" id="sample6_extraAddress" placeholder="참고항목"
						name="referenceItem" value="${referenceItem }"><br></td>
				</tr>
				<tr>
					<td>핀번호</td>
					<td><input type="password" name="user_pin" class="ppwd1"
						maxlength="6" placeholder="옥션 머니 출금 비밀번호"
						value="${user_info.user_pin }"></td>
				</tr>
				<tr>
					<td>핀번호 확인</td>
					<td><input type="password" name="user_pin2" class="ppwd2"
						maxlength="6" placeholder="옥션 머니 출금 비밀번호 확인"
						value="${user_info.user_pin }">
						<div class="pin_alert-success">핀번호가 일치합니다.</div>
						<div class="pin_alert-different">핀번호가 불일치합니다.</div>
						<p></td>
				</tr>
				<tr>
					<td></td>
					<td><input class="btn btn-primary"
						style="background-color: #000000; border-color: #A2A2A2;"
						type="submit" id="submit" value="수정하기"> <input
						class="btn btn-primary"
						style="background-color: #000000; border-color: #A2A2A2;"
						type="reset" value="취소"></td>
					<td><button class="btn btn-primary"
							style="background-color: #000000; border-color: #A2A2A2;"
							onclick="location.href='withdrawlForm.do'">회원탈퇴</button></td>
				</tr>

			</table>
			<br>
		</form>
		</div>
	</div>
	<div align="center">
		<button class="btn btn-primary"
			style="background-color: #000000; border-color: #A2A2A2;"
			onclick="history.back();">돌아가기</button>
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