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
			<c:set var="b_num" value='${b_num }'></c:set>
				<script type="text/javascript">
					alert("현재 입찰 최고가보다 입력하신 금액이 적습니다.");
					location.href = "bidForm.do?b_num=${b_num}";
				</script>
			</c:when>
			<c:when test="${result==1 }">
			<c:set var="b_num" value='${b_num }'></c:set>
				<script type="text/javascript">
					alert("입찰참여 성공");
					location.href = "productDetail.do?b_num=${b_num}";
				</script>
			</c:when>
			<c:when test="${result==2 }">
			<c:set var="b_num" value='${b_num }'></c:set>
				<script type="text/javascript">
					alert("상품시작가격보다 입력하신 금액이 적습니다.");
					location.href = "bidForm.do?b_num=${b_num}";
				</script>
			</c:when>
			<c:when test="${result==3 }">
			<c:set var="b_num" value='${b_num }'></c:set>
				<script type="text/javascript">
					alert("잔액이 부족합니다.");
					location.href = "bidForm.do?b_num=${b_num}";
				</script>
			</c:when>
			<c:when test="${result==4 }">
			<c:set var="b_num" value='${b_num }'></c:set>
				<script type="text/javascript">
					alert("자신이 등록한 상품에는 입찰할 수 없습니다.");
					location.href = "productDetail.do?b_num=${b_num}";
				</script>
			</c:when>
		</c:choose> 
	</form>
</body>
</html>