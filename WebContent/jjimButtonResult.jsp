<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${loginState!=1 }">
		<script type="text/javascript">
			alert("로그인을 해주세요");
			location.href="loginForm.jsp"
		</script>
	</c:if>
	<c:if test="${loginState==1}">
		<c:if test="${result==2}">
			<script type="text/javascript">
				var msg = "상품번호 : " + ${jjim_pd_num}
				+" 찜하기 성공";
				alert(msg);
				location.href="main.do";
			</script>
		</c:if>

		<c:if test="${result==3}">
			<script type="text/javascript">
				var msg = "상품번호 : " + ${jjim_pd_num}
				+" 찜하기 실패";
				alert(msg);
				location.href="main.do";
			</script>
		</c:if>

		<c:if test="${result==4}">
			<script type="text/javascript">
				var msg = "상품번호 : " + ${jjim_pd_num}
				+"찜 취소";
				alert(msg);
				location.href="main.do";
			</script>
		</c:if>

		<c:if test="${result==5}">
			<script type="text/javascript">
				var msg = "상품번호 : " + ${jjim_pd_num}
				+"찜 취소 실패";
				alert(msg);
				location.href="main.do";
			</script>
		</c:if>
	</c:if>
</body>
</html>