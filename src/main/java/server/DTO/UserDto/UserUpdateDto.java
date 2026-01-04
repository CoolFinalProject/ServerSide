package server.DTO.UserDto;

import server.enums.UserRole;

public class UserUpdateDto {
    private String userName;
	private String passWord;
    private boolean active;
    private UserRole userRole;


    public UserUpdateDto(){};
    public UserUpdateDto(String userName, String passWord, boolean active, UserRole userRole) 
    {
        this.userName = userName;
        this.passWord = passWord;
        this.active = active;
        this.userRole = userRole;
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
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    @Override
    public String toString() {
        return "UserUpdateDto [userName=" + userName + ", passWord=" + passWord + ", active=" + active + ", userRole="
                + userRole + "]";
    }

    
    
}
