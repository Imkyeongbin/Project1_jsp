package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.BiddingList;

public class MysellAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		
		AuctionDAO au = AuctionDAO.getInstance();

		if (user_email == null || user_email == "") {
			return "loginForm.jsp";
		}
	    
		try {
			List<BiddingList> sellList = au.sellList(user_email);
			int sellcount = sellList.size();
			System.out.println("sellList.size() -> " +sellList.size());
			request.setAttribute("sellcount", sellcount);
			request.setAttribute("sl", sellList);
			
		}catch(Exception e) {
			System.out.println("MysellAction.requestPro -> "+e.getMessage());
		}
		return "mysellList.jsp";
	}

	
}
