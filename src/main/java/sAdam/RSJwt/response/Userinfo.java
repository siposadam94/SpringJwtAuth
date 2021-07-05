package sAdam.RSJwt.response;

public class Userinfo {

	private String username;
	private String phoneNumber;
	private Object roles;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Object getRoles() {
		return roles;
	}
	public void setRoles(Object roles) {
		this.roles = roles;
	}
	
	
}
