package service;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class DeleteAction
 */
@WebServlet("/DeleteAction")
public class DeleteAction implements CommandProcess {
	String deleteFile="";
	int delResult = 0;

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("DeleteAction.java 시작");

		// 유저 계정정보 가져오는 부분
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		if (user_email == null) {
			return "loginForm.jsp";
		}

		// 변수 선언 .. 파일이름과 경로 읽어오기.
		System.out.println("변수선언부분 시작");
		try {

			String fileList = request.getParameter("fileList");
			String realPath2 = request.getParameter("realPath2");
			
			System.out.println("fileList: " + fileList);
			System.out.println("realPath2: " + realPath2);
			
			System.out.println();
			
			String[] pd_images = fileList.split(":");
			
			for(String item : pd_images) {
				deleteFile = realPath2 + item;
				System.out.println("deleteFile: " + deleteFile);
				
				try {
					delResult = upFileDelete(deleteFile);
					System.out.println("DeleteAction delResult"+delResult);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("delResult : " + delResult ); 
				
				
		      }
			
			

			

		} catch (Exception e) {
			System.out.println("변수선언 실패" + e.getMessage());
		}
		
		
		

		return "main.do";

	}
	
	// 파일삭제
			private int upFileDelete(String deleteFileName)   throws Exception {
					int   result = 0;
					System.out.println("파일삭제 부분 시작");
					try {
						  File file = new File(deleteFileName); 
						  if( file.exists() ){ 
							  if(file.delete()){ 
							  		System.out.println("파일삭제 성공"); 
							  		result = 1;
							  	}
							    else{ 
							    	System.out.println("파일삭제 실패"); 
							    	result = 0;
							   	} 
							  }
							  else{ 
								  System.out.println("파일이 존재하지 않습니다."); 
								  result = -1;
							  }

					} catch (Exception e) {
						System.out.println("파일삭제 실패" + e.getMessage());
					}
					  return result;
			  }

}
