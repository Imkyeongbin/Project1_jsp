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
import dao.Interest;
import dao.Jjim_List;

public class SearchAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		Interest interest = new Interest();
		int loginState = 0;
		if (user_email == null) { // 로그인되지 않은 상태라면
			interest = null;
			user_email = null;
			loginState = 0;
		} else { // 로그인 된 상태라면
			interest.setUser_email(user_email);
			System.out.println("interest user_email -> " + user_email);
			loginState = 1;
			request.setAttribute("user_email", user_email);
		}
		request.setAttribute("loginState", loginState);

		
		String search_word = request.getParameter("search_word");
		int result = 0;	// result : 0 검색결과x / result : 1 검색결과o
		int list_size = 0;
		List<Board> board_list = new ArrayList<Board>();
		try {
			AuctionDAO ad = AuctionDAO.getInstance();
			ad.update_BoardState();
			//김경민 구현 ///////////////////////////////////////////////////
			ad.done_bid();
			List<Jjim_List> jjim_list2 = ad.jjim_list2(user_email);
			request.setAttribute("jjim_list2", jjim_list2);
			////////////////////////////////////////////////////////////////
			if(search_word!=null)
				result = ad.searchEngine(board_list,search_word);
			if(result == 0) {
				list_size = 0;
			}
			list_size = board_list.size();
		}catch(Exception e) {
			System.out.println("SearchAction.requestPro -> " + e.getMessage());
		}
		request.setAttribute("list_size", list_size);
		request.setAttribute("search_word", search_word);
		request.setAttribute("result", result);
		request.setAttribute("board_list", board_list);
		return "searchResult.jsp";
	}

}
