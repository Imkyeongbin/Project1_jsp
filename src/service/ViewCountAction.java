package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AuctionDAO;

import dao.Board;


public class ViewCountAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String b_num = request.getParameter("b_num");
		try {
			AuctionDAO ad = AuctionDAO.getInstance();
			List<Board> board_info = ad.getBoard_Info(b_num);
			
			int b_view_count = board_info.get(0).getB_view_count()+1;
			ad.update_view_count(b_view_count, b_num);
			
			request.setAttribute("b_num", b_num);
			request.setAttribute("b_view_count", b_view_count);
			
		
			
		}catch(Exception e) {
			System.out.println("ProductDetailAction.requesetPro->"+e.getMessage());
		}
		return "productDetail.do";
	}

}
