package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.BiddingList;

public class BiddingAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		if(user_email==null||user_email=="") {
			return "loginForm.jsp";
		}
		
		AuctionDAO au = AuctionDAO.getInstance();

		try {
			List<BiddingList> bidlist = au.bidding(user_email);
			int bidlistSize = bidlist.size();
			request.setAttribute("bidlistSize", bidlistSize);
			request.setAttribute("bl", bidlist);
			//System.out.println("list=="+bidlist);

		} catch (Exception e) {
			System.out.println("jjimaction >" + e.getMessage());
		}


		return "biddingList.jsp";
	}

}
