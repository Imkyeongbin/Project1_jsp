package service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.Board;
import dao.Product_Info;

/**
 * Servlet implementation class RepostingAction
 */
@WebServlet("/RepostingAction")
public class RepostingAction implements CommandProcess {

	// 변수 선언
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	
	String b_num = "";
	String pd_num = "";
	int bc_num=0;
	int c_num=0;
	String seller_nickname="";
	String seller_email="";
	String pd_image="";
	String pd_name="";
	String pd_condition="";
	String pd_buydate="";
	List<String> files;
	

	private Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("AuctionDAO.getConnection Error -> " + e.getMessage());
		}
		return conn;
	}

	public void executeSQL() throws SQLException {
		// board에서 가져오는 부분
		System.out.println("board 테이블에서 정보 가져오기 실행.");
		
		sql = " select * from board where b_num='" + b_num + "'";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pd_num = rs.getString("pd_num"); // rs.getString(1);
				bc_num = rs.getInt("bc_num");
				c_num = rs.getInt("c_num");
				seller_nickname = rs.getString("seller_nickname");
				seller_email = rs.getString("seller_email");
				pd_image = rs.getString("pd_image");		
			}

			System.out.println("pd_num=" + pd_num);
			System.out.println("bc_num=" + bc_num);
			System.out.println("c_num=" + c_num);
			System.out.println("seller_nickname=" + seller_nickname);
			System.out.println("seller_email=" + seller_email);
			System.out.println("pd_image=" + pd_image);
			

		} catch (Exception e) {
			System.out.println("테이블에서 정보 가져오기 실패 ->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		
		// product_info 에서 가져오는 부분
			
				System.out.println("product_info 테이블에서 정보 가져오기 실행.");
				sql = " select * from product_info where pd_num='" + pd_num + "'";

				try {
					conn = getConnection();
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						pd_name = rs.getString("pd_name"); // rs.getString(1);
						pd_condition = rs.getString("pd_condition");
						pd_buydate = rs.getString("pd_buydate");
					}

					System.out.println("pd_name=" + pd_name);
					System.out.println("pd_condition=" + pd_condition);
					System.out.println("pd_buydate=" + pd_buydate);

				} catch (Exception e) {
					System.out.println("product_info 테이블에서 정보 가져오기실패 ->" + e.getMessage());
				} finally {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				}

	}

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("RepostingAction.java 시작");

		// 유저 계정정보 가져오는 부분
		HttpSession session = request.getSession();
		String user_email = (String) session.getAttribute("user_email");
		if (user_email == null) {
			return "loginForm.jsp";
		}
		
		System.out.println("mysellList에서 b_num받아오기");
		b_num = request.getParameter("b_num");
		System.out.println("b_num="+b_num);
		
		//sql문 실행.
		try {
			executeSQL();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		files = new ArrayList<String>();
		String[] pd_images = pd_image.split(":");
		
		for(String item : pd_images) {
			files.add(item);
	      }

		// request에 넣고 다음으로넘긴다.
		request.setAttribute("b_num", b_num);
		request.setAttribute("pd_num", pd_num);
		request.setAttribute("bc_num", bc_num);
		request.setAttribute("c_num", c_num);
		request.setAttribute("seller_nickname", seller_nickname);
		request.setAttribute("seller_email", seller_email);
		request.setAttribute("pd_image", pd_image);
		request.setAttribute("pd_name", pd_name);
		request.setAttribute("pd_condition", pd_condition);
		request.setAttribute("pd_buydate", pd_buydate);
		request.setAttribute("files", files);

		// ↓ 모든 것이 성공적으로 수행되었을 경우 return 될 page
		return "repostingForm.jsp";
	}

}
