package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Bid_State;
import dao.Board;
import dao.Jjim_List;
import dao.Product_Info;

public class ProductDetailAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		int loginState = 1;
		if (user_email == "" || user_email == null) {
			loginState = 0;
		}

		request.setAttribute("loginState", loginState);

		String b_num = request.getParameter("b_num");
		System.out.println("선택상품 b_num=> " + b_num);
		try {
			AuctionDAO ad = AuctionDAO.getInstance();
			// bidders count
			ad.update_board_num(b_num);

			List<Bid_State> bidList = ad.getBidList(b_num);
			List<Product_Info> product_info = ad.getProduct_Info(b_num);
			List<Board> board_info = ad.getBoard_Info(b_num);
			List<Jjim_List> jjim_list = ad.jjim_list(user_email, b_num);

			int max_bid_price = ad.max_bid_price(b_num);
			String b_expiration = board_info.get(0).getB_expiration();
			int b_view_count = board_info.get(0).getB_view_count();

			// jjim list 에서 b_num으로 검색해서 0,1로 결과값 가져오기
			int jjim_state = 0;
			for (int i = 0; i < jjim_list.size(); i++) {
				String jjim_b_num = jjim_list.get(i).getB_num();
				if (b_num.equals(jjim_b_num)) {
					jjim_state = 1;
				} else {
					jjim_state = 0;
				}
			}
			;

			// 카테고리 명 가져오기
			String subject = ad.getCg(b_num);
			request.setAttribute("subject", subject);

			// 이미지 가져오기
			String[] images = product_info.get(0).getPd_image().split(":");
			request.setAttribute("images", images);

			request.setAttribute("jjim_state", jjim_state);
			request.setAttribute("b_num", b_num);
			request.setAttribute("b_expiration", b_expiration);
			request.setAttribute("bidList", bidList);
			request.setAttribute("product_info", product_info);
			request.setAttribute("board_info", board_info);
			request.setAttribute("max_bid_price", max_bid_price);
			request.setAttribute("b_view_count", b_view_count);

		} catch (Exception e) {
			System.out.println("ProductDetailAction.requesetPro->" + e.getMessage());
		}
		return "productDetail.jsp";
	}

}
