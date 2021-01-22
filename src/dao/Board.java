package dao;

public class Board{
	private int b_state;
	private String b_contents;
	private int b_view_count;
	private int b_like_count;
	private String b_regdate;
	private String seller_nickname;
	private String seller_email;
	private int b_bids;
	private int b_bidders_num;
	private String b_expiration;
	private int c_num;
	private int bc_num;
	private String pd_num;
	private String b_title;
	private String b_num;
	private String pd_image;
	private String now_maxbid;
	public String getNow_maxbid() {
		return now_maxbid;
	}
	public void setNow_maxbid(String now_maxbid) {
		this.now_maxbid = now_maxbid;
	}
	public String getPd_image() {
		return pd_image;
	}
	public void setPd_image(String pd_image) {
		this.pd_image = pd_image;
	}
	public int getB_state() {
		return b_state;
	}
	public void setB_state(int b_state) {
		this.b_state = b_state;
	}
	public String getB_contents() {
		return b_contents;
	}
	public void setB_contents(String b_contents) {
		this.b_contents = b_contents;
	}
	public int getB_view_count() {
		return b_view_count;
	}
	public void setB_view_count(int b_view_count) {
		this.b_view_count = b_view_count;
	}
	public int getB_like_count() {
		return b_like_count;
	}
	public void setB_like_count(int b_like_count) {
		this.b_like_count = b_like_count;
	}
	public String getB_regdate() {
		return b_regdate;
	}
	public void setB_regdate(String b_regdate) {
		this.b_regdate = b_regdate;
	}
	public String getSeller_nickname() {
		return seller_nickname;
	}
	public void setSeller_nickname(String seller_nickname) {
		this.seller_nickname = seller_nickname;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public int getB_bids() {
		return b_bids;
	}
	public void setB_bids(int b_bids) {
		this.b_bids = b_bids;
	}
	public int getB_bidders_num() {
		return b_bidders_num;
	}
	public void setB_bidders_num(int b_bidders_num) {
		this.b_bidders_num = b_bidders_num;
	}
	public String getB_expiration() {
		return b_expiration;
	}
	public void setB_expiration(String b_expiration) {
		this.b_expiration = b_expiration;
	}
	public int getC_num() {
		return c_num;
	}
	public void setC_num(int c_num) {
		this.c_num = c_num;
	}
	public int getBc_num() {
		return bc_num;
	}
	public void setBc_num(int bc_num) {
		this.bc_num = bc_num;
	}
	public String getPd_num() {
		return pd_num;
	}
	public void setPd_num(String pd_num) {
		this.pd_num = pd_num;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_num() {
		return b_num;
	}
	public void setB_num(String b_num) {
		this.b_num = b_num;
	}

}