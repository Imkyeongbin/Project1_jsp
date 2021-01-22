<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정 완료</title>
</head>
<body>
	<%-- <c:if test="${delete_result == 1}">
		<script type="text/javascript">
			alert("이전 프로필 사진 삭제완료..");
		</script>
	</c:if>
	<c:if test="${delete_result == 0}">
		<script type="text/javascript">
			alert("이전 프로필 사진 삭제실패;;");
		</script>
	</c:if> --%>
	
	<c:if test="${update_result == 1 }">
		<script type="text/javascript">
			alert("수정 완료 !");
			location.href = "main.do";
		</script>
	</c:if>
	<c:if test="${update_result == 0 }">
		<script type="text/javascript">
			alert("수정 실패...");
			history.back();
		</script>
	</c:if>
	<c:if test="${update_result == 2 }">
		<script type="text/javascript">
			alert("중복된 닉네임입니다. 다른 닉네임을 사용해주세요.");
			history.back();
		</script>
	</c:if>
</body>
</html>