package dao;

import java.sql.Date;

public class User_Info {
	private String user_email;		//아이디
	private String user_password;	//비밀번호
	private String user_nickname;	//닉네임
	private String user_name;		//실제이름
	private String user_phone;		//연락처
	private String user_address;	//집주소
	private String user_gender;		//성별
	private Date user_birth;		//생년월일
	private String user_pin;		//출금핀번호
	private String user_image;		//프로필이미지
	private Date user_regdate;		//회원가입일
	private int user_active;		//탈퇴여부 0:탈퇴 1:가입
	
	
	public int getUser_active() {
		return user_active;
	}
	public void setUser_active(int user_active) {
		this.user_active = user_active;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getUser_gender() {
		return user_gender;
	}
	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}
	public Date getUser_birth() {
		return user_birth;
	}
	public void setUser_birth(Date user_birth) {
		this.user_birth = user_birth;
	}
	public String getUser_pin() {
		return user_pin;
	}
	public void setUser_pin(String user_pin) {
		this.user_pin = user_pin;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public Date getUser_regdate() {
		return user_regdate;
	}
	public void setUser_regdate(Date user_regdate) {
		this.user_regdate = user_regdate;
	}
	
}
