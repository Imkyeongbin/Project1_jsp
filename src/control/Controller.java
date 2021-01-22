package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.net.httpserver.Authenticator.Result;

import service.CommandProcess;

//@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> commandMap = new HashMap<String, Object>();

	public Controller() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		String props = config.getInitParameter("config");

		Properties pr = new Properties();
		FileInputStream f = null;
		try {
			String configFilePath = config.getServletContext().getRealPath(props);
			f = new FileInputStream(configFilePath);
			pr.load(f);
		} catch (IOException e) {
			throw new ServletException(e);
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException ex) {
				}
		}

		Iterator keyIter = pr.keySet().iterator();

		while (keyIter.hasNext()) {
			String command = (String) keyIter.next(); // /list.do
			String className = pr.getProperty(command); // service.ListAction
			System.out.println(command + " // " + className);

			try {
				Class commandClass = Class.forName(className);// 해당 문자열을 클래스로 만든다.
				System.out.println(commandClass);
				Object commandInstance = commandClass.newInstance();// 해당클래스의 인스턴스생성x, 객체생성 o
				System.out.println(commandClass + " // " + commandInstance);
				commandMap.put(command, commandInstance); // Map객체인 commandMap에 객체 저장
				System.out.println(commandClass + " // " + commandInstance + " => 맵에저장완료");
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		requestPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		requestPro(request, response);
	}

	// 시용자의 요청을 분석해서 해당 작업을 처리
	// 브라우저(집에있는정철이가) -> 컨트롤러(Servlet) -> Model(Dao) -> DTO -> 컨트롤러 ->
	// View(화면을보여줘야해)
	private void requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String view = null; // 보여줄 페이지.
		service.CommandProcess com = null;
		String command = request.getRequestURI();
		try {
			System.out.println("requestPro command =>" + command);
			command = command.substring(request.getContextPath().length());
			com = (CommandProcess) commandMap.get(command);
			System.out.println("requestPro command =>" + command);
			System.out.println("requestPro com => " + com);
			view = com.requestPro(request, response);
			System.out.println("requestPro view => " + view);
		} catch (Throwable e) {
			throw new ServletException(e);
		}
		// ###################### 김예린 구현 ################################
		// Ajax String를 포함하고 있으면
		if (command.contains("ajaxIdCheck")) {
			System.out.println("ajax ajaxIdCheck->" + command); // /ch16/list.do
			String chkMsg1 = (String) request.getAttribute("chkMsg1");
			System.out.println("ajax ajaxIdCheck chkMsg1->" + chkMsg1); // /ch16/list.do
			PrintWriter pw = response.getWriter();
			pw.write(chkMsg1);
			pw.flush();

		} else if (command.contains("ajaxNickNameCheck")) {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			System.out.println("ajax ajaxNickNameCheck->" + command);
			String chkMsg2 = (String) request.getAttribute("chkMsg2");
			PrintWriter pw = response.getWriter();
			pw.write(chkMsg2);
			pw.flush();

			// ###################### 임경빈 구현 ################################

		} else if (command.contains("ajaxNickNameCheck_EditMyInfo")) {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			System.out.println("ajax ajaxNickNameCheck_EditMyInfo->" + command);
			String chkMsg3 = (String) request.getAttribute("chkMsg3");
			PrintWriter pw = response.getWriter();
			pw.write(chkMsg3);
			pw.flush();
			
			// ###################### 김경민 구현 ################################
		 } else if (command.contains("jjimButton")){  
	          request.setCharacterEncoding("utf-8"); 
	          response.setCharacterEncoding("utf-8");
	         String b_like_count = String.valueOf(request.getAttribute("b_like_count"));
	         System.out.println("b_like_count1111111111 -> " + b_like_count);
	         PrintWriter pw = response.getWriter();
	         pw.write(b_like_count);
	         pw.flush();
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
	}
}
