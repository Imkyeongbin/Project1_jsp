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
import dao.Auction_Money;
import dao.Bid;
import dao.Bid_State;
import dao.Board;
import dao.Product_Info;

public class BidProAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int result = 0;
		int max_bid_price = 0;
		// 로그인 세션에서 user_email 가져오기
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");

		// 입력한 bid_price 가져오기
		int bid_price_check = Integer.parseInt(request.getParameter("bid_price"));
		System.out.println("입력한 가격=>" + bid_price_check);
		// 현재시간 구하기
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = formatter.format(cal.getTime());
		String bid_timestamp = today;

		// 상품정보 가져오기

		String b_num = request.getParameter("b_num");
		System.out.println("BidFormAction 에 b_num=> " + b_num);
		/* String pd_num = "201228401001"; */

		try {

			AuctionDAO ad = AuctionDAO.getInstance();

			List<Auction_Money> amoney = ad.getBalance(user_email);
			List<Product_Info> product_info = ad.getProduct_Info(b_num);
			int pd_price = product_info.get(0).getPd_price();
			// 현재 입찰 최고가 가져오기
			max_bid_price = ad.max_bid_price(b_num);
			System.out.println("BidFormAction max_bid_price=>" + max_bid_price);
			int balance = amoney.get(0).getBalance();
			String seller_email = product_info.get(0).getSeller_email();
			// 최고가보다 입력금액이 큰 경우 입찰참여가능
			// 잔액에 입력한 입찰금액이 있는지 확인
			if (!user_email.equals(seller_email)) {
				System.out.println("user_email"+user_email);
				System.out.println("seller_email"+seller_email);
				if (bid_price_check <= balance) {
					if (bid_price_check >= pd_price) {
						if (bid_price_check > max_bid_price) {
							int bid_price = bid_price_check;

							// 직전 최고가입찰자에게 환불
							if (max_bid_price != 0) {
								ad.pay_back_money(max_bid_price, b_num);
							}
							;

							Bid_State bs = new Bid_State();
							Bid bid = new Bid();
							Auction_Money am = new Auction_Money();

							String pd_num = product_info.get(0).getPd_num();
							int bc_num = product_info.get(0).getBc_num();
							int c_num = product_info.get(0).getC_num();

							bid.setB_num(b_num);
							bs.setPd_num(pd_num);
							bs.setBc_num(bc_num);
							bs.setC_num(c_num);
							bs.setBid_price(bid_price);
							bs.setBid_timestamp(bid_timestamp);
							bs.setUser_email(user_email);
							bs.setSeller_email(seller_email);
							am.setBalance(balance);
							am.setWithdraw(bid_price);

							int bid_num = ad.part_bid(bs, bid, b_num);

							System.out.println("1111bid_num check" + bid_num);
							ad.keep_money(am, bs, bid_num);
							result = 1;
							System.out.println("BidProAction result=>" + result);
						} else {
							result = 0; // 현재 최고가보다 낮음
						}
					} else {
						result = 2; // 상품 시작가격보다 적음
					}
				} else {
					result = 3; // 옥션머니 잔액 모자름
				}
			} else {
				result = 4; //판매자가 자기가 올린 상품에 입찰시도
			}

		} catch (Exception e) {
			System.out.println("BidFormAction.requesetPro->" + e.getMessage());
		}
		System.out.println("if 입찰금액 비교 result=>" + result);
		request.setAttribute("result", result);
		request.setAttribute("b_num", b_num);
		return "bidPro.jsp";
	}
}
