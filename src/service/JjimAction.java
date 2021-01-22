package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AuctionDAO;
import dao.Jjim;

public class JjimAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		if(user_email==null) {
			user_email=null;
			return "loginForm.jsp";
		}
		System.out.println("JjimAction user_email->" + user_email);
		AuctionDAO au = AuctionDAO.getInstance();

		try {
			List<Jjim> jjim1 = au.jjim(user_email);
			
			///경민 추가////////////////////////
			int jjimcount = jjim1.size();
			System.out.println("jjim1.size() => "+jjim1.size());
			request.setAttribute("jjimcount", jjimcount);
			////////////////////////////////////////////////
			
			request.setAttribute("jjim1", jjim1);
			//System.out.println("list=="+list);
		} catch (Exception e) {
			System.out.println("Jjimaction ->" + e.getMessage());
		}

		return "jjimList.jsp";
	}

}
