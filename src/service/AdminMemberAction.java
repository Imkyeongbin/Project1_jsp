package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.User_Info;

public class AdminMemberAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		int loginState = 1;
		if (user_email == "" || user_email == null) {
			loginState = 0;
		}
		int adminState =1;
		request.setAttribute("loginState", loginState);
		request.setAttribute("adminState", adminState);

		try {
			AuctionDAO ad = AuctionDAO.getInstance();

			List<User_Info> admin_member = ad.admin_member();

			request.setAttribute("admin_member", admin_member);

		} catch (Exception e) {
			System.out.println("AdminAction.requesetPro->" + e.getMessage());
		}
	
		return "adminMember.jsp";
	
	}

}
