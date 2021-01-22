package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AuctionDAO;

public class FindIdAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * request.getParameter("user_name"); request.getParameter("user_birth");
		 * request.getParameter("user_phone");
		 */
		     String user_email = null;
		     String user_name = request.getParameter("user_name");
		     String user_birth = request.getParameter("user_birth");
		     String user_phone = request.getParameter("user_phone");
		     
		   try {
			   AuctionDAO ad = AuctionDAO.getInstance();
			user_email = ad.find_IdCheck(user_name, user_birth, user_phone);  //user_email 값 
			  
		  
		   }catch (Exception e) {
			   System.out.println("FindIdAction.requestPro->" +e.getMessage());
		   }
		   
		   request.setAttribute("user_email", user_email);
		
		return "foundIdForm.jsp";
	}

}
