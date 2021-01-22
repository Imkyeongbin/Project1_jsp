package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Auction_Money;
import dao.Bid_State;
import dao.Board;
import dao.Jjim;
import dao.Product_Info;

public class AuctionMoneyAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String user_email = (String)session.getAttribute("user_email");
		
		try {
			AuctionDAO ad = AuctionDAO.getInstance();
			
			List<Auction_Money> amoney = ad.getBalance(user_email);
			
			request.setAttribute("amoney", amoney);
			
			int balance = amoney.get(0).getBalance();
			request.setAttribute("balance", balance);
			
			System.out.println("balance->"+balance);
			
		}catch(Exception e) {
			System.out.println("AuctionMoneyAction.requesetPro->"+e.getMessage());
		}
		return "auctionMoney.jsp";
	}

}
