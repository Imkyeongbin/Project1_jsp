package dao;

public class Bid_Done {
	private String win_user_email;
	private int max_bid_price;
	private String seller_email;
	private String b_num;
	private int confirm_status;
	
	public int getConfirm_status() {
		return confirm_status;
	}
	public void setConfirm_status(int confirm_status) {
		this.confirm_status = confirm_status;
	}
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
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getB_num() {
		return b_num;
	}
	public void setB_num(String b_num) {
		this.b_num = b_num;
	}
	
	
}
