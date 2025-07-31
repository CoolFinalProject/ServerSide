package server.DTO.UserDto;

import server.enums.UserRole;

public class UserDto {
	private String userName;
	private String passWord;
	private String userId;
	private UserRole userRole;
//	private HashMap<String, Float> genrePrefrences;
	
	public UserDto() {};
	
	
	
	public UserDto(String userName, String passWord, String userId, UserRole userRole) {
	super();
	this.userName = userName;
	this.passWord = passWord;
	this.userId = userId;
	this.userRole = userRole;
}



	public String getUserName() {
		return userName;
	}
	
	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserDto {userName: " + userName + ", passWord: " + passWord + ", userId: " + userId + ", userRole: "
				+ userRole + "}";
	}
	
	
}
