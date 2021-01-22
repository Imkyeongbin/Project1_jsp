package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AuctionDAO {
	private static AuctionDAO instance = null;

	private AuctionDAO() {
	}

	public static AuctionDAO getInstance() {
		if (instance == null) {
			instance = new AuctionDAO();
		}
		return instance;
	}

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

	// ############################# 김예린 구현 ###################################
	public int loginCheck(String user_email, String user_password) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT user_email, user_password, user_active FROM USER_INFO WHERE user_email = ? and user_password = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			pstmt.setString(2, user_password);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				int user_active = rs.getInt("user_active");
				if (rs.getString("user_email").equals("admin@admin.com")) {
					result = 5;
				} else {
					if (user_active == -1) {
						result = 3;
					} else if (user_active == 1) {
						result = 1;
					} else if (user_active == -2) {
						result = 4;
					}
				}
				System.out.println("AuctionDAO.loginCheck : user_email -> " + rs.getString(1));

			}

		} catch (Exception e) {
			System.out.println("AuctionDAO.loginCheck -> " + e.getMessage());
		}
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

		// result = 1 : 로그인 성공!, result = 0 : 로그인 실패;
		return result;
	}

	public String find_IdCheck(String user_name, String user_birth, String user_phone) throws SQLException {
		String user_email = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT user_email FROM USER_INFO WHERE user_name = ? AND user_birth = ? AND user_phone = ? ";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_name);
			pstmt.setString(2, user_birth);
			pstmt.setString(3, user_phone);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user_email = rs.getString("user_email");

			}

			System.out.println("AuctionDAO.findIdCheck : user_email -> " + rs.getString(1));

		} catch (Exception e) {
			System.out.println("AuctionDAO.findIdCheck -> " + e.getMessage());
		}
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

		return user_email;
	}

	public int new_Password(String user_password, String user_email) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "UPDATE user_info SET user_password = ? WHERE user_email = ?";

		System.out.println("AuctionDAO new_Password user_password->" + user_password);
		System.out.println("AuctionDAO new_Password user_email->" + user_email);
		System.out.println("AuctionDAO new_Password sql->" + sql);
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_password);
			pstmt.setString(2, user_email);
			result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("UPDATE user_info data -> UPDATE Success!!");
			} else {
				System.out.println("UPDATE user_info data -> UPDATE Fail T.T");
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.new_Password Exception-> " + e.getMessage());
		}

		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

		System.out.println("UPDATE user_info Return Before");
		return result;
	}

	public int join(User_Info ui, Interest interest) throws SQLException {
		int result = 0; // result insert가 안됬을 때 : 데이터베이스에 용량이 없어서 추가가 안됨 / 이미 회원가입이된 email이 insert될려고 할
						// 경우.
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql1 = "INSERT INTO user_info values(?,?,?,?,?,?,?,?,?,?,sysdate,1)";
		String sql2 = "INSERT INTO interest values(?,?,?,(select subject from categories where bc_num = ? and c_num = 0))";
		String sql3 = "INSERT INTO auction_money (user_email,time) values(? , TO_CHAR(SYSDATE,'YYYY/MM/DD HH24:MI:SS'))";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, ui.getUser_email());
			pstmt.setString(2, ui.getUser_password());
			pstmt.setString(3, ui.getUser_nickname());
			pstmt.setString(4, ui.getUser_name());
			pstmt.setString(5, ui.getUser_phone());
			pstmt.setString(6, ui.getUser_address());
			pstmt.setString(7, ui.getUser_gender());
			pstmt.setDate(8, (java.sql.Date) ui.getUser_birth());
			pstmt.setString(9, ui.getUser_image());
			pstmt.setString(10, ui.getUser_pin());

			result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("Insert user_info data -> join Success!!");
			} else {
				System.out.println("Insert user_info data -> join Fail;;");
			}

			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, ui.getUser_email());
			pstmt.setInt(2, interest.getBc_num());
			pstmt.setInt(3, interest.getC_num());
			pstmt.setInt(4, interest.getBc_num());
			result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("Insert interest data -> join Success!!");
			} else {
				System.out.println("Insert interest data -> join Fail;;");
			}

			pstmt.close();
			pstmt = conn.prepareStatement(sql3);
			pstmt.setString(1, ui.getUser_email());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			conn.rollback();
			System.out.println("AuctionDAO.join -> " + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int check(String user_email) throws SQLException {
	      int result = 0;
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = "Select * FROM user_info WHERE user_email = ?";
	      System.out.println("AuctionDAO user_email->" + user_email);
	      System.out.println("AuctionDAO sql->" + sql);
	      try {
	         conn = getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, user_email);
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	            result = 1;

	         }
	      } catch (Exception e) {
	         System.out.println("AjaxIdCheck -> " + e.getMessage());

	      } finally {
	         if (rs != null)
	            rs.close();
	         if (pstmt != null)
	            pstmt.close();
	         if (conn != null)
	            conn.close();

	      }
	      return result;

	   }

	   public int select(String user_niackname) throws SQLException {
	      int result = 0;
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = "Select * FROM user_info WHERE user_nickname = ?";
	      System.out.println("AuctionDAO user_niackname->" + user_niackname);
	      System.out.println("AuctionDAO sql->" + sql);
	      try {
	         conn = getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, user_niackname);
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	            result = 1;

	         }
	      } catch (Exception e) {
	         System.out.println("AjaxNickNameCheckAction -> " + e.getMessage());

	      } finally {
	         if (rs != null)
	            rs.close();
	         if (pstmt != null)
	            pstmt.close();
	         if (conn != null)
	            conn.close();

	      }
	      return result;

	   }

	// ################### 임경빈 구현 ###########################

	public Board getBCI(String b_num) throws SQLException {
		Board board = new Board();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bc_num, c_num, pd_image FROM board WHERE b_num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int bc_num = rs.getInt(1);
				int c_num = rs.getInt(2);
				String pd_image = rs.getString(3);

				board.setBc_num(bc_num);
				board.setC_num(c_num);
				board.setPd_image(pd_image);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getExpirationInBidResult -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return board;
	}

	public String getB_title_resultPage(String b_num) throws SQLException {
		String result = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT b_title FROM board WHERE b_num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getExpirationInBidResult -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public String getExpirationInBidResult(String b_num) throws SQLException {
		String result = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT B_EXPIRATION FROM board WHERE b_num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();
//			System.out.println("rs까진 됨");
//			System.out.println("getExpirationInBidResult rs.next->"+rs.next());
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getExpirationInBidResult -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public Bid_Done getBid_DoneSellerResult(String b_num) throws SQLException {
		Bid_Done result = new Bid_Done();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM bid_done WHERE b_num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();
//			System.out.println("rs까진 됨");
			if (rs.next()) {
				String win_user_email = rs.getString(1);
				int max_bid_price = rs.getInt(2);
				String seller_email = rs.getString(3);
//				System.out.println("AuctionDAO.getBid_DoneSellerResult max_bid_price->"+max_bid_price);
				result.setWin_user_email(win_user_email);
				result.setMax_bid_price(max_bid_price);
				result.setSeller_email(seller_email);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getBid_DoneSellerResult -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int getConfirm_status(String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT confirm_status FROM bid_done WHERE b_num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getConfirm_status -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int updateConfirm_status(String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE bid_done SET confirm_status = 1 WHERE b_num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("AuctionDAO.getConfirm_status -> " + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int getTotalBidCntInResultPage(String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM bid WHERE b_num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
//			System.out.println("AuctionDAO.getTotalBidCnt"+result );
		} catch (Exception e) {
			System.out.println("AuctionDAO.getTotalBidCnt -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				pstmt.close();
		}

		return result;
	}

	public List<Bid> bidListInResultPage(String b_num, int startRow, int endRow) throws SQLException {
		List<Bid> list = new ArrayList<Bid>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("AuctionDAO bidListInResultPage Before..");
		String sql = "SELECT rn,bid_num,b_num,SUBSTR(user_email,1,4)||'*****@*****',bid_timestamp,bid_price "
				+ " FROM (SELECT rownum rn,a.* FROM (SELECT * FROM bid WHERE b_num = ? ORDER BY bid_num DESC) a )"
				+ " WHERE rn BETWEEN ? AND ? ORDER BY bid_num";
		System.out.println("AuctionDAO bidListInResultPage sql-->" + sql);
		System.out.println("AuctionDAO bidListInResultPage 1 b_num-->" + b_num);
		System.out.println("AuctionDAO bidListInResultPage 1 startRow-->" + startRow);
		System.out.println("AuctionDAO bidListInResultPage 1 endRow-->" + endRow);
		try {
			System.out.println("AuctionDAO bidListInResultPage getConnection Before..");
			conn = getConnection();
			System.out.println("AuctionDAO bidListInResultPage getConnection After..");
			pstmt = conn.prepareStatement(sql);
			System.out.println("AuctionDAO bidListInResultPage prepareStatement After..");
			pstmt.setString(1, b_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			System.out.println("AuctionDAO bidListInResultPage executeQuery Before..");
			rs = pstmt.executeQuery();
//			System.out.println(rs.next());
			System.out.println("AuctionDAO bidListInResultPage 2 b_num-->" + b_num);
			System.out.println("AuctionDAO bidListInResultPage 2 startRow-->" + startRow);
			System.out.println("AuctionDAO bidListInResultPage 2 endRow-->" + endRow);
			if (rs.next()) {
				do {
					Bid bid = new Bid();
					String user_email = rs.getString(4);
					String bid_timestamp = rs.getString(5);
					int bid_price = rs.getInt(6);

					bid.setB_num(b_num);
					bid.setUser_email(user_email);
					bid.setBid_timestamp(bid_timestamp);
					bid.setBid_price(bid_price);
					list.add(bid);
				} while (rs.next());
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO bidListInResultPage Exception->" + e.getMessage());
		} finally {
			System.out.println("AuctionDAO bidListInResultPage finally... ");
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		System.out.println("AuctionDAO bidListInResultPage Return Before... ");
		return list;
	}

	public Win_User_Info consumerInfoInResultPage(String win_user_email, boolean authority) throws SQLException {
		Win_User_Info result = new Win_User_Info();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT user_address, user_email, user_phone, user_image FROM user_info WHERE user_email = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, win_user_email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String consumer_address = rs.getString(1);
				String consumer_email = rs.getString(2);
				String consumer_phone = rs.getString(3);
				String consumer_image = rs.getString(4);
				if (authority) {
					result.setConsumer_address(consumer_address);
					result.setConsumer_email(consumer_email);
					result.setConsumer_phone(consumer_phone);
					result.setConsumer_image(consumer_image);
				} else {
					result.setConsumer_address("0");
					result.setConsumer_email(consumer_email);
					result.setConsumer_phone("0");
					result.setConsumer_image("img01.png");
				}

			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.consumerInfoInResultPage->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public User_Info getUserInfo_EditMyInfo(String user_email) throws SQLException {
		User_Info result = new User_Info();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM user_info WHERE user_email = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String user_image = rs.getString("user_image");
				String user_nickname = rs.getString("user_nickname");
				String user_name = rs.getString("user_name");
				String user_phone = rs.getString("user_phone");
				Date user_birth = rs.getDate("user_birth");
				String user_gender = rs.getString("user_gender");
				String user_address = rs.getString("user_address");
				String user_pin = rs.getString("user_pin");

				result.setUser_email(user_email); // 받아온 변수
				result.setUser_image(user_image);
				result.setUser_nickname(user_nickname);
				result.setUser_name(user_name);
				result.setUser_phone(user_phone);
				result.setUser_birth(user_birth);
				result.setUser_gender(user_gender);
				result.setUser_address(user_address);
				result.setUser_pin(user_pin);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getUserInfo_EditMyInfo -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public Interest getUserInterest_EditMyInfo(String user_email) throws SQLException {
		Interest result = new Interest();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM interest WHERE user_email = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// user_email
				int bc_num = rs.getInt("bc_num");
				int c_num = rs.getInt("c_num");
				String subject = rs.getString("subject");

				result.setUser_email(user_email); // 받아온 변수
				result.setBc_num(bc_num);
				result.setC_num(c_num);
				result.setSubject(subject);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getUserInterest_EditMyInfo" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int editMyInfo_Update(User_Info ui, int bc_num, boolean noInterest) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql1 = "SELECT * from user_info where user_nickname=? and user_email!=?";
		String sql2a = "UPDATE user_info SET user_password=?,user_nickname=?,user_phone=?,user_address=?,user_pin=? WHERE user_email = ?";
		String sql2b = "UPDATE user_info SET user_password=?,user_nickname=?,user_phone=?,user_address=?,user_pin=?,user_image=? WHERE user_email = ?";
		String sql3a = "Insert into interest (user_email, bc_num, c_num, subject) VALUES (?, ?, ?, (select subject from categories where bc_num = ? and c_num = ?) )";
		String sql3b = "UPDATE interest SET bc_num=?, c_num=?, subject=(select subject from categories where bc_num = ? and c_num = ?) WHERE user_email = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, ui.getUser_nickname());
			pstmt.setString(2, ui.getUser_email());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

				return 2;
			}
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();

			if (ui.getUser_image() == null) {
				pstmt = conn.prepareStatement(sql2a);
				pstmt.setString(1, ui.getUser_password());
				pstmt.setString(2, ui.getUser_nickname());
				pstmt.setString(3, ui.getUser_phone());
				pstmt.setString(4, ui.getUser_address());
				pstmt.setString(5, ui.getUser_pin());
				pstmt.setString(6, ui.getUser_email());
			} else {
				pstmt = conn.prepareStatement(sql2b);
				pstmt.setString(1, ui.getUser_password());
				pstmt.setString(2, ui.getUser_nickname());
				pstmt.setString(3, ui.getUser_phone());
				pstmt.setString(4, ui.getUser_address());
				pstmt.setString(5, ui.getUser_pin());
				pstmt.setString(6, ui.getUser_image());
				pstmt.setString(7, ui.getUser_email());
			}

			result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("AuctionDAO.editMyInfo_Update User_info Update... -> Success!!");
			} else {
				System.out.println("AuctionDAO.editMyInfo_Update User_info Update... -> Fail;;");
			}
			if (pstmt != null)
				pstmt.close();
			if (noInterest) {
				pstmt = conn.prepareStatement(sql3a);
				// Insert into interest VALUES(?, ?, ?, (select subject from categories where
				// bc_num = ? and c_num = ?))

				pstmt.setString(1, ui.getUser_email());
				pstmt.setInt(2, bc_num);
				pstmt.setInt(3, 0); // c_num = 0
				pstmt.setInt(4, bc_num);
				pstmt.setInt(5, 0); // c_num = 0
				result = pstmt.executeUpdate();

				if (result > 0) {
					System.out.println("AuctionDAO.editMyInfo_Update interest Insert... -> Success!!");
				} else {
					System.out.println("AuctionDAO.editMyInfo_Update interest Insert... -> Fail;;");
				}

			} else {
				pstmt = conn.prepareStatement(sql3b);
				// "UPDATE interest SET bc_num=?, c_num=?, subject=(select subject from
				// categories where bc_num = ? and c_num = ?) WHERE user_email = ?"
				pstmt.setInt(1, bc_num);
				pstmt.setInt(2, 0);
				pstmt.setInt(3, bc_num);
				pstmt.setInt(4, 0);
				pstmt.setString(5, ui.getUser_email());
				result = pstmt.executeUpdate();

				if (result > 0) {
					System.out.println("AuctionDAO.editMyInfo_Update interest Update... -> Success!!");
				} else {
					System.out.println("AuctionDAO.editMyInfo_Update interest Update... -> Fail;;");
				}

			}

		} catch (Exception e) {
			System.out.println("AuctionDAO.editMyInfo_Update->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public Seller_User_Info sellerUserInfo(String seller_email, boolean authority) throws SQLException {
		Seller_User_Info result = new Seller_User_Info();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT user_email, user_phone, user_image FROM user_info WHERE user_email = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seller_email);
			rs = pstmt.executeQuery(); //
			if (rs.next()) {
				String seller_phone = rs.getString(2);
				result.setSeller_email(seller_email);
				if (authority) {
					result.setSeller_phone(seller_phone);
				} else {
					result.setSeller_phone("0");
				}
				String seller_image = rs.getString(3);
				result.setSeller_image(seller_image);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.sellerUserInfo ->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int withdrawl(String user_email, String passwd) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE user_info SET user_active=? WHERE user_email = ? and user_password = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, -1);
			pstmt.setString(2, user_email);
			pstmt.setString(3, passwd);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("AuctionDAO.withdrawl->" + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int select_EditMyInfo(String user_nickname, String user_email) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select count(*) FROM user_info WHERE user_nickname = ? and user_email != ?";
		// System.out.println("AuctionDAO user_nickname->"+user_nickname);
		if (user_nickname.equals("") || user_nickname.startsWith(" ")) {
			result = -1;
		} else {
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user_nickname);
				pstmt.setString(2, user_email);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result = rs.getInt(1);

				}
			} catch (Exception e) {
				System.out.println("AuctionDAO.select_EditMyInfo -> " + e.getMessage());

			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}
		}
		return result;

	}

	public int getMaxBid_price(String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select max(bid_price) FROM bid WHERE b_num = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);

			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getMaxBid_price -> " + e.getMessage());

		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int getBid_num_maxBid_price(String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxBid_price = getMaxBid_price(b_num);
		String sql = "SELECT bid_num FROM bid WHERE bid_price = ? AND b_num = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, maxBid_price);
			pstmt.setString(2, b_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);

			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.getMaxBid_price -> " + e.getMessage());

		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public int update_confirmation(String b_num, int bid_num_maxBid_price, String seller_email, int max_bid_price)
			throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql1 = "SELECT confirm_status FROM bid_done WHERE b_num = ?";
		String sql2 = "SELECT balance FROM auction_money WHERE time = (SELECT MAX(time) FROM auction_money WHERE user_email= ?) and user_email = ?";
		String sql3 = "INSERT INTO auction_money VALUES(?," + "                                 	?,"
				+ "                                		?," + "                                		?,"
				+ "                                		0,"
				+ "                                		TO_CHAR(SYSDATE,'YYYY/MM/DD HH24:MI:SS') )";

		int balance = 0;
//		System.out.println("AuctionDAO update_confirmation->"+max_bid_price); 
		try {
			conn = getConnection();

			pstmt = conn.prepareStatement(sql1); // bid_num이 이미 있는지 확인(이미 있으면 구매 확정이 이미 이뤄졌다는 것)
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 1) {

					result = -1; // 이미 구매 확정되었습니다.

				} else {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();

					pstmt = conn.prepareStatement(sql2); // 기존의 잔액을 확인하고 없으면 0으로 간주.
					pstmt.setString(1, seller_email);
					pstmt.setString(2, seller_email);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						balance = rs.getInt(1);
					}
					balance += max_bid_price; // 잔액에 입찰가를 더함
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();

					pstmt = conn.prepareStatement(sql3); // 총 결과를 INSERT하여 구매 확정 완료
					pstmt.setString(1, seller_email);
					pstmt.setInt(2, bid_num_maxBid_price);
					pstmt.setInt(3, balance);
					pstmt.setInt(4, max_bid_price);
					result = pstmt.executeUpdate();

				}
			}

		} catch (Exception e) {
			System.out.println("AuctionDAO.update_confirmation -> " + e.getMessage());

		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();

		}
		return result;
	}

	// ###################### 김경민 구현 ################################
	 public String getCg(String b_num) throws SQLException {
	      String subject =null;
	      String subject1 =null;
	      String subject2 =null;
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;

	      String sql = "SELECT c.subject"
	            + " FROM   CATEGORIES c, BOARD b" + " WHERE  b.b_num = ?" + " AND b.c_num = c.c_num";
	      String sql1 = "SELECT c.subject"
	            + " FROM   CATEGORIES c, BOARD b" + " WHERE  b.b_num = ?" + " AND b.bc_num = c.bc_num AND c.c_num = 0";
	      
	      try {
	         conn = getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, b_num);
	         rs = pstmt.executeQuery();

	         if (rs.next()) { 
	            subject2 = rs.getString("subject");
	         }
	         pstmt.close();
	         rs.close();
	         pstmt = conn.prepareStatement(sql1);
	         pstmt.setString(1, b_num);
	         rs = pstmt.executeQuery();

	         if (rs.next()) { 
	            subject1 = rs.getString("subject");
	         }
	         
	         subject = subject1+'>'+subject2;
	               
	      } catch (Exception e) {
	         System.out.println(e.getMessage());
	      } finally {
	         if (rs != null)
	            rs.close();
	         if (pstmt != null)
	            pstmt.close();
	         if (conn != null)
	            conn.close();
	      }
	      return subject;

	   }

	public int adminRestoreMember(String pick_user) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql1 = "UPDATE USER_INFO SET user_active = 1 WHERE user_email = ?";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, pick_user);
			result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("adminRestoreMember success");
			} else {
				System.out.println("adminRestoreMember fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.adminRestoreMember->" + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int adminStopMember(String pick_user) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql1 = "UPDATE USER_INFO SET user_active = -2 WHERE user_email = ?";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, pick_user);
			result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("adminStopMember success");
			} else {
				System.out.println("adminStopMember fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.adminStopMember->" + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int adminStopMoney(String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql1 = "UPDATE BID_DONE SET confirm_status = -1 WHERE b_num = ?";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, b_num);
			result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("adminStopMoney success");
			} else {
				System.out.println("adminStopMoney fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.adminStopMoney->" + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int adminStopAuction(String b_num) throws SQLException {
		int result = 0;
		int result1 = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql1 = "UPDATE BOARD SET b_expiration = TO_CHAR(SYSDATE,'YYYY/MM/DD hh24:mi:ss'), b_state = -1 WHERE b_num = ?";
		String sql2 = "UPDATE AUCTION SET b_expiration = TO_CHAR(SYSDATE,'YYYY/MM/DD hh24:mi:ss') WHERE b_num = ?";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, b_num);
			result = pstmt.executeUpdate();

			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, b_num);
			result1 = pstmt.executeUpdate();

			if (result == 1 && result1 == 1) {
				System.out.println("adminStopAuction success");
			} else {
				System.out.println("adminStopAuction fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.adminStopAuction->" + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public List<User_Info> admin_member() throws SQLException {
		List<User_Info> admin_member = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM USER_INFO";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) { // 데이터를 여러개 돌려서
				User_Info ui = new User_Info();

				ui.setUser_email(rs.getString("user_email"));
				ui.setUser_nickname(rs.getString("user_nickname"));
				ui.setUser_name(rs.getString("user_name"));
				ui.setUser_birth(rs.getDate("user_birth"));
				ui.setUser_phone(rs.getString("user_phone"));
				ui.setUser_regdate(rs.getDate("user_regdate"));
				ui.setUser_active(rs.getInt("user_active"));

				admin_member.add(ui);
			}
			return admin_member;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return admin_member;

	}

	public List<Admin_Board> admin_board() throws SQLException {
		List<Admin_Board> admin_board = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT b.b_state, b.seller_email, b.pd_num, b.b_num, b.b_expiration, a.now_maxbid, "
				+ " a.now_win_user_email, bd.win_user_email, bd.max_bid_price, bd.confirm_status"
				+ " FROM BOARD b, AUCTION a, BID_DONE bd  WHERE b.b_num = a.b_num AND b.b_num = bd.b_num(+)";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) { // 데이터를 여러개 돌려서
				Admin_Board ab = new Admin_Board();

				ab.setB_state(rs.getInt("b_state"));
				ab.setSeller_email(rs.getString("seller_email"));
				ab.setB_expiration(rs.getString("b_expiration"));
				ab.setPd_num(rs.getString("pd_num"));
				ab.setB_num(rs.getString("b_num"));
				ab.setNow_maxbid(rs.getString("now_maxbid"));
				ab.setNow_win_user_email(rs.getString("now_win_user_email"));
				ab.setWin_user_email(rs.getString("win_user_email"));
				ab.setMax_bid_price(rs.getInt("max_bid_price"));
				ab.setConfirm_status(rs.getInt("confirm_status"));

				admin_board.add(ab);
			}
			return admin_board;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return admin_board;

	}

	public int part_bid(Bid_State bs, Bid bid, String b_num) throws SQLException {
		int result = 0;
		int bid_num = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql1 = "INSERT INTO BID (bid_num, b_num,user_email, bid_timestamp, bid_price)values((BID_NUM_SEQ.NEXTVAL),?,?,?,?)";
		String sql2 = "SELECT bid_num FROM BID WHERE bid_timestamp = ?";
		String sql3 = "INSERT INTO BID_STATE (bid_num, pd_num, bc_num, c_num, bid_price, bid_timestamp, user_email, seller_email)values(?,?,?,?,?,?,?,?)";
		String sql4 = "UPDATE AUCTION SET now_maxbid = ?, now_win_user_email = ? WHERE b_num = ? ";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql1);
			// 여기도 private때문에 bs.getUser_ 로 가져와야함

			pstmt.setString(1, bid.getB_num());
			pstmt.setString(2, bs.getUser_email());
			pstmt.setString(3, bs.getBid_timestamp());
			pstmt.setInt(4, bs.getBid_price());
			result = pstmt.executeUpdate();
			pstmt.close();

			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, bs.getBid_timestamp());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				bid_num = rs.getInt("bid_num");

				pstmt.close();

				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, bid_num);
				pstmt.setString(2, bs.getPd_num());
				pstmt.setInt(3, bs.getBc_num());
				pstmt.setInt(4, bs.getC_num());
				pstmt.setInt(5, bs.getBid_price());
				pstmt.setString(6, bs.getBid_timestamp());
				pstmt.setString(7, bs.getUser_email());
				pstmt.setString(8, bs.getSeller_email());
				result = pstmt.executeUpdate();

				pstmt.close();

				pstmt = conn.prepareStatement(sql4);
				pstmt.setInt(1, bs.getBid_price());
				pstmt.setString(2, bs.getUser_email());
				pstmt.setString(3, bid.getB_num());

				result = pstmt.executeUpdate();
			}

			rs.close();
			pstmt.close();
			conn.close();
			if (result == 1) {
				System.out.println("bid success");
			} else {
				System.out.println("bid fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.part_bid->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return bid_num;
	}

	public int done_bid() throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;

		String sql = "CREATE OR REPLACE VIEW finish_bid " + "as "
				+ "SELECT b.b_num, b.b_expiration, bid.bid_price, bid.user_email, b.seller_email "
				+ "FROM BID bid, BOARD b " + "WHERE bid.b_num(+) = b.b_num ";
		String sql1 = "CREATE OR REPLACE VIEW finish_bid1 " + "as "
				+ "SELECT fb.b_num, fb.seller_email, fb.bid_price, fb.user_email " + "FROM finish_bid fb "
				+ "WHERE fb.b_expiration < TO_CHAR(SYSDATE,'YYYY/MM/DD hh24:mi:ss') " + "AND NOT EXISTS " + "(SELECT 1 "
				+ "FROM BID_DONE bd " + "WHERE fb.b_num=bd.b_num) ";
		String sql2 = "SELECT max(user_email)KEEP(DENSE_RANK FIRST ORDER BY bid_price DESC) win_user_email, "
				+ "max(bid_price) max_bid_price, "
				+ "max(seller_email)KEEP(DENSE_RANK FIRST ORDER BY bid_price DESC) seller_email, " + "b_num "
				+ "FROM finish_bid1 " + "GROUP BY b_num ";
		String sql3 = "INSERT INTO BID_DONE(win_user_email, max_bid_price, seller_email, b_num, confirm_status) "
				+ "values(?,?,?,?,0) ";
		try {
			// 연결 시도
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();

			pstmt.close();
			pstmt = conn.prepareStatement(sql1);
			result = pstmt.executeUpdate();

			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String win_user_email = rs.getString("win_user_email");
				int max_bid_price = rs.getInt("max_bid_price");
				String b_num = rs.getString("b_num");
				String seller_email = rs.getString("seller_email");

				pstmt1 = conn.prepareStatement(sql3);
				pstmt1.setString(1, win_user_email);
				pstmt1.setInt(2, max_bid_price);
				pstmt1.setString(3, seller_email);
				pstmt1.setString(4, b_num);
				result = pstmt1.executeUpdate();
			}

			rs.close();
			pstmt1.close();
			pstmt.close();
			// executeUpdate int 바뀐 갯수를 알려줌 0: 값이 안들어감 0아니면 값들어감

			// 넣어준 값이 하나라도 들어가면 1, 하나도 안들어가면 0 //executeUpdate 오토커밋 시켜버림
			if (result > 0) {

				System.out.println("bid update done");
			} else {

				System.out.println("bid done fail");
			}

		} catch (Exception e) {

			System.out.println("AuctionDAO.done_bid->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int max_bid_price(String b_num) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int max_bid_price = 0;
		String sql = "SELECT MAX(bs.bid_price)" + " FROM   BID_STATE bs, BOARD b" + " WHERE  b.b_num = ? "
				+ " AND    bs.pd_num=b.pd_num";
		try {
			// 연결 시도
			conn = getConnection();
			conn.setAutoCommit(false);// 오토커밋 막어버리기
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, b_num);

			rs = pstmt.executeQuery();
			// rs.next() 0줄이나 1줄임
			if (rs.next()) {
				max_bid_price = rs.getInt("max(bs.bid_price)");
			}
		} catch (Exception e) {
			conn.rollback();
			System.out.println("AuctionDAO.max_bid_price ->" + e.getMessage());
		}

		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

		return max_bid_price;
	}

	public int update_view_count(int b_view_count, String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql1 = "UPDATE BOARD SET b_view_count = ? WHERE b_num = ?";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql1);
			// 여기도 private때문에 bs.getUser_ 로 가져와야함

			pstmt.setInt(1, b_view_count);
			pstmt.setString(2, b_num);

			result = pstmt.executeUpdate();

			// executeUpdate int 바뀐 갯수를 알려줌 0: 값이 안들어감 0아니면 값들어감

			if (result == 1) {
				System.out.println("view count success");
			} else {
				System.out.println("view count fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.view count->" + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int update_board_num(String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(DISTINCT user_email) num_bidders, COUNT(bid_num) num_bids " + " FROM BID "
				+ " WHERE b_num = ? ";
		String sql1 = "UPDATE BOARD SET b_bidders_num = ?, b_bids = ? WHERE b_num = ?";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int b_bidders_num = rs.getInt("num_bidders");
				int b_bids = rs.getInt("num_bids");
				pstmt.close();
				pstmt = conn.prepareStatement(sql1);
				// 여기도 private때문에 bs.getUser_ 로 가져와야함

				pstmt.setInt(1, b_bidders_num);
				pstmt.setInt(2, b_bids);
				pstmt.setString(3, b_num);

				result = pstmt.executeUpdate();
			}

			// executeUpdate int 바뀐 갯수를 알려줌 0: 값이 안들어감 0아니면 값들어감

			if (result == 1) {
				System.out.println("view count success");
			} else {
				System.out.println("view count fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.view count->" + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int keep_money(Auction_Money am, Bid_State bs, int bid_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql1 = "INSERT INTO AUCTION_MONEY (user_email, balance, bid_num, withdraw, time) "
				+ "values(?,?,?,?,TO_CHAR(SYSDATE,'YYYY/MM/DD hh24:mi:ss')) ";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql1);

			pstmt.setString(1, bs.getUser_email());
			pstmt.setInt(2, (am.getBalance() - am.getWithdraw()));
			pstmt.setInt(3, bid_num);
			pstmt.setInt(4, -(am.getWithdraw()));

			result = pstmt.executeUpdate();

			// executeUpdate int 바뀐 갯수를 알려줌 0: 값이 안들어감 0아니면 값들어감

			if (result == 1) {
				System.out.println("keep money success");
			} else {
				System.out.println("keep money fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.keep money->" + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int pay_back_money(int max_bid_price, String b_num) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "CREATE OR REPLACE VIEW pay_back as " + "SELECT am.bid_num, am.user_email, am.balance "
				+ "FROM BID b, AUCTION_MONEY am " + "WHERE am.bid_num = b.bid_num(+) " + "AND b_num = '" + b_num
				+ "' ORDER BY am.bid_num DESC ";

		String sql1 = "SELECT max(bid_num), "
				+ "max(user_email)KEEP(DENSE_RANK FIRST ORDER BY bid_num DESC) user_email, "
				+ "max(balance)KEEP(DENSE_RANK FIRST ORDER BY bid_num DESC) balance " + "FROM PAY_BACK ";
		String sql2 = "INSERT INTO AUCTION_MONEY (user_email, balance, bid_num, deposit, time) "
				+ "values(?,?,?,?,TO_CHAR(SYSDATE,'YYYY/MM/DD hh24:mi:ss')) ";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();

			pstmt.close();
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int bid_num = rs.getInt("max(bid_num)");
				String user_email = rs.getString("user_email");
				int balance = rs.getInt("balance");
				pstmt.close();
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, user_email);
				pstmt.setInt(2, balance + max_bid_price);
				pstmt.setInt(3, bid_num);
				pstmt.setInt(4, max_bid_price);

				result = pstmt.executeUpdate();
			}

			// executeUpdate int 바뀐 갯수를 알려줌 0: 값이 안들어감 0아니면 값들어감

			if (result == 1) {
				System.out.println("pay_back money success");
			} else {
				System.out.println("pay back fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.pay_back money->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int charge_money(String user_email, int charge_money) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT balance FROM AUCTION_MONEY WHERE user_email = ? ORDER BY TIME DESC";

		String sql1 = "INSERT INTO AUCTION_MONEY (user_email, balance, deposit, time) "
				+ "values(?,?,?,TO_CHAR(SYSDATE,'YYYY/MM/DD hh24:mi:ss')) ";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				int balance = rs.getInt("balance");
				pstmt.close();
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, user_email);
				pstmt.setInt(2, balance + charge_money);
				pstmt.setInt(3, charge_money);

				result = pstmt.executeUpdate();
			}

			// executeUpdate int 바뀐 갯수를 알려줌 0: 값이 안들어감 0아니면 값들어감

			if (result == 1) {
				System.out.println("charge money success");
			} else {
				System.out.println("charge fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.charge_money->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int withdraw_money(String user_email, int withdraw_money) throws SQLException {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT balance FROM AUCTION_MONEY WHERE user_email = ? AND time IN "
				+ "(SELECT MAX(time) FROM AUCTION_MONEY) ";

		String sql1 = "INSERT INTO AUCTION_MONEY (user_email, balance, withdraw, time) "
				+ "values(?,?,?,TO_CHAR(SYSDATE,'YYYY/MM/DD hh24:mi:ss')) ";

		try {
			// 연결 시도
			conn = getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				int balance = rs.getInt("balance");
				if (balance >= withdraw_money) {
					pstmt.close();
					pstmt = conn.prepareStatement(sql1);
					pstmt.setString(1, user_email);
					pstmt.setInt(2, balance - withdraw_money);
					pstmt.setInt(3, -(withdraw_money));

					result = pstmt.executeUpdate();
				} else {
					result = 3;// 잔액 부족으로 출금 불가
				}

			}

			// executeUpdate int 바뀐 갯수를 알려줌 0: 값이 안들어감 0아니면 값들어감

			if (result == 1) {
				System.out.println("withdraw_money success");
			} else {
				System.out.println("withdraw_money fail");
			}
		} catch (Exception e) {

			System.out.println("AuctionDAO.withdraw_money->" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int pin_check(String user_email, String user_pin) throws SQLException {
		int check_result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT user_email, user_pin FROM USER_INFO WHERE user_email = ? and user_pin = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			pstmt.setString(2, user_pin);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("AuctionDAO.pinCheck pin" + rs.getString(2));
				check_result = 1;
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.loginCheck -> " + e.getMessage());
		}
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

		// check_result = 1 : pin 성공!, check_result = 0 : 실패;
		return check_result;
	}

	public List<Auction_Money> getBalance(String user_email) throws SQLException {
		List<Auction_Money> amoney = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = " SELECT am.bid_num, am.balance, am.withdraw, am.deposit, am.time, b.b_num "
				+ " FROM AUCTION_MONEY am, BID b " + " WHERE b.bid_num(+) = am.bid_num "
				+ " AND am.user_email = ? ORDER BY am.TIME desc ";

		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();

			while (rs.next()) { // 데이터를 여러개 돌려서
				Auction_Money am = new Auction_Money();

				am.setB_num(rs.getString("b_num"));
				am.setBalance(rs.getInt("balance"));
				am.setBid_num(rs.getInt("bid_num"));
				am.setDeposit(rs.getInt("deposit"));
				am.setWithdraw(rs.getInt("withdraw"));
				am.setTime(rs.getString("time"));
				amoney.add(am);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return amoney;

	}

	public List<Bid_State> getBidList(String b_num) throws SQLException {
		List<Bid_State> bidList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT bs.bid_price, bs.bid_num, bs.user_email, bs.bid_timestamp"
				+ " FROM   BID_STATE bs, BOARD b" + " WHERE  b.b_num = ?" + " AND    bs.pd_num = b.pd_num"
				+ " ORDER BY bs.bid_price DESC";

		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();

			while (rs.next()) { // 데이터를 여러개 돌려서
				Bid_State bs = new Bid_State();

				bs.setBid_price(rs.getInt("bid_price"));
				bs.setBid_num(rs.getInt("bid_num"));
				bs.setUser_email(rs.getString("user_email"));
				bs.setBid_timestamp(rs.getString("bid_timestamp"));
				bidList.add(bs);
			}
			return bidList;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return bidList;

	}

	public List<Product_Info> getProduct_Info(String b_num) throws SQLException {
		List<Product_Info> product_info = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT pi.*" + " FROM   PRODUCT_INFO pi, BOARD b" + " WHERE  b.b_num = ?"
				+ " AND    pi.pd_num = b.pd_num";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();

			while (rs.next()) { // 데이터를 여러개 돌려서
				Product_Info pi = new Product_Info();

				pi.setPd_num(rs.getString("pd_num"));
				pi.setBc_num(rs.getInt("bc_num"));
				pi.setC_num(rs.getInt("c_num"));
				pi.setSeller_email(rs.getString("seller_email"));
				pi.setSeller_nickname(rs.getString("seller_nickname"));
				pi.setPd_name(rs.getString("pd_name"));
				pi.setPd_price(rs.getInt("pd_price"));
				pi.setPd_unit(rs.getInt("pd_unit"));
				pi.setPd_condition(rs.getString("pd_condition"));
				pi.setPd_image(rs.getString("pd_image"));
				pi.setPd_buydate(rs.getString("pd_buydate"));

				product_info.add(pi);
				System.out.println("product_info list=>" + product_info);
			}
			return product_info;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return product_info;

	}

	public List<Board> getBoard_Info(String b_num) throws SQLException {
		List<Board> board_info = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT *" + " FROM   BOARD" + " WHERE  b_num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			rs = pstmt.executeQuery();

			while (rs.next()) { // 데이터를 여러개 돌려서
				Board b = new Board();

				b.setB_state(rs.getInt("b_state"));
				b.setB_contents(rs.getString("b_contents"));
				b.setB_view_count(rs.getInt("b_view_count"));
				b.setB_like_count(rs.getInt("b_like_count"));
				b.setB_regdate(rs.getString("b_regdate"));
				b.setSeller_email(rs.getString("seller_email"));
				b.setSeller_nickname(rs.getString("seller_nickname"));
				b.setB_bids(rs.getInt("b_bids"));
				b.setB_bidders_num(rs.getInt("b_bidders_num"));
				b.setB_expiration(rs.getString("b_expiration"));
				b.setPd_num(rs.getString("pd_num"));
				b.setBc_num(rs.getInt("bc_num"));
				b.setC_num(rs.getInt("c_num"));
				b.setB_title(rs.getString("b_title"));
				b.setB_num(rs.getString("b_num"));
				b.setPd_image(rs.getString("pd_image"));

				board_info.add(b);
			}
			return board_info;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return board_info;

	}

	public List<Jjim_List> jjim_list(String user_email, String b_num) throws SQLException {
		List<Jjim_List> jjim_list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql1 = "SELECT * FROM JJIM_LIST WHERE b_num = ? AND user_email = ? ";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, b_num);
			pstmt.setString(2, user_email);
			// System.out.println("user_email->" + user_email);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Jjim_List jl = new Jjim_List();
				jl.setB_num(b_num);
				jl.setUser_email(user_email);

				// System.out.println("list=>" + jjim);
				jjim_list.add(jl);
			}
			pstmt.close();
		} catch (Exception e) {
			System.out.println("AuctionDAO.jjim_list -> " + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		}

		return jjim_list;

	}

	public List<Jjim_List> jjim_list2(String user_email) throws SQLException {
		List<Jjim_List> jjim_list2 = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql1 = "SELECT b_num FROM JJIM_LIST WHERE user_email = ? ";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, user_email);
			// System.out.println("user_email->" + user_email);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Jjim_List jl = new Jjim_List();
				jl.setB_num(rs.getString("b_num"));

				// System.out.println("list=>" + jjim);
				jjim_list2.add(jl);
			}
			pstmt.close();
		} catch (Exception e) {
			System.out.println("AuctionDAO.jjim_list -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();

		}

		return jjim_list2;

	}

	// ####################### 김환 구현 ###########################
	//판매게시물 등록.
		public int posting(Product_Info pd, Board bd) throws SQLException {
			int result = 0; // result가 안됬을 때 : 데이터베이스에 용량이 없어서 추가가 안됨
			// 이미 회원가입 된 email이 insert되려고 할 경우.
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String pd_num = "";
			String b_num = "";
			String user_nickname = null;

			// user_nickname 가져오는 부분
			System.out.println();
			System.out.println("user_nickname 가져오는 부분 시작");

			sql = "select user_nickname from user_info where user_email='" + pd.getSeller_email() + "'";

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					user_nickname = rs.getString("user_nickname"); // rs.getInt(1);
				}

				System.out.println("user_nickname=" + user_nickname);

			} catch (Exception e) {
				System.out.println("pd_num 가져오기실패 ->" + e.getMessage());
				return result;
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// Product_info 테이블 데이터 입력 부분
			System.out.println();
			System.out.println("Product_info 테이블 데이터 입력 부분 시작");

			sql = "INSERT INTO PRODUCT_INFO(" + "PD_NUM,pd_name,pd_unit,pd_price," + "pd_condition,pd_buydate,pd_image,"
					+ " BC_NUM , C_NUM, seller_email, seller_nickname) " + "VALUES(TO_CHAR(SYSDATE,'YYMMDD')||"
					+ "TO_CHAR(?)||TO_CHAR(PI_NUM_SEQ.NEXTVAL),?,?,?,?,?,?,?,?,?,?)";
			// to_char는 c_num

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pd.getC_num()); // pd_num을 위한 c_num 입력
				pstmt.setString(2, pd.getPd_name());
				pstmt.setInt(3, pd.getPd_unit());
				pstmt.setInt(4, pd.getPd_price());
				pstmt.setString(5, pd.getPd_condition());
				pstmt.setString(6, pd.getPd_buydate());
				pstmt.setString(7, pd.getPd_image());
				pstmt.setInt(8, pd.getBc_num());
				pstmt.setInt(9, pd.getC_num());
				pstmt.setString(10, pd.getSeller_email());
				pstmt.setString(11, user_nickname);
				result = pstmt.executeUpdate();// int를 반환하고 바뀐 개수를 알려줌
				// 0이라면 값이 안들어갔음.

				System.out.println("pd.getC_num()" + pd.getC_num());
				System.out.println("pd.getPd_name()" + pd.getPd_name());
				System.out.println("pd.getPd_unit()" + pd.getPd_unit());
				System.out.println("pd.getPd_price()" + pd.getPd_price());
				System.out.println("pd.getPd_condition()" + pd.getPd_condition());
				System.out.println("pd.getPd_buydate()" + pd.getPd_buydate());
				System.out.println("pd.getPd_image()" + pd.getPd_image());
				System.out.println("pd.getBc_num()" + pd.getBc_num());
				System.out.println("pd.getC_num()" + pd.getC_num());
				System.out.println("pd.getSeller_email()" + pd.getSeller_email());
				System.out.println("user_nickname" + user_nickname);
				System.out.println();

				System.out.println("product table insert result ->" + result);
				if (result > 0) {

					System.out.println("Insert success!!");

				} else {

					System.out.println("Insert Fail;");
					return result;
				}
			} catch (Exception e) {
				
				System.out.println("product ->" + e.getMessage());
				return result;
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// DB에서 pd_num 시퀀스 생성된 가져오기 부분
			System.out.println();
			System.out.println("pd_num 가져오기 부분 시작");

			sql = "select pd_num from product_info where pd_name='" + pd.getPd_name() + "'" + "and pd_price="
					+ pd.getPd_price() + "and pd_buydate='" + pd.getPd_buydate() + "'" + "and seller_email='"
					+ pd.getSeller_email() + "'";

			try {
				// pd_num 가져오는 부분
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					pd_num = rs.getString("pd_num"); // rs.getInt(1);
				}

				System.out.println("pd_num=" + pd_num);

			} catch (Exception e) {
				System.out.println("pd_num 가져오기실패 ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// board 테이블 데이터 입력 부분

			System.out.println("board 테이블 데이터 입력 부분 시작");

			sql = "INSERT INTO BOARD(b_num,b_regdate,b_title,b_expiration,b_contents,"
					+ "pd_num,bc_num,c_num,seller_email, seller_nickname,pd_image,b_state,b_bidders_num,b_bids,b_like_count,b_view_count)"
					+ "values('b'||TO_CHAR(B_NUM_SEQ.NEXTVAL),to_char(sysdate,'yyyy/mm/dd'),?,?,?,?,?,?,?,?,?,1,0,0,0,0)";

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bd.getB_title());
				pstmt.setString(2, bd.getB_expiration());
				pstmt.setString(3, bd.getB_contents());
				pstmt.setString(4, pd_num);
				pstmt.setInt(5, bd.getBc_num());
				pstmt.setInt(6, bd.getC_num());
				pstmt.setString(7, pd.getSeller_email());
				pstmt.setString(8, user_nickname);
				pstmt.setString(9, pd.getPd_image());

				System.out.println("bd.getB_title()" + bd.getB_title());
				System.out.println("bd.getB_expiration()" + bd.getB_expiration());
				System.out.println("bd.getB_contents()" + bd.getB_contents());
				System.out.println("pd_num" + pd_num);
				System.out.println("bd.getBc_num()" + bd.getBc_num());
				System.out.println("bd.getC_num()" + bd.getC_num());
				System.out.println("pd.getSeller_email()" + pd.getSeller_email());
				System.out.println("user_nickname" + user_nickname);
				System.out.println("pd.getPd_image()" + pd.getPd_image());
				System.out.println();

				result = pstmt.executeUpdate();// int를 반환하고 바뀐 개수를 알려줌
				// 0이라면 값이 안들어갔음.
				System.out.println("board teble insert result ->" + result);
				if (result > 0) {

					System.out.println("Insert success!!");

				} else {

					System.out.println("Insert Fail;");
				}
			} catch (Exception e) {

				System.out.println("board teble insert ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// DB에서 b_num 시퀀스 생성된 가져오기 부분
			System.out.println();
			System.out.println("b_num 가져오기 부분 시작");

			sql = "select b_num from board " + "where b_title='" + bd.getB_title() + "'" + "and pd_num='" + pd_num + "'"
					+ "and b_expiration='" + bd.getB_expiration() + "'" + "and seller_email='" + pd.getSeller_email() + "'";

			try {
				// pd_num 가져오는 부분
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					b_num = rs.getString("b_num"); // rs.getInt(1);
				}

				System.out.println("b_num=" + b_num);

			} catch (Exception e) {
				System.out.println("b_num 가져오기실패 ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// seller 테이블 데이터 입력 부분
			System.out.println();
			System.out.println("seller 테이블 데이터 입력 부분 시작");

			sql = "INSERT INTO SELLER(b_num,seller_email, seller_nickname)" + "values(?,?,?)";

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, b_num);
				pstmt.setString(2, pd.getSeller_email());
				pstmt.setString(3, user_nickname);

				System.out.println("b_num" + b_num);
				System.out.println("pd.getSeller_email()" + pd.getSeller_email());
				System.out.println("user_nickname" + user_nickname);
				System.out.println();
				result = pstmt.executeUpdate();// int를 반환하고 바뀐 개수를 알려줌
				// 0이라면 값이 안들어갔음.
				System.out.println("seller teble insert result ->" + result);
				if (result > 0) {

					System.out.println("Insert success!!");

				} else {

					System.out.println("Insert Fail;");
				}
			} catch (Exception e) {

				System.out.println("seller teble insert ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// Action 테이블 데이터 입력 부분
			System.out.println();
			System.out.println("Action 테이블 데이터 입력 부분 시작");

			sql = "INSERT INTO Auction(b_num,pd_price,pd_unit,b_expiration,now_maxbid,now_win_user_email)"
					+ "values(?,?,?,?,0,0)";

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, b_num);
				pstmt.setInt(2, pd.getPd_price());
				pstmt.setInt(3, pd.getPd_unit());
				pstmt.setString(4, bd.getB_expiration());

				System.out.println("b_num : " + b_num);
				System.out.println("pd.getPd_price() : " + pd.getPd_price());
				System.out.println("pd.getPd_unit() : " + pd.getPd_unit());
				System.out.println("bd.getB_expiration() : " + bd.getB_expiration());
				System.out.println();
				result = pstmt.executeUpdate();// int를 반환하고 바뀐 개수를 알려줌
				// 0이라면 값이 안들어갔음.
				System.out.println("Auction teble insert result ->" + result);
				if (result > 0) {

					System.out.println("Insert success!!");

				} else {

					System.out.println("Insert Fail;");
				}
			} catch (Exception e) {

				System.out.println("Action teble insert ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			return result;

		}
		
		//재등록.
		public int rePosting(Product_Info pd, Board bd) throws SQLException {
			int result = 0; // result가 안됬을 때 : 데이터베이스에 용량이 없어서 추가가 안됨
			// 이미 회원가입 된 email이 insert되려고 할 경우.
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "";
			String b_num ="";

			System.out.println("rePosting 메소드 시작.");
			// Product_info 테이블 데이터 입력 부분
			System.out.println();
			System.out.println("Product_info 테이블 데이터 입력 부분 시작");

			sql = "UPDATE PRODUCT_INFO"
				 +" SET pd_price=?, pd_unit=?"
				 +" where pd_num=?";
			// to_char는 c_num

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pd.getPd_price()); // pd_num을 위한 c_num 입력
				pstmt.setInt(2, pd.getPd_unit());
				pstmt.setString(3, pd.getPd_num());
				result = pstmt.executeUpdate();// int를 반환하고 바뀐 개수를 알려줌
				// 0이라면 값이 안들어갔음.

				System.out.println("pd.getPd_price()" + pd.getPd_price());
				System.out.println("pd.getPd_unit()" + pd.getPd_unit());
				System.out.println("pd.getPd_num()" + pd.getPd_num());
				System.out.println();

				System.out.println("product table insert result ->" + result);
				if (result > 0) {

					System.out.println("Insert success!!");

				} else {
					
					System.out.println("Insert Fail;");
				}
			} catch (Exception e) {
				System.out.println("product ->" + e.getMessage());
				return result;
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// board 테이블 데이터 입력 부분

			System.out.println("board 테이블 데이터 입력 부분 시작");

			sql = "INSERT INTO BOARD(b_num,b_regdate,b_title,b_expiration,b_contents,"
					+ "pd_num,bc_num,c_num,seller_email, seller_nickname,pd_image,b_state,b_bidders_num,b_bids,b_like_count,b_view_count)"
					+ "values('b'||TO_CHAR(B_NUM_SEQ.NEXTVAL),to_char(sysdate,'yyyy/mm/dd'),?,?,?,?,?,?,?,?,?,1,0,0,0,0)";

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bd.getB_title());
				pstmt.setString(2, bd.getB_expiration());
				pstmt.setString(3, bd.getB_contents());
				pstmt.setString(4, pd.getPd_num());
				pstmt.setInt(5, bd.getBc_num());
				pstmt.setInt(6, bd.getC_num());
				pstmt.setString(7, pd.getSeller_email());
				pstmt.setString(8, pd.getSeller_nickname());
				pstmt.setString(9, bd.getPd_image());

				System.out.println("bd.getB_title()" + bd.getB_title());
				System.out.println("bd.getB_expiration()" + bd.getB_expiration());
				System.out.println("bd.getB_contents()" + bd.getB_contents());
				System.out.println("pd.getPd_num()" + pd.getPd_num());
				System.out.println("bd.getBc_num()" + bd.getBc_num());
				System.out.println("bd.getC_num()" + bd.getC_num());
				System.out.println("pd.getSeller_email()" + pd.getSeller_email());
				System.out.println("pd.getSeller_nickname()" + pd.getSeller_nickname());
				System.out.println("pd.getPd_image()" + bd.getPd_image());
				System.out.println();

				result = pstmt.executeUpdate();// int를 반환하고 바뀐 개수를 알려줌
				// 0이라면 값이 안들어갔음.
				System.out.println("board teble insert result ->" + result);
				if (result > 0) {

					System.out.println("Insert success!!");

				} else {

					System.out.println("Insert Fail;");
				}
			} catch (Exception e) {

				System.out.println("board teble insert ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// DB에서 b_num 시퀀스 생성된 가져오기 부분
			System.out.println();
			System.out.println("b_num 가져오기 부분 시작");

			sql = "select b_num from board " + "where b_title='" + bd.getB_title() + "'" + "and pd_num='" + pd.getPd_num() + "'"
					+ "and b_expiration='" + bd.getB_expiration() + "'" + "and seller_email='" + pd.getSeller_email() + "'";

			try {
				// pd_num 가져오는 부분
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					b_num = rs.getString("b_num"); // rs.getInt(1);
				}

				System.out.println("b_num=" + b_num);

			} catch (Exception e) {
				System.out.println("b_num 가져오기실패 ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// seller 테이블 데이터 입력 부분
			System.out.println();
			System.out.println("seller 테이블 데이터 입력 부분 시작");

			sql = "INSERT INTO SELLER(b_num,seller_email, seller_nickname)" + "values(?,?,?)";

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, b_num);
				pstmt.setString(2, pd.getSeller_email());
				pstmt.setString(3, pd.getSeller_nickname());

				System.out.println("b_num" + b_num);
				System.out.println("pd.getSeller_email()" + pd.getSeller_email());
				System.out.println("user_nickname" + pd.getSeller_nickname());
				System.out.println();
				result = pstmt.executeUpdate();// int를 반환하고 바뀐 개수를 알려줌
				// 0이라면 값이 안들어갔음.
				System.out.println("seller teble insert result ->" + result);
				if (result > 0) {

					System.out.println("Insert success!!");

				} else {

					System.out.println("Insert Fail;");
				}
			} catch (Exception e) {

				System.out.println("seller teble insert ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			// Action 테이블 데이터 입력 부분
			System.out.println();
			System.out.println("Action 테이블 데이터 입력 부분 시작");

			sql = "INSERT INTO Auction(b_num,pd_price,pd_unit,b_expiration,now_maxbid,now_win_user_email)"
					+ "values(?,?,?,?,0,0)";

			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, b_num);
				pstmt.setInt(2, pd.getPd_price());
				pstmt.setInt(3, pd.getPd_unit());
				pstmt.setString(4, bd.getB_expiration());

				System.out.println("b_num : " + b_num);
				System.out.println("pd.getPd_price() : " + pd.getPd_price());
				System.out.println("pd.getPd_unit() : " + pd.getPd_unit());
				System.out.println("bd.getB_expiration() : " + bd.getB_expiration());
				System.out.println();
				result = pstmt.executeUpdate();// int를 반환하고 바뀐 개수를 알려줌
				// 0이라면 값이 안들어갔음.
				System.out.println("Auction teble insert result ->" + result);
				if (result > 0) {

					System.out.println("Insert success!!");

				} else {

					System.out.println("Insert Fail;");
				}
			} catch (Exception e) {

				System.out.println("Action teble insert ->" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			}

			return result;

		}

	// ############## 홍진형 구현 #####################
	public List<Jjim> jjim(String user_email) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		List<Jjim> jjim1 = new ArrayList<Jjim>();
		String sql1 = "select b_num from jjim_list where user_email = ? ";
		String sql2 = "select b.b_title , a.now_win_user_email, a.now_maxbid , DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image , b.b_num , b.bc_num ,  b.c_num,  b.b_expiration from board b , auction a "
				+ " where b.b_num = a.b_num " + " AND B.B_NUM = ? ";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, user_email);
			// System.out.println("user_email->" + user_email);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String b_num = rs.getString("b_num");
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, b_num);
				rs1 = pstmt.executeQuery();
				if (rs1.next()) {
					Jjim jjim = new Jjim();
					jjim.setB_expiration(rs1.getString("b_expiration"));
					jjim.setB_num(rs1.getString("b_num"));
					jjim.setPd_image(rs1.getString("pd_image"));
					jjim.setBc_num(rs1.getInt("bc_num"));
					jjim.setC_num(rs1.getInt("c_num"));
					jjim.setB_title(rs1.getString("b_title"));
					jjim.setNow_maxbid(rs1.getString("now_maxbid"));
					jjim.setNow_win_user_email(rs1.getString("now_win_user_email"));
					// System.out.println("list=>" + jjim);
					jjim1.add(jjim);
					rs1.close();
					System.out.println("222");
				}
				System.out.println("1111111111");
			}

		} catch (Exception e) {
			System.out.println("AuctionDAO.jjim -> " + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		}

		return jjim1;

	}

	public int delete_jjim(String b_num, String user_email) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from jjim_list where b_num = ? and user_email = ? ";
		String sql1 = "update board set b_like_count=b_like_count-1 where b_num= ? ";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_num);
			pstmt.setString(2, user_email);
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, b_num);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("jjimlist" + e.getMessage());

		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();

		}
		return result;
	}

	public List<BiddingList> bidding(String user_email) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BiddingList> bidlist = new ArrayList<BiddingList>();
		String sql = "select p.pd_name ,a.now_maxbid, p.bc_num , p.c_num, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, b.b_expiration , b.b_num , c.bid_price    from product_info p  , board b , bid_state c , auction a where b.pd_num = c.pd_num  and p.pd_num = c.pd_num and a.b_num  = b.b_num and c.user_email = ?  order by b.b_expiration desc ";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			System.out.println("user_email->" + user_email);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BiddingList bl = new BiddingList();
				bl.setB_expiration(rs.getString("b_expiration"));
				bl.setBid_price(rs.getInt("bid_price"));
				bl.setPd_name(rs.getString("pd_name"));
				bl.setB_num(rs.getString("b_num"));
				bl.setPd_image(rs.getString("pd_image"));
				bl.setBc_num(rs.getInt("bc_num"));
				bl.setC_num(rs.getInt("C_num"));
				bl.setNow_maxbid(rs.getInt("now_maxbid"));

				// System.out.println("list=>" + bl);
				bidlist.add(bl);
			}
		} catch (Exception e) {
			System.out.println("jjimlist" + e.getMessage());

		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		}

		return bidlist;

	}

	public List<BiddingList> sellList(String user_email) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BiddingList> sellList = new ArrayList<BiddingList>();
		String sql = "select b.b_title , a.now_maxbid , b.b_expiration , b.b_num , b.bc_num , b.pd_num, b.c_num, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image from board b , auction a , seller s where s.seller_email= ? and b.b_num = a.b_num and b.b_num = s.b_num order by b.b_expiration desc  ";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_email);
			System.out.println("user_email->" + user_email);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BiddingList bl = new BiddingList();
				bl.setB_expiration(rs.getString("b_expiration"));
				bl.setB_title(rs.getString("b_title"));
				bl.setB_num(rs.getString("b_num"));
				bl.setPd_image(rs.getString("pd_image"));
				bl.setBc_num(rs.getInt("bc_num"));
				bl.setC_num(rs.getInt("C_num"));
				bl.setNow_maxbid(rs.getInt("now_maxbid"));

				// System.out.println("list=>" + bl);
				sellList.add(bl);
			}
		} catch (Exception e) {
			System.out.println("sellList" + e.getMessage());

		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		}

		return sellList;
	}

	// ################################# 서정철 구현 ##################################
	public void mainInit(TotalListManager tl, Interest interest, int loginState) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql0 = "SELECT bc_num, subject FROM INTEREST WHERE USER_EMAIL = ?";
		String sql1 = "SELECT * FROM CATEGORIES";
		String sql2 = "SELECT * FROM PRODUCT_INFO";
//		String sql3 = "SELECT * FROM BOARD where b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') and b_state = 1";
		String sql3 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS')";
		// 대분류별로 판매리스트를 인기순으로 정렬가져옴.
		String sql4 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 100 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_like_count desc";

		String sql5 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 200 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_like_count desc";

		String sql6 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 300 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_like_count desc";

		String sql7 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 400 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_like_count desc";

		String sql8 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 500 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_like_count desc";

		String sql9 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 600 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_like_count desc";

		// 대분류별로 판매리스트를 경매마감순으로 정렬가져옴.
		String sql10 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 100 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_expiration asc";

		String sql11 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 200 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_expiration asc";

		String sql12 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 300 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_expiration asc";

		String sql13 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 400 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_expiration asc";

		String sql14 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 500 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_expiration asc";

		String sql15 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 600 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_expiration asc";

		// 대분류별로 판매리스트를 조회순으로 정렬가져옴.
		String sql16 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 100 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_view_count desc";

		String sql17 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 200 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_view_count desc";

		String sql18 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 300 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_view_count desc";

		String sql19 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 400 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_view_count desc";

		String sql20 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 500 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_view_count desc";

		String sql21 = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.bc_num = 600 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') order by b.b_view_count desc";

		try {
			conn = getConnection();

			if (loginState == 1) { // 접속 중이라면 관심카테고리 해당 게정의 관심카테고리 데이터를 interest DTO에 저장.
				pstmt = conn.prepareStatement(sql0);
				pstmt.setString(1, interest.getUser_email());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					interest.setBc_num(rs.getInt(1));
					interest.setC_num(0);
					interest.setSubject(rs.getString(2));
				}
				rs.close();
				pstmt.close();
			}
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Categories cg = new Categories();
					cg.setBc_num(rs.getInt("bc_num"));
					cg.setC_num(rs.getInt("c_num"));
					cg.setSubject(rs.getString("subject"));
					tl.cg_list.add(cg);
				} while (rs.next());
			} // 카테고리 데이터 셋팅
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Product_Info pi = new Product_Info();
					pi.setPd_num(rs.getString("pd_num"));
					pi.setBc_num(rs.getInt("bc_num"));
					pi.setC_num(rs.getInt("c_num"));
					pi.setSeller_email(rs.getString("seller_email"));
					pi.setSeller_nickname(rs.getString("seller_nickname"));
					pi.setPd_name(rs.getString("pd_name"));
					pi.setPd_price(rs.getInt("pd_price"));
					pi.setPd_unit(rs.getInt("pd_unit"));
					pi.setPd_condition(rs.getString("pd_condition"));
					pi.setPd_image(rs.getString("pd_image"));
					pi.setPd_buydate(rs.getString("pd_buydate"));
					tl.pd_list.add(pi);
				} while (rs.next());
			} // 상품정보 데이터 셋팅
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql3);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.bd_list.add(bd);
				} while (rs.next());
			} // board 데이터 셋팅
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql4);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.lk_list_100.add(bd);
				} while (rs.next());
			} // 인기순 데이터 정리 - CODE : 100
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql5);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.lk_list_200.add(bd);
				} while (rs.next());
			} // 인기순 데이터 정리 - CODE : 200
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql6);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.lk_list_300.add(bd);
				} while (rs.next());
			} // 인기순 데이터 정리 - CODE : 300
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql7);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.lk_list_400.add(bd);
				} while (rs.next());
			} // 인기순 데이터 정리 - CODE : 400
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql8);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.lk_list_500.add(bd);
				} while (rs.next());
			} // 인기순 데이터 정리 - CODE : 500
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql9);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.lk_list_600.add(bd);
				} while (rs.next());
			} // 인기순 데이터 정리 - CODE : 600
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.exp_list_100.add(bd);
				} while (rs.next());
			} // 경매마감순 데이터 정리 - CODE : 100
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql11);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.exp_list_200.add(bd);
				} while (rs.next());
			} // 경매마감순 데이터 정리 - CODE : 200
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql12);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.exp_list_300.add(bd);
				} while (rs.next());
			} // 경매마감순 데이터 정리 - CODE : 300
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql13);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.exp_list_400.add(bd);
				} while (rs.next());
			} // 경매마감순 데이터 정리 - CODE : 400
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql14);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.exp_list_500.add(bd);
				} while (rs.next());
			} // 경매마감순 데이터 정리 - CODE : 500
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql15);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.exp_list_600.add(bd);
				} while (rs.next());
			} // 경매마감순 데이터 정리 - CODE : 600
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql16);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.ovc_list_100.add(bd);
				} while (rs.next());
			} // 조회순 데이터 정리 - CODE : 100
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql17);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.ovc_list_200.add(bd);
				} while (rs.next());
			} // 조회순 데이터 정리 - CODE : 200
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql18);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.ovc_list_300.add(bd);
				} while (rs.next());
			} // 조회순 데이터 정리 - CODE : 300
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql19);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.ovc_list_400.add(bd);
				} while (rs.next());
			} // 조회순 데이터 정리 - CODE : 400
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql20);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.ovc_list_500.add(bd);
				} while (rs.next());
			} // 조회순 데이터 정리 - CODE : 500
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql21);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					tl.ovc_list_600.add(bd);
				} while (rs.next());
			} // 조회순 데이터 정리 - CODE : 600
		} catch (Exception e) {
			System.out.println("AuctionDAO.mainInit -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public int jjimButtonPro(String user_email, String b_num) throws SQLException {
		int result = 0; // DML 실행시 변화 확인
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// sql1 : 찜목록 테이블에 찜되어 있는지 여부 확인
		String sql1 = "SELECT B_NUM FROM JJIM_LIST WHERE USER_EMAIL=? AND B_NUM = ?";
		// sql2 : 찜목록에 없다면 찜목록 테이블에 추가
		String sql2 = "INSERT INTO JJIM_LIST VALUES(?, ?)"; // B_NUM, USER_EMAIL
		String sql2_1 = "UPDATE board SET b_like_count = b_like_count + 1 where b_num = ?";
		// sql3 : 찜목록에 있다면 찜목록에서 제거
		String sql3 = "DELETE JJIM_LIST WHERE USER_EMAIL=? AND B_NUM = ?";
		String sql3_1 = "UPDATE board SET b_like_count = b_like_count - 1 where b_num = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, user_email);
			pstmt.setString(2, b_num);
			rs = pstmt.executeQuery();
			if (rs.next())
				result = 1;
			else
				result = 0;
			rs.close();

			switch (result) {
			case 0:
				pstmt.close();
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, b_num);
				pstmt.setString(2, user_email);
				result = pstmt.executeUpdate();
				if (result == 1) {
					pstmt.close();
					pstmt = conn.prepareStatement(sql2_1);
					pstmt.setString(1, b_num);

					int tmp = 0;
					tmp = pstmt.executeUpdate();
					if (tmp != 0) {
						System.out.println("상품번호 : " + b_num + " 찜갯수+1 처리 성공");
					}
					result = 2; // 찜 성공
					System.out.println("AuctionDAO.jjimButtonPro Jjim Success!!");
				} else {
					result = 3; // 찜 실패
					System.out.println("AuctionDAO.jjimButtonPro Jjim Fail;;");
				}
				pstmt.close();
				break;
			case 1:
				pstmt.close();
				pstmt = conn.prepareStatement(sql3);
				pstmt.setString(1, user_email);
				pstmt.setString(2, b_num);
				result = pstmt.executeUpdate();
				if (result == 1) {
					pstmt.close();
					pstmt = conn.prepareStatement(sql3_1);
					pstmt.setString(1, b_num);
					int tmp = 0;
					tmp = pstmt.executeUpdate();
					if (tmp != 0) {
						System.out.println("상품번호 : " + b_num + " 찜갯수-1 처리 성공");
					}
					result = 4; // 찜삭제 성공
					System.out.println("AuctionDAO.jjimButtonPro Jjim Delete Success!!");
				} else {
					result = 5; // 찜삭제 실패
					System.out.println("AuctionDAO.jjimButtonPro Jjim Delete Fail;;");
				}
				pstmt.close();
				break;
			}
		} catch (Exception e) {
			System.out.println("jjimButtonPro -> " + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public void update_BoardState() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "UPDATE BOARD SET b_state = 0 where b_state>=0 and b_expiration < to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS')";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
			if (result != 0) {
				System.out.println(" 경매기간이 지난 데이터 갯수 -> " + result);
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.update_BoardState -> " + e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public int searchEngine(List<Board> board_list, String search_word) throws SQLException {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String trim_word = search_word.trim();
		String sql = "SELECT b.b_num, b.b_title, b.pd_num, b.bc_num, b.c_num, b.b_expiration, b.b_bidders_num, b.b_bids, b.seller_email, b.seller_nickname, b.b_regdate, b.b_like_count, b.b_view_count, b.b_contents, b.b_state, DECODE( SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1)), NULL, b.PD_IMAGE, SUBSTR(b.PD_IMAGE,0,(INSTR(b.PD_IMAGE, ':')-1))) as pd_image, a.now_maxbid FROM BOARD b, auction a where b.b_state = 1 and b.b_num=a.b_num and b.b_expiration >= to_char(sysdate, 'yyyy/mm/dd HH24:MI:SS') " + " and ( b_title like ('%" + trim_word + "%') or b_contents like ('%" + trim_word + "%') )";
//		"SELECT b_num, b_title, pd_num, bc_num, c_num, b_expiration, b_bidders_num, b_bids, seller_email, seller_nickname, b_regdate, b_like_count, b_view_count, b_contents, b_state, DECODE( SUBSTR(PD_IMAGE,0,(INSTR(PD_IMAGE, ':')-1)), NULL, PD_IMAGE, SUBSTR(PD_IMAGE,0,(INSTR(PD_IMAGE, ':')-1))) as pd_image from board where b_state=1 and ( b_title like ('%" + trim_word + "%') or b_contents like ('%" + trim_word + "%') )";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			// pstmt.setString(1, search_word);
			// pstmt.setString(2, search_word);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				do {
					Board bd = new Board();
					bd.setB_num(rs.getString("b_num"));
					bd.setB_title(rs.getString("b_title"));
					bd.setPd_num(rs.getString("pd_num"));
					bd.setBc_num(rs.getInt("bc_num"));
					bd.setC_num(rs.getInt("c_num"));
					bd.setB_expiration(rs.getString("b_expiration"));
					bd.setB_bidders_num(rs.getInt("b_bidders_num"));
					bd.setB_bids(rs.getInt("b_bids"));
					bd.setSeller_email(rs.getString("seller_email"));
					bd.setSeller_nickname(rs.getString("seller_nickname"));
					bd.setB_regdate(rs.getString("b_regdate"));
					bd.setB_like_count(rs.getInt("b_like_count"));
					bd.setB_view_count(rs.getInt("b_view_count"));
					bd.setB_contents(rs.getString("b_contents"));
					bd.setB_state(rs.getInt("b_state"));
					bd.setPd_image(rs.getString("pd_image"));
					bd.setNow_maxbid(rs.getString("now_maxbid"));
					board_list.add(bd);
				} while (rs.next());
				result = 1;
			} else {
				result = 0;
			}
		} catch (Exception e) {
			System.out.println("AuctionDAO.searchEngine -> " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}
}