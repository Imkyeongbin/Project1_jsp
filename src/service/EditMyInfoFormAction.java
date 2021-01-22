package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Interest;
import dao.User_Info;

public class EditMyInfoFormAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		AuctionDAO ad = AuctionDAO.getInstance();
		//로그인 여부 확인
		HttpSession session = request.getSession();
		String user_email = session.getAttribute("user_email").toString();
		if(user_email==null) {
			user_email=null;
		}
		
		// email이 널값이거나 ""이면 loginForm.jsp로 보냄
		if (user_email.equals("") || user_email == null) {
			return "loginForm.jsp";
		}
		
		// user_email로 User_Info를 받아옴.
		
		try {
			User_Info ui = ad.getUserInfo_EditMyInfo(user_email);
			request.setAttribute("user_info", ui);
			
			// 주소/우편번호/상세주소/참고항목
			String[] user_address = ui.getUser_address().split("//");
			request.setAttribute("address", user_address[0]);
			request.setAttribute("zip", user_address[1]);
			request.setAttribute("addressInDetail", user_address[2]);
			request.setAttribute("referenceItem", user_address[3]);
			System.out.println("EditMyInfoFormAction address -> " + user_address[0]);
			System.out.println("EditMyInfoFormAction zip -> " + user_address[1]);
			System.out.println("EditMyInfoFormAction addressInDetail -> " + user_address[2]);
			System.out.println("EditMyInfoFormAction referenceItem -> " + user_address[3]);

		} catch (Exception e) {
			System.out.println("EditMyInfoFormAction.requestPro -> " + e.getMessage());
		}

		// user_email로 Interest를 받아옴.
		try {
			Interest interest = ad.getUserInterest_EditMyInfo(user_email);
			request.setAttribute("interest", interest);
		} catch (Exception e) {
			System.out.println("EditMyInfoFormAction.requestPro -> " + e.getMessage());
		}

		return "editMyInfoForm.jsp";
	}

}
