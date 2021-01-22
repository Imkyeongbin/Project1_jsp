package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;

public class ConfirmationAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		if(user_email==null||user_email=="") {
			return "loginForm.jsp";
		}
		
		String seller_email = request.getParameter("seller_email");
		int bid_num_maxBid_price = Integer.parseInt(request.getParameter("bid_num_maxBid_price"));
		System.out.println("bid_num_maxBid_price ->" + bid_num_maxBid_price);
		int max_bid_price = Integer.parseInt(request.getParameter("max_bid_price"));
		System.out.println("max_bid_price ->" + max_bid_price);
		String b_num = request.getParameter("b_num");
		
		AuctionDAO ad = AuctionDAO.getInstance();
		
		int result = 0;		// b_num으로 confirm_status값 확인후, 최신의 auction_money.balance값을 찾고 max_bid_price를 더한 값을 insert
		try { 
			result = ad.update_confirmation(b_num, bid_num_maxBid_price, seller_email, max_bid_price);
		}catch(Exception e) {
			System.out.println("ConfirmationAction.requestPro1 =>"+e.getMessage());
		}
		System.out.println("ConfirmationAction.requestPro result =>"+result );
		
		
		if(result==1) {	
			try {
				result= ad.updateConfirm_status(b_num);	//confirm_status b_num값으로 업데이트 0->1
				
			}catch(Exception e) {
				System.out.println("ConfirmationAction.requestPro2 =>"+e.getMessage());
			}
		}
		request.setAttribute("result", result);
		
		return "confirmation.jsp";
	}

}
