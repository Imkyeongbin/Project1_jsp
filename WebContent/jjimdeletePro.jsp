<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<c:if test="${result == 1 }">
  <script type="text/javascript">
     alert("삭제 완료!!");
     location.href = "jjimList.do";
  
  </script>
</c:if>
<c:if test="${result == 0}">
 <script type="text/javascript">
     alert("삭제 실패;;");
     location.href = "jjimList.do";
   
 </script>

</c:if>
<body>
</body>
</html>