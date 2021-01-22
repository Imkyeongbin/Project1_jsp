package service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.Board;
import dao.Product_Info;

@WebServlet("/PostingAction")
public class PostingAction implements CommandProcess {

	// 변수 선언
	String imgName = "";
	String renamedFile = "";

	int bc_num = 0;
	int c_num = 0;
	String pd_name = "";
	int pd_unit = 0;
	int pd_price = 0;
	String pd_condition = "";
	String pd_buydate = "";

	String b_title = "";
	String b_expiration = "";
	String b_contents = "";
	int count;
	List<String> files;
	String fileList ;
	String user_email = "";
	String uploadUri = "";
	String realPath = "";
	
	String realPath2 = "";
	String uploadUri2 = "";
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("PostingAction.java 시작");
		
		//파일 중복방지를 위해 메소드 실행 할 때마다 count랑 files 초기화
		count = 0;
		files = new ArrayList<String>();
		fileList = "";
		
		// 유저 계정정보 가져오는 부분
		HttpSession session = request.getSession();
		user_email = (String) session.getAttribute("user_email");
		if (user_email == null) {
			return "loginForm.jsp";
		}
		
		// Multipart/form-data 여부 확인
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {
			// 2. 메모리나 파일로 업로드 파일 보관하는 FileItem의 Factory 설정
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// 3. 업로드 요청을 처리하는 ServletFileUpload 생성
			ServletFileUpload upload = new ServletFileUpload(factory);

			// 4. 업로드 요청 파싱해서 FileItem 목록 구함

			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Iterator<FileItem> iter = items.iterator();

			while (iter.hasNext()) {
				FileItem item = iter.next();

				// 파일인지 여부 확인 : isFormFile() -> type=file 이외의 폼 데이터 인지 확인
				if (item.isFormField()) { // 텍스트 입력인 경우
					String name = item.getFieldName(); // 태그 name
					String value = item.getString("utf-8");
					System.out.println("일반 폼 필드 : " + name + "-" + value);

					// form문에서 입력받은 값 가져오기

					if (name.equalsIgnoreCase("bc_num"))
						bc_num = Integer.parseInt((String) item.getString(("utf-8")));
					if (name.equalsIgnoreCase("c_num"))
						c_num = Integer.parseInt((String) item.getString(("utf-8")));
					if (name.equalsIgnoreCase("pd_name"))
						pd_name = (String) item.getString(("utf-8"));
					if (name.equalsIgnoreCase("pd_unit"))
						pd_unit = Integer.parseInt((String) item.getString(("utf-8")));
					if (name.equalsIgnoreCase("pd_price"))
						pd_price = Integer.parseInt((String) item.getString(("utf-8")));
					if (name.equalsIgnoreCase("pd_condition"))
						pd_condition = (String) item.getString(("utf-8"));
					if (name.equalsIgnoreCase("pd_buydate")) {
						String a = (String) item.getString(("utf-8"));
						// replace([기존문자],[바꿀문자])
						a = a.replace("-", "/");
						pd_buydate = a;
					}
					if (name.equalsIgnoreCase("b_title"))
						b_title = (String) item.getString(("utf-8"));
					if (name.equalsIgnoreCase("b_expiration")) {
						String a = (String) item.getString(("utf-8"));
						// replace([기존문자],[바꿀문자])
						a = a.replace("-", "/");
						a = a.replace("T", " ");
						a = a + ":00";
						b_expiration = a;
					}
					if (name.equalsIgnoreCase("b_contents"))
						b_contents = (String) item.getString(("utf-8"));

					System.out.println("bc_num: " + bc_num);
					System.out.println("c_num: " + c_num);
					System.out.println("pd_name: " + pd_name);
					System.out.println("pd_unit: " + pd_unit);
					System.out.println("pd_price: " + pd_price);
					System.out.println("pd_condition: " + pd_condition);
					System.out.println("pd_buydate: " + pd_buydate);
					System.out.println("b_title: " + b_title);
					System.out.println("b_expiration: " + b_expiration);
					System.out.println("b_contents: " + b_contents);
					System.out.println();

				} else {
					
					// 웹서비스에서사용되는 저장 경로
					uploadUri = "/image/ct_image/" + bc_num + "/" + c_num;
					uploadUri2 = "/image/ct_image/" + bc_num + "/" + c_num+"/"; //DeleteAction 용
					System.out.println("웹서비스에서사용되는 저장 경로");
					System.out.println(uploadUri);

					// 물리적인 경로
					System.out.println("물리적인 경로");
					realPath = request.getSession().getServletContext().getRealPath(uploadUri);
					realPath2 = request.getSession().getServletContext().getRealPath(uploadUri2); //DeleteAction 용
					System.out.println(uploadUri + "의 물리적 경로 : " + realPath);
					System.out.println();
					
					File targetDir = new File(realPath2);
					if(!targetDir.exists()) {
						targetDir.mkdir();
					}
					count++;
					System.out.printf("%d번째 파일\n", count);

					String name = item.getFieldName();
					String fileName = item.getName(); // 파일이름
					String contentType = item.getContentType(); // 확장자.
					boolean isInMemory = item.isInMemory(); // 뭔지모름.
					long sizeInBytes = item.getSize(); // 파일 사이즈
					//FileRename 클래스의 객체 선언.
					FileRename fr = new FileRename();
					renamedFile = fr.Rename(fileName,realPath2);
					files.add(renamedFile); //ArrayList로 넘겨서 response에서 for each로 표시할 때 쓰는 용.
					if(fileList=="") fileList = renamedFile; // fileList는 String형식으로 파일이름사이에 콜론(:)으로 이어서 DB에 저장하는 용.
					else 			fileList = fileList+":"+renamedFile;

					System.out.println("form input name : " + name);
					System.out.println("fileName : " + fileName);
					System.out.println("contentType : " + contentType);
					System.out.println("isInMemory : " + isInMemory);
					System.out.println("sizeInBytes : " + sizeInBytes);
					System.out.println("renamedFile : " + renamedFile);
					System.out.println();

					// 저장하고자 하는 파일의 이름
					imgName = renamedFile;
					System.out.println("저장하고자 하는 파일의 이름");
					System.out.println(imgName);

					// 데이터 저장 File(위치, 파일)
					// 만들어놓은 웹컨텐트 /file/photo/____<이곳에 저장하기 위해 경로를 지정한 것(물리적으로)
					
					try {
						item.write(new File(realPath, imgName));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
//		원래 ArrayLis 받아서 출력했을 때 [파일1,파일2] 형식으로 되어있는것을 치환해서 쓰려고 했으나
//	 	파일이름에 원래 콜론이 들어갈 수 있으므로 스트링변수에 하나씩 추가하는 방식으로 바꿈.
//		fileList = String.format("%s", files);
//		fileList = fileList.replace("[", "");
//		fileList = fileList.replace("]", "");
//		fileList = fileList.replace(" ", "");
//		System.out.println("파일 리스트 : " + fileList);

		// Service 객체 생성.(서비스가 없고 DAO에서 직접 처리한다면 DAO 객체 생성)
		// MemberService service = MemberService.getInstance();

		// 데이터들을 담을 그릇인 DTO(혹은 Bean) 객체를 생성 후, 데이터들을 set해준다.
		Product_Info product = new Product_Info();
		Board board = new Board();

		product.setBc_num(bc_num);
		product.setC_num(c_num);
		product.setPd_name(pd_name);
		product.setPd_unit(pd_unit);
		product.setPd_price(pd_price);
		;
		product.setPd_condition(pd_condition);
		product.setPd_buydate(pd_buydate);
		// db에 넣어줄 파일 이름.
		product.setPd_image(fileList);
		product.setSeller_email(user_email);
		
		board.setB_title(b_title);
		board.setB_expiration(b_expiration);
		board.setB_contents(b_contents);

		// 만약 return할 페이지에 방금 전송한 데이터들을 출력하고 싶다면 DTO를 속성에 담아준다.

		// 세션에 넣고 다음으로넘긴다.
		String context = request.getContextPath();
		System.out.println(context);
		request.setAttribute("files", files);
		request.setAttribute("uploadUri", context+uploadUri+"\\");
		request.setAttribute("realPath2", realPath2); // deleteAction용.
		request.setAttribute("fileList", fileList); // deleteAction용.
		request.setAttribute("board", board);
		request.setAttribute("product", product);
		
//		request.setAttribute("fileName", "\\" + renamedFile);
		
		// ↓ 모든 것이 성공적으로 수행되었을 경우 return 될 page
		return "posting-response.jsp";
	}

}
