package ambient_intelligence.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="USERS")
public class UserEntity {
	private String email;
	private String username;
	@Id
	private String userId;

	private UserRole role;
	private String avatar;
	
	public UserEntity() {
		
	}
	
	
	public UserEntity(String email, String username, String userId, UserRole role,String avatar) {
		super();
		this.email = email;
		this.username = username;
		this.userId = userId;
		this.role = role;
		this.avatar=avatar;
	}



	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setEmail(String email)
	{
		this.email=email;
	}
	public String getuserId() {
		return userId;
	}
	
	public void setuserId(String userId) {
		this.userId = userId;
	}

		
	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}


	public String getEmail() {
		return this.email;
	}


	@Override
	public String toString() {
		return "UserEntity {email: " + email + ", name: " + username + ", userId: " + userId + ", role: " + role
				+ ", avatar: " + avatar + "}";
	}


}
