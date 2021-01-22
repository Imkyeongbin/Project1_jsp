package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Board;
import dao.Categories;
import dao.Interest;
import dao.Jjim_List;
import dao.Product_Info;
import dao.TotalListManager;

public class CategoriesAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		Interest interest = new Interest();
		int loginState = 0;

		if (user_email == null) { // 로그인되지 않은 상태라면
			user_email = null;
			loginState = 0;
		} else { // 로그인 된 상태라면
			interest.setUser_email(user_email);
			loginState = 1;
			request.setAttribute("user_email", user_email);
		}
		request.setAttribute("loginState", loginState);

		// 이전 페이지에서 '더보기' 정보 인가 받음.
		int bc_num = Integer.parseInt(request.getParameter("bc_num"));
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		int sort_state = Integer.parseInt(request.getParameter("sort_state"));
		request.setAttribute("bc_num", bc_num);
		request.setAttribute("c_num", c_num);
		//categories.jsp :: 정럴콤보박스용  |  sort_state=> 1 : 인기순, 2 : 경매마감순, 3: 조회순
		request.setAttribute("sort_state", sort_state);
		
		// categories.jsp 진입 전 : 데이터베이스에서 특정 카테고리 데이터, 상품정보 데이터, 판매게시글 데이터를 가져오자.
		TotalListManager tl = new TotalListManager();
		tl.setCg_list(new ArrayList<Categories>());
		tl.setPd_list(new ArrayList<Product_Info>());
		tl.setBd_list(new ArrayList<Board>());
		tl.setLk_list_100(new ArrayList<Board>());
		tl.setLk_list_200(new ArrayList<Board>());
		tl.setLk_list_300(new ArrayList<Board>());
		tl.setLk_list_400(new ArrayList<Board>());
		tl.setLk_list_500(new ArrayList<Board>());
		tl.setLk_list_600(new ArrayList<Board>());
		tl.setExp_list_100(new ArrayList<Board>());
		tl.setExp_list_200(new ArrayList<Board>());
		tl.setExp_list_300(new ArrayList<Board>());
		tl.setExp_list_400(new ArrayList<Board>());
		tl.setExp_list_500(new ArrayList<Board>());
		tl.setExp_list_600(new ArrayList<Board>());
		tl.setOvc_list_100(new ArrayList<Board>());
		tl.setOvc_list_200(new ArrayList<Board>());
		tl.setOvc_list_300(new ArrayList<Board>());
		tl.setOvc_list_400(new ArrayList<Board>());
		tl.setOvc_list_500(new ArrayList<Board>());
		tl.setOvc_list_600(new ArrayList<Board>());

		try {
			// DB연결 시도
			AuctionDAO ad = AuctionDAO.getInstance();
			ad.update_BoardState();
			
			/////경민 구현////////////////
			List<Jjim_List> jjim_list2 = ad.jjim_list2(user_email);
			request.setAttribute("jjim_list2", jjim_list2);
			/////////////////////////////////////
			
			ad.mainInit(tl, interest, loginState); // 메인화면에 보여줄 데이터들을 DTO에 넣어주자.

			request.setAttribute("total_list", tl);
			request.setAttribute("categories_list", tl.getCg_list()); // 카테고리정보 모두 가져옴
			request.setAttribute("product_info_list", tl.getPd_list()); // 상품정보 모두 가져옴
			request.setAttribute("board_list", tl.getBd_list());
			// 인기 순
			request.setAttribute("lkc_bd_list100", tl.getLk_list_100());
			request.setAttribute("lkc_bd_list200", tl.getLk_list_200());
			request.setAttribute("lkc_bd_list300", tl.getLk_list_300());
			request.setAttribute("lkc_bd_list400", tl.getLk_list_400());
			request.setAttribute("lkc_bd_list500", tl.getLk_list_500());
			request.setAttribute("lkc_bd_list600", tl.getLk_list_600());
			// 경매마감 시간 순
			request.setAttribute("exp_bd_list100", tl.getExp_list_100());
			request.setAttribute("exp_bd_list200", tl.getExp_list_200());
			request.setAttribute("exp_bd_list300", tl.getExp_list_300());
			request.setAttribute("exp_bd_list400", tl.getExp_list_400());
			request.setAttribute("exp_bd_list500", tl.getExp_list_500());
			request.setAttribute("exp_bd_list600", tl.getExp_list_600());
			// 조회 순
			request.setAttribute("ovc_bd_list100", tl.getOvc_list_100());
			request.setAttribute("ovc_bd_list200", tl.getOvc_list_200());
			request.setAttribute("ovc_bd_list300", tl.getOvc_list_300());
			request.setAttribute("ovc_bd_list400", tl.getOvc_list_400());
			request.setAttribute("ovc_bd_list500", tl.getOvc_list_500());
			request.setAttribute("ovc_bd_list600", tl.getOvc_list_600());

		} catch (Exception e) {
			System.out.println("MainAction.requestPro -> " + e.getMessage());
		}

		return "categories.jsp";
	}

}