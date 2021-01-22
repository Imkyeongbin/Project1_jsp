package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Admin_Board;
import dao.AuctionDAO;

public class AdminStopAuctionAction implements CommandProcess {

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
		String b_num = request.getParameter("b_num");
		System.out.println("admin b_num"+b_num);
		int result = 0;
		try {
			AuctionDAO ad = AuctionDAO.getInstance();

			result = ad.adminStopAuction(b_num);

			request.setAttribute("result", result);

		} catch (Exception e) {
			System.out.println("AdminAction.requesetPro->" + e.getMessage());
		}
	
		return "adminAlert.jsp";
	}

}
