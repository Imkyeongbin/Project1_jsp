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

		<c:if test="${result==0 }">
			<script type="text/javascript">
				alert("옥션머니 입출금에 오류가 발생하였습니다. 관리팀으로 연락주시기 바랍니다.");
				location.href = "auctionMoney.do";
			</script>
		</c:if>
		<c:if test="${result==1 }">
			<script type="text/javascript">
				alert("출금되었습니다.");
				location.href = "auctionMoney.do";
			</script>
		</c:if>
		<c:if test="${result==2 }">
			<script type="text/javascript">
				alert("옥션머니 입금 성공");
				location.href = "auctionMoney.do";
			</script>
		</c:if>
		<c:if test="${result==3 }">
			<script type="text/javascript">
				alert("잔액이 부족하여 출금할 수 없습니다.");
				location.href = "auctionMoney.do";
			</script>
		</c:if>
		<c:if test="${result==4 }">
			<script type="text/javascript">
				alert("PIN번호가 잘못 입력되었습니다.");
				location.href = "auctionMoney.do";
			</script>
		</c:if>
	</form>
</body>
</html>