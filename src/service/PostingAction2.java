package service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Board;
import dao.Product_Info;

@WebServlet("/PostingAction2")
public class PostingAction2 implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("PostingAction2.java 시작");

		// 유저 계정정보 가져오는 부분
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		if (user_email == null) {
			return "loginForm.jsp";
		}

		// 변수 선언
		System.out.println("변수선언부분 시작");
		try {

		
			int bc_num = Integer.parseInt(request.getParameter("bc_num"));
			int c_num = Integer.parseInt(request.getParameter("c_num"));
			int pd_unit = Integer.parseInt(request.getParameter("pd_unit"));
			int pd_price = Integer.parseInt(request.getParameter("pd_price"));
			
			String pd_name = request.getParameter("pd_name");
			String pd_condition = request.getParameter("pd_condition");
			String pd_buydate = request.getParameter("pd_buydate");

			String b_title = request.getParameter("b_title");
			String b_expiration = request.getParameter("b_expiration");
			String b_contents = request.getParameter("b_contents");
			String fileList = request.getParameter("pd_image");

			System.out.println("bc_num: " + bc_num);
			System.out.println("c_num: " + c_num);
			System.out.println("pd_unit: " + pd_unit);
			System.out.println("pd_price: " + pd_price);
			
			System.out.println("pd_name: " + pd_name);
			System.out.println("pd_condition: " + pd_condition);
			System.out.println("pd_buydate: " + pd_buydate);
			
			System.out.println("b_title: " + b_title);
			System.out.println("b_expiration: " + b_expiration);
			System.out.println("b_contents: " + b_contents);
			System.out.println("fileList : " + fileList);
			System.out.println();

			// Service 객체 생성.(서비스가 없고 DAO에서 직접 처리한다면 DAO 객체 생성)
			// MemberService service = MemberService.getInstance();
			AuctionDAO pd = AuctionDAO.getInstance();
			Product_Info product = new Product_Info();
			Board board = new Board();
			// 데이터들을 담을 그릇인 DTO(혹은 Bean) 객체를 생성 후, 데이터들을 set해준다.

			product.setBc_num(bc_num);
			product.setC_num(c_num);
			product.setPd_name(pd_name);
			product.setPd_unit(pd_unit);
			product.setPd_price(pd_price);
			;
			product.setPd_condition(pd_condition);
			product.setPd_buydate(pd_buydate);
			// db에 넣어줄 파일 이름(Array List)
			product.setPd_image(fileList);
			product.setSeller_email(user_email);
			board.setB_title(b_title);
			board.setB_expiration(b_expiration);
			board.setB_contents(b_contents);
			board.setBc_num(bc_num);
			board.setC_num(c_num);

			try {
				// dao에서 만들어놓은 product 수행 메서드 사용. set으로 담아줬던 DTO를 넘겨서 product메서드 수행.
				System.out.println("SQL문 실행페이지로 넘기기 성공.");
				pd.posting(product, board);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.out.println("변수선언 실패" + e.getMessage());
		}

		return "main.do";

	}

}
