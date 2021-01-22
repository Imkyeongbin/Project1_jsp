package dao;

public class Bid {
	private int bid_num;
	private String b_num;
	private String user_email;
	private String bid_timestamp;
	private int bid_price;
	public int getBid_num() {
		return bid_num;
	}
	public void setBid_num(int bid_num) {
		this.bid_num = bid_num;
	}
	public String getB_num() {
		return b_num;
	}
	public void setB_num(String b_num) {
		this.b_num = b_num;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getBid_timestamp() {
		return bid_timestamp;
	}
	public void setBid_timestamp(String bid_timestamp) {
		this.bid_timestamp = bid_timestamp;
	}
	public int getBid_price() {
		return bid_price;
	}
	public void setBid_price(int bid_price) {
		this.bid_price = bid_price;
	}
}
