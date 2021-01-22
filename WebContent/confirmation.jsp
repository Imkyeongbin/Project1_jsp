<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매 확정 페이지</title>

</head>
<body>
	<c:if test="${result < 0 }">
		<script type="text/javascript">
			alert("이미 구매 확정된 상품입니다.");
			location.href = "main.do";
		</script>
	</c:if>
	
	<c:if test="${result == 0 }">
		<script type="text/javascript">
			alert("구매 확정 실패...");
			history.back();
		</script>
	</c:if>
	<c:if test="${result == 1 }">
		<script type="text/javascript">
			alert("구매 확정 완료");
			location.href = "main.do";
		</script>
	</c:if>
</body>
</html>