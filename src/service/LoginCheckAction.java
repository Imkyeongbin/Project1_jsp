package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;

public class LoginCheckAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(); 
		String user_email = (String)session.getAttribute("user_email");
		String b_num = request.getParameter("b_num");
		System.out.println("loginCheckAction.checkcheck => "+b_num);
		request.setAttribute("b_num", b_num);
		if(user_email==null) {
			return "loginForm.jsp";
		}
		return "bidForm.do";
	}

}
