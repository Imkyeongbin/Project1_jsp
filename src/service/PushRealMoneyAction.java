package service;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;


public class PushRealMoneyAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		int result =0;
		int charge_money = Integer.parseInt(request.getParameter("charge_money"));
		System.out.println("입력한 충전금액=>" + charge_money);
		try {

			AuctionDAO ad = AuctionDAO.getInstance();
			
			result = ad.charge_money(user_email, charge_money);
			if(result ==1) {
				result = 2;
			}
			request.setAttribute("result", result);
			
		} catch (Exception e) {
			System.out.println("PushRealMoneyAction.requesetPro->" + e.getMessage());
		}
		System.out.println("result 값=>"+result);
		return "accountResult.jsp";
	}
}
