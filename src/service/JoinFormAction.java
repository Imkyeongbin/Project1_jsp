package service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.AuctionDAO;
import dao.Interest;
import dao.User_Info;

@WebServlet("/JoinFormAction")
public class JoinFormAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String realPath = request.getSession().getServletContext().getRealPath("/image/profile_image/");
		File targetDir = new File(realPath);
		
		// 디렉토리가 없을 경우 생성
		if(!targetDir.exists()) {
			targetDir.mkdir();
		}
		int result = 0;
		int maxSize = 5 * 1024 * 1024; // 5M
		
		String filename = "";
		System.out.println("realPath->" + realPath);
		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "utf-8",new DefaultFileRenamePolicy());
		Enumeration en = multi.getFileNames();
		while (en.hasMoreElements()) {
			// input 태그의 속성이 file인 태그의 name 속성값 :파라미터이름m
			String filename1 = (String) en.nextElement();
			// 서버에 저장된 파일 이름
			filename = multi.getFilesystemName(filename1);
			// 전송전 원래의 파일 이름
			String original = multi.getOriginalFileName(filename1);
			// 전송된 파일의 내용 타입
			String type = multi.getContentType(filename1);
			// 전송된 파일속성이 file인 태그의 name 속성값을 이용해 파일객체생성
			File file = multi.getFile(filename1);
			System.out.println("real Path : " + realPath);
			System.out.println("파라메터 이름 : " + filename1);
			System.out.println("실제 파일 이름 : " + original);
			System.out.println("저장된 파일 이름 : " + filename);
			System.out.println("파일 타입 : " + type);
			if (file != null) {
				System.out.println("크기 : " + file.length());
			}
		}
		
		String user_email = multi.getParameter("user_email");
		String user_password = multi.getParameter("user_password");
		String user_name = multi.getParameter("user_name");
		String user_nickname = multi.getParameter("user_nickname");
		String user_phone = multi.getParameter("user_phone");
		String user_gender = multi.getParameter("user_gender");
		/* String user_address = multi.getParameter("user_address"); */
		String user_birth = multi.getParameter("user_birth");
		String user_pin = multi.getParameter("user_pin");		
		int bc_num = Integer.parseInt(multi.getParameter("interest"));
		String user_address = multi.getParameter("address")+"//"+multi.getParameter("zip")+"//" + multi.getParameter("addressInDetail")+"//"+multi.getParameter("referenceItem");
		
		User_Info ui = new User_Info();
		Interest interest = new Interest();
		
		ui.setUser_email(user_email);
		ui.setUser_password(user_password);
		ui.setUser_name(user_name);
		ui.setUser_nickname(user_nickname);
		ui.setUser_phone(user_phone);
		ui.setUser_gender(user_gender);
		ui.setUser_address(user_address);
		
		Date d = Date.valueOf(user_birth);
		ui.setUser_birth(d);
		ui.setUser_pin(user_pin);
	    ui.setUser_image(filename);
	    interest.setBc_num(bc_num);
	    interest.setC_num(0);
	    
		try {
			
			AuctionDAO ad = AuctionDAO.getInstance();
			result = ad.join(ui, interest);
			request.setAttribute("joinState", result);
			System.out.println("joinState => " + result);	
		}catch(Exception e) {
			System.out.println("joinFormAction.requestPro -> " + e.getMessage());
		}
		
		request.setAttribute("user_email",user_email);
		request.setAttribute("user_password",user_password);
		request.setAttribute("user_name",user_name);
		request.setAttribute("user_nickname",user_nickname);
		request.setAttribute("user_phone",user_phone);
		request.setAttribute("user_gender",user_gender);
		request.setAttribute("user_address", user_address);
		request.setAttribute("user_birth",user_birth);
		request.setAttribute("user_pin",user_pin);
		request.setAttribute("fileName",filename);
		
		
		
		return "joinPro.jsp";
	}

}