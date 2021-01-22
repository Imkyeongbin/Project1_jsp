package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AuctionDAO;

public class NewPasswordAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  
		System.out.println("NewPasswordAction Start..");
	     int  result = 0;
	     String user_password = request.getParameter("user_password");
	     String user_email    = request.getParameter("user_email");
	     
		try {
			System.out.println("NewPasswordAction user_password->"+user_password);
		   AuctionDAO ad = AuctionDAO.getInstance();
		   result = ad.new_Password(user_password, user_email);   
			System.out.println("NewPasswordAction result->"+result);
			System.out.println("NewPasswordAction user_email->"+user_email);
			  
			
		} catch (Exception e) {
			System.out.println("NewPasswordAction.requestPro->" +e.getMessage());
			
		}
		
		request.setAttribute("user_email", user_email);
		request.setAttribute("result", result);
		
		return "loginForm.jsp";
	}

}
