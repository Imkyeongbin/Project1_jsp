package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class RepostingAction2
 */
@WebServlet("/RepostingAction2")
public class RepostingAction2 implements CommandProcess {
	
	
	

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("RepostingAction2.java 시작");
		
		// 유저 계정정보 가져오는 부분
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		if (user_email == null) {
			return "loginForm.jsp";
		}
		
	
		List<String> files = new ArrayList<String>();
		
		// 변수 선언
		System.out.println("변수선언부분 시작");
		try {
			//원래있던값들 재활용.
			String b_num = request.getParameter("b_num");
			String pd_num = request.getParameter("pd_num");
			int bc_num = Integer.parseInt(request.getParameter("bc_num"));
			int c_num = Integer.parseInt(request.getParameter("c_num"));
			String seller_nickname = request.getParameter("seller_nickname");
			String seller_email = request.getParameter("seller_email");
			String uploadUri = "/image/ct_image/" + bc_num + "/" + c_num;
			String context = request.getContextPath();
			
			String pd_image = request.getParameter("pd_image");
			String[] pd_images = pd_image.split(":");
			
			for(String item : pd_images) {
				files.add(item);
		      }
			
			String pd_name = request.getParameter("pd_name");
			String pd_condition = request.getParameter("pd_condition");
			String pd_buydate = request.getParameter("pd_buydate");
			
			//새로받아오는 값들.
			int pd_unit = Integer.parseInt(request.getParameter("pd_unit"));
			int pd_price = Integer.parseInt(request.getParameter("pd_price"));
			
			String b_title = request.getParameter("b_title");
			String b_expiration = request.getParameter("b_expiration");
			
			b_expiration = b_expiration.replace("-", "/");
			b_expiration = b_expiration.replace("T", " ");
			b_expiration = b_expiration + ":00";
			String b_contents = request.getParameter("b_contents");
			
			System.out.println("b_num: " + b_num);
			System.out.println("pd_num: " + bc_num);
			
			System.out.println("bc_num: " + bc_num);
			System.out.println("c_num: " + c_num);
			
			System.out.println("seller_nickname: " + seller_nickname);
			System.out.println("seller_email: " + seller_email);
			
			System.out.println("pd_unit: " + pd_unit);
			System.out.println("pd_price: " + pd_price);
			
			System.out.println("pd_name: " + pd_name);
			System.out.println("pd_condition: " + pd_condition);
			System.out.println("pd_buydate: " + pd_buydate);
			
			System.out.println("b_title: " + b_title);
			System.out.println("b_expiration: " + b_expiration);
			System.out.println("b_contents: " + b_contents);
			System.out.println("fileList : " + pd_image);//String
			System.out.println("files : " + files);//ArrayList
			System.out.println();
			
			// request에 넣고 다음으로넘긴다.
			request.setAttribute("pd_unit", pd_unit);
			request.setAttribute("pd_price", pd_price);
			request.setAttribute("b_title", b_title);
			request.setAttribute("b_expiration", b_expiration);
			request.setAttribute("b_contents", b_contents);
			
			request.setAttribute("b_num", b_num);
			request.setAttribute("pd_num", pd_num);
			request.setAttribute("bc_num", bc_num);
			request.setAttribute("c_num", c_num);
			request.setAttribute("seller_nickname", seller_nickname);
			request.setAttribute("seller_email", seller_email);
			request.setAttribute("files", files);
			request.setAttribute("pd_name", pd_name);
			request.setAttribute("pd_image", pd_image);
			request.setAttribute("pd_condition", pd_condition);
			request.setAttribute("pd_buydate", pd_buydate);
			request.setAttribute("uploadUri", context+uploadUri+"\\");


		} catch (Exception e) {
			System.out.println("변수선언 실패" + e.getMessage());
		}
		
		
		

		return "reposting-response.jsp";

	}

}
