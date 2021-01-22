<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form>
		 <c:choose>
			<c:when test="${result==0 }">
				<script type="text/javascript">
					alert("시스템에 오류가 있습니다.");
					location.href = "admin.do";
				</script>
			</c:when>
			<c:when test="${result==1 }">
				<script type="text/javascript">
					alert("경매 중지 성공");
					location.href = "admin.do";
				</script>
			</c:when>
			<c:when test="${result==2 }">
				<script type="text/javascript">
					alert("구매확정 중지 성공");
					location.href = "admin.do";
				</script>
			</c:when>
			<c:when test="${result==3 }">
				<script type="text/javascript">
					alert("회원계정 중지 성공");
					location.href = "adminMember.do";
				</script>
			</c:when>
			<c:when test="${result==4 }">
				<script type="text/javascript">
					alert("회원계정 복구 성공");
					location.href = "adminMember.do";
				</script>
			</c:when>
		</c:choose> 
	</form>
</body>
</html>