package ambient_intelligence.boundary;

import java.util.Date;
import java.util.Map;

import ambient_intelligence.helper.CommandId;
import ambient_intelligence.helper.CreatedBy;
import ambient_intelligence.helper.ObjectId;

public class NewCommandBoundary {
	private String command;
	
	private ObjectId targetObject;
// sessionId
	private Date invocationTimeStamp;
	private CreatedBy invokedBy; // userId 
	private Map<String, Object> commandAttributes;
	public NewCommandBoundary() {}
	
	   public NewCommandBoundary(ObjectId targetObject, CreatedBy invokedBy, Date invocationTimeStamp, String command, Map<String, Object> details) {
			super();
			this.targetObject = targetObject;
			this.invokedBy = invokedBy;
			this.invocationTimeStamp = invocationTimeStamp;
			this.command = command;
			this.commandAttributes = details;
		}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public ObjectId getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(ObjectId targetObject) {
		this.targetObject = targetObject;
	}

	public Date getInvocationTimeStamp() {
		return invocationTimeStamp;
	}

	public void setInvocationTimeStamp(Date invocationTimeStamp) {
		this.invocationTimeStamp = invocationTimeStamp;
	}

	public CreatedBy getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(CreatedBy invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getCommandAttributes() {
		return commandAttributes;
	}

	public void setCommandAttributes(Map<String, Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
	}

	@Override
	public String toString() {
		return "NewCommandBoundary [command=" + command + ", targetObject=" + targetObject + ", invocationTimeStamp="
				+ invocationTimeStamp + ", invokedBy=" + invokedBy + ", commandAttributes=" + commandAttributes + "]";
	}
	
	
}
