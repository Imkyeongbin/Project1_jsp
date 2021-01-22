package service;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;

public class LoginProAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user_email = request.getParameter("user_email");
		String user_password = request.getParameter("user_password");
		int result = 0;
		

		try {
			AuctionDAO ad = AuctionDAO.getInstance();
			result = ad.loginCheck(user_email, user_password);
			// result=0 ->로그인 실패, result=1 -> 로그인 성공, result=3 -> 로그인은 성공인데 탈퇴한 사람, result=4 계정중지
			// result=5 관리자 페이지
		} catch (Exception e) {
			System.out.println("LoginProAction.requestPro -> " + e.getMessage());
		}
		HttpSession session = request.getSession();
		session.setAttribute("user_email", user_email);
		session.setAttribute("loginState", result);
		return "loginPro.jsp";
	} 

}    
