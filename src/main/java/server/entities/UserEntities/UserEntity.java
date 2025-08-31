package server.entities.UserEntities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import server.enums.UserRole;

@Document(collection="users")
public class UserEntity {
    private String userName;
	private String passWord;
    @Id
	private String userId; 
	private boolean active;
	private UserRole userRole;

    public UserEntity(String userName, String passWord, String userId, boolean active, UserRole userRole) {
        this.active = active;
        this.passWord = passWord;
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
    }
    public UserEntity(){};

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
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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
        return "UserEntity [userName=" + userName + ", passWord=" + passWord + ", userId=" + userId + ", active="
                + active + ", userRole=" + userRole + "]";
    }


    
}
