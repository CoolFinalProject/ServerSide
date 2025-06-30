package ambient_intelligence.data;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "OBJECTS")
public class ObjectEntity {

	@Id
	private String objectId;
	private String type;
	private String alias;
	private String status;
	private boolean active;
	
	private Date creationTimestamp;
	
	private String createdBy;
	
	private Map<String, Object> details;

	
	@DBRef
	private ObjectEntity parent;
	
	public ObjectEntity() {};
	
	public ObjectEntity(String objectId, String type, String alias, String status, boolean active,
			Date creationTimestamp, String createdBy, Map<String, Object> details) {
		super();
		this.objectId = objectId;
		this.type = type;
		this.alias = alias;
		this.status = status;
		this.active = active;
		this.creationTimestamp = creationTimestamp;
		this.createdBy = createdBy;
		this.details = details;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

	public ObjectEntity getParent() {
		return parent;
	}

	public void setParent(ObjectEntity parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "ObjectEntity {objectId: " + objectId + ", type: " + type + ", alias: " + alias + ", status: " + status
				+ ", active: " + active + ", creationTimestamp: " + creationTimestamp + ", createdBy: " + createdBy
				+ ", details: " + details + "}";
	}
	
	
	
	
}
