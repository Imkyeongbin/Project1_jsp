package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
//import java.util.Date;
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

public class MainAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 현재 로그인 상태 체크 : loginState:0 비로그인 상태, loginState:1 로그인 상태
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		Interest interest = new Interest();
		int loginState = 0;

		if (user_email == null) { // 로그인되지 않은 상태라면
			interest = null;
			user_email = null;
			loginState = 0;
		} else { // 로그인 된 상태라면
			if(user_email.equals("admin@admin.com")) {	//관리자계정
				int adminState = 1;
				session.setAttribute("adminState",adminState);
			}
			interest.setUser_email(user_email);
			System.out.println("interest user_email -> " + user_email);
			loginState = 1;
			request.setAttribute("user_email", user_email);
		}

		session.setAttribute("loginState", loginState);

		// main.jsp 진입 전 : 데이터베이스에서 카테고리 데이터, 상품정보 데이터, 판매게시글 데이터를 가져오자.
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
			
			//김경민 구현 ///////////////////////////////////////////////////
			ad.done_bid();
			List<Jjim_List> jjim_list2 = ad.jjim_list2(user_email);
			request.setAttribute("jjim_list2", jjim_list2);
			
			////////////////////////////////////////////////////////////////
			ad.mainInit(tl, interest, loginState); // 메인화면에 보여줄 데이터들을 DTO에 넣어주자.
			
			if (loginState == 1) {
				List<Board> interest_board = new ArrayList<Board>();
				for (int i = 0; i < tl.getBd_list().size(); i++) {
					if (tl.getBd_list().get(i).getBc_num() == interest.getBc_num()) {
						interest_board.add(tl.getBd_list().get(i));
					}
				}
				interest_board.sort(new LikeCount_Comparator());
				request.setAttribute("interest_board", interest_board); // 관심 카테고리 아이템 리스트 변수화
				request.setAttribute("interest", interest); // 접속 중인 계정의 관심카테고리 DTO를 가져옴
			}

			request.setAttribute("total_list", tl);
			request.setAttribute("categories_list", tl.getCg_list()); // 카테고리정보 모두 가져옴
			request.setAttribute("board_list", tl.getBd_list()); // 판매게시글 모두 가져옴
			request.setAttribute("product_info_list", tl.getPd_list()); // 상품정보 모두 가져옴

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
		return "main.jsp";
	}
}

/*
 * class ViewCount_Comparator implements Comparator<Board> {
 * 
 * @Override public int compare(Board bd1, Board bd2) { // 조회 수 오름차순 정렬 if
 * (bd1.getB_view_count() > bd2.getB_view_count()) return -1; if
 * (bd1.getB_view_count() < bd2.getB_view_count()) return 1; return 0; } }
 */
class LikeCount_Comparator implements Comparator<Board> {
	@Override
	public int compare(Board bd1, Board bd2) { // 조회 수 오름차순 정렬
		if (bd1.getB_like_count() > bd2.getB_like_count())
			return -1;
		if (bd1.getB_like_count() < bd2.getB_like_count())
			return 1;
		return 0;
	}
}

/*
 * class EXP_Comparator implements Comparator<Board> {
 * 
 * @Override public int compare(Board bd1, Board bd2) { // 조회 수 오름차순 정렬
 * 
 * @SuppressWarnings("deprecation") Date d1 = new Date(bd1.getB_expiration());
 * 
 * @SuppressWarnings("deprecation") Date d2 = new Date(bd2.getB_expiration());
 * 
 * if (d1.compareTo(d2) == 1) return 1; if (d1.compareTo(d2) == -1) return -1;
 * return 0; }
 * 
 * class REG_Comparator implements Comparator<Board> {
 * 
 * @Override public int compare(Board bd1, Board bd2) { // 조회 수 오름차순 정렬
 * 
 * @SuppressWarnings("deprecation") Date d1 = new Date(bd1.getB_regdate());
 * 
 * @SuppressWarnings("deprecation") Date d2 = new Date(bd2.getB_regdate());
 * 
 * if (d1.compareTo(d2) == 1) return -1; if (d1.compareTo(d2) == -1) return 1;
 * return 0; } } }
 */