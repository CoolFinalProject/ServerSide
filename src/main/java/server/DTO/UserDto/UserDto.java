package server.DTO.UserDto;

import java.util.Date;
import java.util.Map;

import server.enums.UserRole;

public class UserDto {
    private String userName;
    private String passWord;
    private String userId;
    private boolean active;
    private UserRole userRole;
    private Date creationTime;
    private Map<String, Float> genrePreferences;

    public UserDto() {
        active = true;
    }

    public UserDto(String userName, String passWord, String userId, boolean active,
                   UserRole userRole, Date creationTime, Map<String, Float> genrePreferences) {
        this.active = active;
        this.creationTime = creationTime;
        this.passWord = passWord;
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.genrePreferences = genrePreferences;
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

    public Map<String, Float> getGenrePreferences() {
        return genrePreferences;
    }

    public void setGenrePreferences(Map<String, Float> genrePreferences) {
        this.genrePreferences = genrePreferences;
    }

    @Override
    public String toString() {
        return "UserDto [userName=" + userName
                + ", passWord=" + passWord
                + ", userId=" + userId
                + ", active=" + active
                + ", userRole=" + userRole
                + ", creationTime=" + creationTime
                + ", genrePreferences=" + genrePreferences
                + "]";
    }
}