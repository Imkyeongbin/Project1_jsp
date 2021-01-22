package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.User_Info;

public class WithdrawlFormAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		AuctionDAO ad = AuctionDAO.getInstance();
		//로그인 여부 확인
		HttpSession session = request.getSession();
		String user_email = session.getAttribute("user_email").toString();
		if(user_email==null) {
			user_email=null;
		}		
		// email이 널값이거나 ""이면 loginForm.jsp로 보냄
		if (user_email.equals("") || user_email == null) {
			return "loginForm.jsp";
		}
		//user_email로 User_Info를 받아옴.
		try {
			User_Info ui = ad.getUserInfo_EditMyInfo(user_email);
			request.setAttribute("user_info", ui);
		}catch(Exception e) {
			System.out.println("WithdrawlFormAction.requestPro -> "+ e.getMessage());
		}
		
		
		return "withdrawlForm.jsp";
	}

}
