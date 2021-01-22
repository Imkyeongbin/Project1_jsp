<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<title>아이디 찾기</title>
<style type="text/css">
.container {
	width: 385px;
	line-height: 50px;
	margin: 40px auto;
}

h3 {
	text-align: center;
}

h3 span {
	color: black;
	font-weight: bold;
}


input {
	border: 1px solid lightgray;
	border-radius: 3px;
}


#submit{
   background-color: #000000; 
   color: white;
}
</style>
</head>

<body>
	<div class="container">
		
		<h3>
			<span>✓ 아이디 찾기</span>
		</h3>
		<hr />
		  <form action="findId.do" name="frm">
			<input type="text" name="user_name" id="user_name"
				class="user_name" placeholder="이름" required="required"
				maxlength="20" style="height: 35px; width: 380px;" /> <br> 
				
			   <input
				type="date" name="user_birth" class="user_birth"
				placeholder="YYYY-MM-DD"  title="생일을 입력하세요" id="user_birth" required="required"
				maxlength="20" style="height: 35px; width: 380px" /> <br> 
				
			   <input
				type="text" name="user_phone" class="user_birth"
				pattern="\d{2,3}-\d{3,4}-\d{4}" 
                placeholder="전화번호  xxx-xxxx-xxxx" title="3자리-4자리-4자리" required="required"
				maxlength="20" style="height: 35px; width: 380px" /> <br> 
				
				<hr />
			   
			  <input type="submit" id="submit" name="login" class="btn btn pull-right" value="확인"
			         style="padding: 10px 30px 10px 30px;" >
		</form>
	</div>

</body>
</html>

   