package service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AuctionDAO;
public class AjaxIdCheckAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); // 서버에서 전달해줄때 나의 컨텐츠타입이 html이고 ~ 세팅하기
		System.out.println("AjaxIdCheckAction start...");
		String user_email = request.getParameter("user_email");
		AuctionDAO ad = AuctionDAO.getInstance();
		// 이메일 존재여부 체크
		System.out.println("AjaxIdCheckAction user_email->"+user_email); 
		int checkNum = 0;
		try {
			  checkNum = ad.check(user_email);
				System.out.println("AjaxIdCheckAction DAO After"+checkNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("AjaxIdCheckActionSQLException->"+e.getMessage());
		}  
		
		System.out.println("AjaxIdCheckAction checkNum->"+checkNum);
		if (checkNum == 1) {
			request.setAttribute("chkMsg1", "아이디가 존재합니다");
			
		}else {
			request.setAttribute("chkMsg1", "사용 가능한 아이디입니다");
			
		}
	
		return "AjaxIdCheckAction";
	}

}
