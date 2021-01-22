package service;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.AuctionDAO;
import dao.User_Info;

public class EditMyInfoProAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		User_Info ui = new User_Info();
		//로그인 여부 확인
		
		
		try {
			// 업로드
			int maxSize = 5 * 1024 * 1024; // 5M
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			String realPath = rootPath + "image\\profile_image";
			String savePath = realPath.replace('\\', '/');
			File targetDir = new File(savePath);
			
			// 디렉토리가 없을 경우 생성
			if(!targetDir.exists()) {
				targetDir.mkdir();
			}
			
			System.out.println("realPath->" + realPath);
			MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "utf-8",	new DefaultFileRenamePolicy());
			String original_image = multi.getParameter("original_image");
			Enumeration en = multi.getFileNames();
			String user_image = "";
			
			String user_email = multi.getParameter("user_email");
			if(user_email==null||user_email.equals("")) {
				return "loginForm.jsp";
			}
			String user_password = multi.getParameter("user_password");
			String user_nickname = multi.getParameter("user_nickname");
			String user_phone = multi.getParameter("user_phone");
			//System.out.println(multi.getParameter("check_bc_num"));
			int bc_num = Integer.parseInt(multi.getParameter("check_bc_num"));
			String user_address = multi.getParameter("address")+"//"+multi.getParameter("zip")+"//" + multi.getParameter("addressInDetail")+"//"+multi.getParameter("referenceItem");
			String user_pin = multi.getParameter("user_pin");
			
			boolean noInterest = false;	//관심항목이 없는 채로 가입한 경우)
			if(multi.getParameter("interest_original").equals("0")) {
				noInterest = true;
			};
//			System.out.println("EditMyInfoProAction.noInterest ->"+noInterest);

			while (en.hasMoreElements()) {
				// input 태그의 속성이 file인 태그의 name 속성값 :파라미터이름m
				String filename1 = (String) en.nextElement();
				// 서버에 저장된 파일 이름
				user_image = multi.getFilesystemName(filename1);
				// 전송전 원래의 파일 이름
				String original = multi.getOriginalFileName(filename1);
				// 전송된 파일의 내용 타입
				String type = multi.getContentType(filename1);
				// 전송된 파일속성이 file인 태그의 name 속성값을 이용해 파일객체생성
				File file = multi.getFile(filename1);
				System.out.println("real Path : " + realPath);
				System.out.println("파라메터 이름 : " + filename1);
				System.out.println("실제 파일 이름 : " + original);
				System.out.println("저장된 파일 이름 : " + user_image);
				System.out.println("파일 타입 : " + type);
				if (file != null) {
					System.out.println("크기 : " + file.length());
				}
			}
			
			ui.setUser_email(user_email);
			ui.setUser_image(user_image);
			ui.setUser_password(user_password);
			ui.setUser_nickname(user_nickname);
			ui.setUser_phone(user_phone);
			// bc_num
			ui.setUser_address(user_address);
			ui.setUser_pin(user_pin);

			AuctionDAO ad = AuctionDAO.getInstance();
			// ui와 bc_num으로 User_Info와 Interest를 업데이트함.
			int update_result = ad.editMyInfo_Update(ui, bc_num, noInterest);
			int delete_image_result = this.deleteImage(original_image, request);
//			System.out.println("UpdateProAction result->"+result);
			request.setAttribute("update_result", update_result);
			request.setAttribute("delete_result",delete_image_result);
		} catch (Exception e) {
			System.out.println("EditMyInfoProAction requestPro ->" + e.getMessage());
			e.printStackTrace();
		}


		return "editMyInfoPro.jsp";
	}
	
	private int deleteImage(String original_image, HttpServletRequest request) {
		int result = 0;
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String realPath = rootPath + "image\\profile_image\\" + original_image;
		String deletePath = realPath.replace('\\', '/');
		System.out.println("deletePath -> "+deletePath);
		File f = new File(deletePath);
		if(f.exists()){
			f.delete();
			result = 1;
			System.out.println("파일 삭제됨");
		}else{
			result = 0;
			System.out.println("파일 없음");
		}
		
		return result;	
	}

}
