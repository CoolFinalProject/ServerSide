package ambient_intelligence.boundary;

import ambient_intelligence.data.UserEntity;
import ambient_intelligence.data.UserRole;
import ambient_intelligence.excptions.InvalidInputException;
import ambient_intelligence.helper.UserId;

public class UserBoundary {
	private UserId userId;
	private UserRole role;
	private String username;
	private String avatar;
	
	public UserBoundary() { //empty constructor
	}
	
	

	
    public UserBoundary(UserId userId, UserRole role, String username, String avatar) {
		super();
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}




	// Validate email format
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        return email != null && email.matches(emailRegex);
    }

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}




	public UserId getUserId() {
		return userId;
	}




	public void setUserId(UserId userId) {
		this.userId = userId;
	}




	@Override
	public String toString() {
		return "UserBoundary {userId: " + userId + ", role: " + role + ", username: " + username + ", avatar: " + avatar
				+ "}";
	}


	
}
