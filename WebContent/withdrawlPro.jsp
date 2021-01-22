<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴 완료</title>

</head>
<body>
<c:if test="${result == 1 }">
	<script type="text/javascript">
		alert("이용해 주셔서 감사합니다.");
		location.href="main.do";
	</script>
</c:if>
<c:if test="${result == 0 }">
	<script type="text/javascript">
		alert("회원 정보가 맞지 않습니다.");
		history.back();
	</script>
</c:if>
</body>
</html>