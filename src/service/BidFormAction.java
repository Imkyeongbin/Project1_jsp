package service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Bid_State;
import dao.Board;
import dao.Product_Info;

public class BidFormAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String b_num = request.getParameter("b_num");
		int max_bid_price = 0;
		try {
			AuctionDAO ad = AuctionDAO.getInstance();

			List<Product_Info> product_info = ad.getProduct_Info(b_num);
			int pd_unit = product_info.get(0).getPd_unit();
			int pd_price = product_info.get(0).getPd_price();
			// 현재 입찰 최고가 가져오기
			max_bid_price = ad.max_bid_price(b_num);
			int view_price = max_bid_price>pd_price?max_bid_price:pd_price;
			request.setAttribute("b_num", b_num);
			request.setAttribute("pd_unit", pd_unit);
			request.setAttribute("view_price", view_price);


		} catch (Exception e) {
			System.out.println("BidFormAction.requesetPro->"+e.getMessage());
		}
		
		
	
		return "bidForm.jsp";
	}
}
