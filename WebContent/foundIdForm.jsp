<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<title>비밀번호 설정</title>
 <style type="text/css">
.container {
	width: 385px;
	line-height: 50px;
	margin: 40px auto;
}

h3 {
	text-align: left;
}

h3 span {
	color: black;
	font-weight: bold;
}


input {
	border: 1px solid lightgray;
	border-radius: 3px;
}


#change{
   background-color: #000000; 
   color: white;
}
</style>
<script type="text/javascript">
$(function() {
	$(".alert-success").hide();
	$(".alert-different").hide();
	$("#user_password, #user_password1").keyup(function() {
		var user_password = $("#user_password").val();
		var user_password1 = $("#user_password1").val();
		if (user_password != "" || user_password1 != "") {
			if (user_password == user_password1) {
				$(".alert-success").show();
				$(".alert-different").hide();
				$("#change").removeAttr("disabled");
			} else {
				$(".alert-success").hide();
				$(".alert-different").show();
				$("#change").attr("disabled", "disabled");
			}
		}
	});
});
  
   </script>
</head>

<body>
	<div class="container">
		
		<h3>
			<span>고객님의 아이디는 ${user_email }입니다.</span>
		</h3>
		<hr />
		   <form action="newPassword.do" name="frm" onsubmit="return chk()">
		     <input type="hidden" name="user_email" value="${user_email}">
			<input type="password" name="user_password" id="user_password" maxlength="50" placeholder="비밀번호를 입력하세요"
			       style="height: 35px; width: 380px;" required="required"> <br> 
				
	   	    <input type="password" name="user_password1" id="user_password1" maxlength="50" required="required" placeholder="비밀번호 재확인" 
	   	           style="height: 35px; width: 380px" /> <br>
					<div class="alert-success">
						비밀번호가 일치합니다.
					</div>
					<div class="alert-different">
						비밀번호가 불일치합니다.
					</div>
			  <input type="submit" id="change" name="change" class="btn btn pull-right" value="변경하기"
			         style="padding: 10px 30px 10px 30px;" >
		</form>
	</div>

</body>
</html>

   
