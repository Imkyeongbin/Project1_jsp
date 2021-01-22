package service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
public class AjaxNickNameCheck_EditMyInfoAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
		System.out.println("AjaxNickNameCheckAction start...");
		String user_nickname = request.getParameter("user_nickname");
		
		HttpSession session = request.getSession();
		String user_email = session.getAttribute("user_email").toString();
		
		// email이 널값이거나 ""이면 loginForm.jsp로 보냄
		if (user_email.equals("") || user_email == null) {
			return "loginForm.jsp";
		}
		
		
		AuctionDAO ad = AuctionDAO.getInstance();
		
		System.out.println("AjaxNickNameCheckAction user_nickname->"+ user_nickname); 
		int checkNum = 0;
		try {
			  checkNum = ad.select_EditMyInfo(user_nickname, user_email);
				System.out.println("AjaxNickNameCheckAction DAO After"+checkNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("AjaxNickNameCheckActionSQLException->"+e.getMessage());
		}  
		
		System.out.println("AjaxNickNameCheckAction checkNum->"+checkNum);
		if (checkNum >= 1) {
			request.setAttribute("chkMsg2", "존재하는 닉네임입니다");
		}else if(checkNum == -1) {
			request.setAttribute("chkMsg2", "사용 불가능한 닉네임입니다");
		}else {
			request.setAttribute("chkMsg2", "사용 가능한 닉네임입니다");
			
		}
	
		return "AjaxNickNameCheckAction";
	}

}
