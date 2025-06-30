package ambient_intelligence.boundary;

import java.util.Date;
import java.util.Map;

import ambient_intelligence.helper.CreatedBy;

public class NewObjectBoundary {
	private String type;
	private String alias;
	private String status;
	private boolean active;
	private Date creationTimestamp;
	private CreatedBy createdBy;
	private Map<String, Object> details;
	
	
	public NewObjectBoundary() {};
	
	public NewObjectBoundary(String type, String alias, String status, boolean active, Date creationTimestamp,
			CreatedBy createdBy, Map<String, Object> details) {
		super();
		this.type = type;
		this.alias = alias;
		this.status = status;
		this.active = active;
		this.creationTimestamp = creationTimestamp;
		this.createdBy = createdBy;
		this.details = details;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	public CreatedBy getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}
	public Map<String, Object> getDetails() {
		return details;
	}
	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "NewObjectBoundary {type: " + type + ", alias: " + alias + ", status: " + status + ", active: " + active
				+ ", creationTimestamp: " + creationTimestamp + ", createdBy: " + createdBy + ", details: " + details
				+ "}";
	}
	
	
	
}
