package server.entities.UserEntities;

import java.util.Date;

import jakarta.persistence.*;


import server.enums.UserRole;

@Entity
@Table(name = "USERS")
public class UserEntity {
    private String userName;
	private String passWord;

    @Id
	private String userId;

	private boolean active;

    @Enumerated(EnumType.STRING) private UserRole userRole;

    private Date creationTime;


    public UserEntity(String userName, String passWord, String userId, boolean active, UserRole userRole,Date creationTime) {
        this.active = active;
        this.passWord = passWord;
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.creationTime=creationTime;
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
    
    public Date getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    @Override
    public String toString() {
        return "UserEntity [userName=" + userName + ", passWord=" + passWord + ", userId=" + userId + ", active="
                + active + ", userRole=" + userRole + ", creationTime=" + creationTime + "]";
    }


    
}
