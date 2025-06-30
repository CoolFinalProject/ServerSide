package ambient_intelligence.helper;


// Will act as an Id for Object
public class ObjectId {
	
	
	private String objectId;
	private	String systemId;
	
	public ObjectId() {};
	public ObjectId(String objectId, String systemId) {
		super();
		this.objectId = objectId;
		this.systemId = systemId;
	}
	public String getId() {
		return objectId;
	}
	public void setId(String id) {
		this.objectId = id;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	@Override
	public String toString() {
		return "ObjectId {objectId: " + objectId + ", systemId: " + systemId + "}";
	}
	
	
}
