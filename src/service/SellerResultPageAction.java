package service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Bid;
import dao.Bid_Done;
import dao.Board;
import dao.Win_User_Info;


public class SellerResultPageAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuctionDAO ad = AuctionDAO.getInstance();
		String b_num = request.getParameter("b_num");
		
		request.setAttribute("b_num", b_num);
//		System.out.println("SellerResultPageAction.requestPro -> getParameter('b_num')" + b_num);
		Board board = new Board();
		try {
			ad.update_BoardState();
			ad.done_bid();
			board = ad.getBCI(b_num);	//b_num으로 bc_num, c_num, pd_image를 받아옴.
			request.setAttribute("board", board);
			String[] images = board.getPd_image().split(":");
			request.setAttribute("images", images);					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("SellerResultPageAction.requestPro1 => "+ e.getMessage());
		}
		String b_title = "";
		try {
			b_title = ad.getB_title_resultPage(b_num);
			request.setAttribute("b_title", b_title);
		}catch(Exception e) {
			System.out.println("SellerResultPageAction.requestPro2 => "+ e.getMessage());
		}
		
		Bid_Done bd = new Bid_Done();
		String b_expiration = "";
		String b_expirationAfterWeek = "";
		int confirm_status = 0;
		
		String win_user_email = "";
		Win_User_Info wui = new Win_User_Info();
		
		String user_email = "";
		//session을 받아오고 user_email을 가져옴.
		try {
			HttpSession session = request.getSession();
			user_email = session.getAttribute("user_email").toString();
		}catch(NullPointerException e) {
			System.out.println("SellerResultPageAction session NullPointerException ");
		}
		//email이 널값이거나 ""이면 loginForm.jsp로 보냄
		if(user_email.equals("")||user_email == null) {
			return "loginForm.jsp";
		}
		
		try {
			System.out.println("SellerResultPageAction b_expiration Before..");
			// 입찰 만료 시간 가져오기
			b_expiration = ad.getExpirationInBidResult(b_num);
			
			request.setAttribute("b_expiration", b_expiration);
		}catch(Exception e) {
			System.out.println("SellerResultPageAction.requestPro3 => "+ e.getMessage());
		}
		
		try {
			System.out.println("SellerResultPageAction 입찰 만료 시간 7일 후 검사 후 가져오기 Before..");
			// 입찰 만료 시간 7일 후 검사 후 가져오기
			SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date b_expirationDate = fm.parse(b_expiration);
			Calendar cal = Calendar.getInstance();
			cal.setTime(b_expirationDate);
	        cal.add(Calendar.DATE, 7);
	        b_expirationAfterWeek = fm.format(cal.getTime());

	        Date today = new Date();
	        int compareTime = cal.getTime().compareTo(today);
	        if(compareTime >0) {
	        	confirm_status = ad.getConfirm_status(b_num);
	        }else {
	        	confirm_status = ad.updateConfirm_status(b_num);
	        }
	        request.setAttribute("confirm_status", confirm_status);
	        
//	        System.out.println("SellerResultPageAction.requestPro3 b_ex_week=>"+cal.getTime());
//	        System.out.println("SellerResultPageAction.requestPro3 오늘=>"+cal.getTime());
			
			request.setAttribute("b_expirationAfterWeek", b_expirationAfterWeek);
		}catch(Exception e) {
			System.out.println("SellerResultPageAction.requestPro4 => "+ e.getMessage());
		}
		
		
		try {
			System.out.println("SellerResultPageAction 입찰 결과 Bid_Done 가져오기 Before..");
			// 입찰 결과 Bid_Done 가져오기
			bd = ad.getBid_DoneSellerResult(b_num);
			win_user_email = bd.getWin_user_email();
			request.setAttribute("win_user_email", bd.getWin_user_email());
			request.setAttribute("max_bid_price", bd.getMax_bid_price());
			request.setAttribute("seller_email", bd.getSeller_email());
		}catch(Exception e){
			System.out.println("SellerResultPageAction.requestPro5 => "+ e.getMessage());
		}
		
		try {
			// bid 테이블 만들기
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null || pageNum.equals("")) { pageNum = "1"; }
			int currentPage = Integer.parseInt(pageNum);
			int pageSize = 10, blockSize = 10;
			int startRow = (currentPage -1)*pageSize +1;
			int endRow = startRow + pageSize - 1;
			int totCnt = ad.getTotalBidCntInResultPage(b_num);
//			System.out.println("SellerResultPageAction totCnt=>"+totCnt);
			int startNum = totCnt - startRow + 1;
//			System.out.println("SellerResultPageAction startNum=>"+startNum);
			
			System.out.println("SellerResultPageAction list Before..");
			List<Bid> list = ad.bidListInResultPage(b_num, startRow, endRow);
			int pageCnt = (int)Math.ceil((double)totCnt/pageSize);
			int startPage = (int)(currentPage-1)/blockSize*blockSize +1;
			int endPage = startPage + blockSize -1;
			if(endPage > pageCnt) endPage = pageCnt; 
			
			request.setAttribute("totCnt", totCnt);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("startNum", startNum);
			request.setAttribute("list", list);
			request.setAttribute("blockSize", blockSize);
			request.setAttribute("pageCnt", pageCnt);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
//			System.out.println("-------------------------------------------");	// /SellerResultPageAction.do
//			System.out.println("startNum-->" + startNum);		// /SellerResultPageAction.do
//			System.out.println("totCnt-->" + totCnt);			// /SellerResultPageAction.do
//			System.out.println("currentPage-->"+ currentPage);	// /SellerResultPageAction.do
//			System.out.println("blockSize-->"+blockSize);		// /SellerResultPageAction.do
//			System.out.println("pageSize-->"+pageSize);			// /SellerResultPageAction.do
//			System.out.println("pageCnt-->"+pageCnt);			// /SellerResultPageAction.do
//			System.out.println("startPage-->"+startPage);		// /SellerResultPageAction.do
//			System.out.println("endPage-->"+endPage);			// /SellerResultPageAction.do
		} catch (Exception e) {
			System.out.println("SellerResultPageAction.requestPro6 => "+e.getMessage());
		}
		
		try {
			System.out.println("SellerResultPageAction 구매자 정보(Win_User_Info) win_user_email로 가져오기 Before..");
			// 구매자 정보(Win_User_Info) win_user_email로 가져오기
			boolean authority = bd.getSeller_email().equals(user_email);
			wui = ad.consumerInfoInResultPage(win_user_email, authority); 
//			System.out.println("wui.getConsumer_email()->"+wui.getConsumer_email());
//			System.out.println("wui.getConsumer_phone()->"+wui.getConsumer_phone());
			request.setAttribute("win_user_info", wui );
			
			String[] consumer_address = wui.getConsumer_address().split("//");
			System.out.println("SellerResultPageAction.consumer_address[0]=>"+consumer_address[0]);
			request.setAttribute("address", consumer_address[0]);
			request.setAttribute("zip", consumer_address[1]);
			request.setAttribute("addressInDetail", consumer_address[2]);
			request.setAttribute("referenceItem", consumer_address[3]);
//			System.out.println("SellerResultPageAction address -> " + consumer_address[0]);
//			System.out.println("SellerResultPageAction zip -> " + consumer_address[1]);
//			System.out.println("SellerResultPageAction addressInDetail -> " + consumer_address[2]);
//			System.out.println("SellerResultPageAction referenceItem -> " + consumer_address[3]);
			
		}catch(Exception e){
			System.out.println("SellerResultPageAction.requestPro7 => "+ e.getMessage());
		}
		
		
		
		
		return "sellerResultPage.jsp";
	}

}
