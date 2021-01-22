package dao;

public class Admin_Board {
	private int b_state;
	private String seller_email;
	private String b_expiration;
	private String pd_num;
	private String b_num;
	private String now_maxbid;
	private String now_win_user_email;
	private String win_user_email;
	private int max_bid_price;
	private int confirm_status;
	
	public String getWin_user_email() {
		return win_user_email;
	}
	public void setWin_user_email(String win_user_email) {
		this.win_user_email = win_user_email;
	}
	public int getMax_bid_price() {
		return max_bid_price;
	}
	public void setMax_bid_price(int max_bid_price) {
		this.max_bid_price = max_bid_price;
	}
	public int getConfirm_status() {
		return confirm_status;
	}
	public void setConfirm_status(int confirm_status) {
		this.confirm_status = confirm_status;
	}
	public int getB_state() {
		return b_state;
	}
	public void setB_state(int b_state) {
		this.b_state = b_state;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getB_expiration() {
		return b_expiration;
	}
	public void setB_expiration(String b_expiration) {
		this.b_expiration = b_expiration;
	}
	public String getPd_num() {
		return pd_num;
	}
	public void setPd_num(String pd_num) {
		this.pd_num = pd_num;
	}
	public String getB_num() {
		return b_num;
	}
	public void setB_num(String b_num) {
		this.b_num = b_num;
	}
	public String getNow_maxbid() {
		return now_maxbid;
	}
	public void setNow_maxbid(String now_maxbid) {
		this.now_maxbid = now_maxbid;
	}
	public String getNow_win_user_email() {
		return now_win_user_email;
	}
	public void setNow_win_user_email(String now_win_user_email) {
		this.now_win_user_email = now_win_user_email;
	}

}
