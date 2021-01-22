<%
	String user_email = (String)session.getAttribute("user_email");
	int loginState = Integer.parseInt((String)session.getAttribute("loginState"));
	if(loginState!=1){
		response.sendRedirect("loginForm.jsp");
	}
	else{
		request.setAttribute("loginState",loginState);
		request.setAttribute("user_email",user_email);	
	}
%>