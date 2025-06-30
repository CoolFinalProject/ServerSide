package ambient_intelligence.helper;

public class UserId {
	String email;
	String systemId;
	
	public UserId() {};
	public UserId(String email, String systemId) {
		super();
		this.email = email;
		this.systemId = systemId;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	@Override
	public String toString() {
		return "UserId {email: " + email + ", systemId: " + systemId + "}";
	}
	
	
	
}
