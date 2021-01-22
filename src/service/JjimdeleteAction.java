package service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;


public class JjimdeleteAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		String b_num = request.getParameter("b_num");
		System.out.println("b_num"+ b_num);
		int result = 0;
		try {
			
			 System.out.println("b_num"+ b_num);
			 AuctionDAO au   = AuctionDAO.getInstance();
			 result = au.delete_jjim(b_num, user_email);
			
			 request.setAttribute("result",result);
		     request.setAttribute("b_num",b_num);
		     System.out.println("pd_num"+ b_num);
		    
		}catch(Exception e) {
			System.out.println( "JjimdeleteAction requestPro=>"+e.getMessage());
		}
		
		return "jjimdeletePro.jsp";
	}

}