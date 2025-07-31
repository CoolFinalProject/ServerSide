package server.DTO.UserDto;


/**
 * This class will be used to authenticate users - will get sent when user wants to authenticate by username+password
 * 
 * **/
public class UserAuthenticateDto {
	private String userName;
	private String passWord;
	
	public UserAuthenticateDto() {};
	public UserAuthenticateDto(String userName, String passWord) {
		super();
		this.userName = userName;
		this.passWord = passWord;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@Override
	public String toString() {
		return "UserAuthenticateDto {userName: " + userName + ", passWord: " + passWord + "}";
	}
	
	
	
}
