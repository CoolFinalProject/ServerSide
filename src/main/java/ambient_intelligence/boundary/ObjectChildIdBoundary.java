package ambient_intelligence.boundary;

import ambient_intelligence.helper.ObjectId;

public class ObjectChildIdBoundary {
	private ObjectId childId;

	
	public ObjectChildIdBoundary() {};
	public ObjectChildIdBoundary(ObjectId childId) {
		super();
		this.childId = childId;
	}
	public ObjectId getChildId() {
		return childId;
	}
	public void setChildId(ObjectId childId) {
		this.childId = childId;
	}
	@Override
	public String toString() {
		return "ObjectChildIdBoundary {childId: " + childId + "}";
	}
	
}
