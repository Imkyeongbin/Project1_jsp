package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;

public class GetRealMoneyAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		
		String user_pin = request.getParameter("user_pin");
		
		int result =0;
		int withdraw_money = Integer.parseInt(request.getParameter("withdraw_money"));
		System.out.println("입력한 충전금액=>" + withdraw_money);
		try {
			AuctionDAO ad = AuctionDAO.getInstance();
			int check_result = ad.pin_check(user_email, user_pin);
			if(check_result == 1) {
				result = ad.withdraw_money(user_email, withdraw_money);
				
				request.setAttribute("result", result);
			}else {
				result=4;
				request.setAttribute("result", result);
			}
			

		} catch (Exception e) {
			System.out.println("GetRealMoneyAction.requesetPro->" + e.getMessage());
		}
		System.out.println("result 값=>"+result);
		return "accountResult.jsp";
	}
}
