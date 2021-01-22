package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Board;

public class JjimButtonAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		// 로그인 상태 확인
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		String loginState= request.getParameter("loginState");
		System.out.println("JjimButton.loginState -> " + loginState);
		String jjim_state= request.getParameter("jjim_state");
		System.out.println("JjimButton.jjim_state -> " + jjim_state);
		//게시글 번호 가져오기
		String b_num= request.getParameter("b_num");	//이전 페이지에서 찜버튼을 눌렀을 때 상품번호 가져오기
		System.out.println("JjimButton.b_num -> " + b_num);
		
			
				AuctionDAO ad = AuctionDAO.getInstance();
				int result = ad.jjimButtonPro(user_email, b_num);
				List<Board> board_info = ad.getBoard_Info(b_num);
				int b_like_count = board_info.get(0).getB_like_count();
				
				request.setAttribute("b_like_count",b_like_count);
				request.setAttribute("jjim_b_num",b_num);
				request.setAttribute("result", result);	// result 2:찜성공, 3:찜실패, 4:찜삭제성공, 5:찜삭제 실패
				System.out.println("JjimButton.requestPro -> " + result);
			
		} catch (Exception e) {
			System.out.println("JjimButtonAction.requestPro -> " + e.getMessage());
		}
		return "JjimButtonAction";
	}
}