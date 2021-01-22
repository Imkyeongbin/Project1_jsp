<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<style type="text/css">
.container {
	width: 385px;
	line-height: 50px;
	margin: 40px auto;
}

h3 {
	text-align: center;
	font-weight: bold;
}

h3 span {
	color: black;
}

.login {
	background-color: #000000;
	color: white;
	border-radius: 5px;
	border: 0;
	padding: 11px 50px;
}

.signup {
	background-color: #000000;
	color: white;
	border-radius: 5px;
	border: 0;
	padding: 11px 50px;
}

i {
	color: lightgray;
}

#i_email {
	position: absolute;
	top: 130px;
	margin: 0 355px;
}

#i_password {
	position: absolute;
	top: 180px;
	margin: 0 355px;
}

input {
	border: 1px solid lightgray;
	border-radius: 3px;
}

#login{
  background-color: white; 
  color: black;
}

#signup{
   background-color: #000000; 
   color: white;
}

 p{
 text-align: right;
 }


</style>
<!-- <link rel="stylesheet" href="css/team1_nav_custom.css"> -->
<script type="text/javascript">
	
</script>

</head>
<body>
	<div class="container">
		<div id="i_email">
			<i class="material-icons">person_outline</i>
		</div>

		<div id="i_password">
			<i class="material-icons">lock_outline</i>
		</div>
		<h3>
			<span>로그인</span>
		</h3>
		<hr />
		<form action="loginPro.do" name="frm" method="post">
			<input type="email" name="user_email" id="user_email"
				class="user_email" placeholder="아이디(이메일)" required="required"
				maxlength="50" style="height: 35px; width: 380px;" /> <br> 
				
			   <input
				type="password" name="user_password" class="user_password"
				placeholder="비밀번호" id="user_password" required="required"
				maxlength="20" style="height: 35px; width: 380px" /> <br> 
			   
			  <input type="submit" id="login" name="login" class="btn btn-default btn-lg btn-block" 
			         style="height:50px; width:380px;" value="로그인">
			
			  <input type="button" id="signup" class="btn btn-primary btn-lg btn-block" value="가입하기"
				     style="height:50px; width:380px;" onclick="location.href='joinForm.jsp'">
				
			  <p>	
			  <a href="findIdForm.jsp">아이디 찾기</a>
			  </p>
		</form>
		
	</div>

</body>
</html>