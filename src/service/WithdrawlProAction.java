package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;

public class WithdrawlProAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user_email = "";
		String passwd = request.getParameter("passwd");
		AuctionDAO ad = AuctionDAO.getInstance();
		int result = 0;
		HttpSession session = null;
		try {
			session = request.getSession();
			user_email = session.getAttribute("user_email").toString();
		}catch(NullPointerException e) {
		}
		//email이 널값이거나 ""이면 loginForm.jsp로 보냄
		if(user_email.equals("")||user_email == null) {
			return "loginForm.jsp";
		}
		//user_email로 User_Info를 받아옴.
		
		try {
			result = ad.withdrawl(user_email, passwd);
			request.setAttribute("result", result);
			
		}catch(Exception e) {
			System.out.println("WithdrawlFormAction.requestPro -> "+ e.getMessage());
		}
		if(result == 1) {
			session.invalidate();
		}
		
		return "withdrawlPro.jsp";
	}

}
