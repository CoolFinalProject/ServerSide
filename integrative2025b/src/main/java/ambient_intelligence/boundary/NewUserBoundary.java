package ambient_intelligence.boundary;

import ambient_intelligence.data.UserRole;
import ambient_intelligence.helper.UserId;

public class NewUserBoundary {
	private String email;
	private UserRole role;
	private String username;
	private String avatar;
	


	public NewUserBoundary() {};
	public NewUserBoundary(String email, UserRole role, String username, String avatar) {
		super();
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "InputUserBoundary {email: " + email + ", role: " + role + ", username: " + username + ", avatar: "
				+ avatar + "}";
	}



}
