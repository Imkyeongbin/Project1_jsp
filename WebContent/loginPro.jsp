<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <c:if test="${loginState == 0 }">
       <script type="text/javascript">
       alert("없는 계정이거나 아이디, 비밀번호를 확인하세요!");
       location.href="loginForm.jsp";
    </script>
    </c:if>
     <c:if test="${loginState == 1 }">
       <script type="text/javascript">
       alert("환영합니다.");
       location.href="main.do";
    </script>
    </c:if>
    
     <c:if test="${loginState == 3 }">
       <script type="text/javascript">
       alert("이미 탈퇴된 회원입니다.");
       location.href="loginForm.jsp";
    </script>
    </c:if>
    
    <c:if test="${loginState == 4 }">
       <script type="text/javascript">
       alert("이용이 중지된 계정입니다. 관리팀으로 문의주세요");
       location.href="loginForm.jsp";
    </script>
    </c:if>
    
    <c:if test="${loginState == 5 }">
       <script type="text/javascript">
       alert("관리자 페이지입니다.");
       location.href="admin.do";
    </script>
    </c:if>
</body>
</html>