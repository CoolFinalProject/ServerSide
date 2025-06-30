package ambient_intelligence.boundary;

import java.util.Date;
import java.util.Map;

import ambient_intelligence.helper.CommandId;
import ambient_intelligence.helper.CreatedBy;
import ambient_intelligence.helper.ObjectId;

public class CommandBoundary {

	
	private CommandId commandId;
	private String command;
	
	private ObjectId targetObject;
// sessionId
	private Date invocationTimeStamp;
	private CreatedBy invokedBy; // userId 
   
   
    

    private Map<String, Object> commandAttributes;

    // Default Constructor
    public CommandBoundary() {}

  

    public CommandBoundary(CommandId commandId, ObjectId targetObject, CreatedBy invokedBy, Date invocationTimeStamp, String command, Map<String, Object> details) {
		super();
		this.commandId = commandId;
		this.targetObject = targetObject;
		this.invokedBy = invokedBy;
		this.invocationTimeStamp = invocationTimeStamp;
		this.command = command;
		this.commandAttributes = details;
	}




    

	public CommandId getCommandId() {
		return commandId;
	}



	public void setCommandId(CommandId commandId) {
		this.commandId = commandId;
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
		return "CommandBoundary {commandId: " + commandId + ", targetObject: " + targetObject + ", invokedBy: " + invokedBy
				+ ", invocationTimeStamp: " + invocationTimeStamp + ", command: " + command + ", details: " + commandAttributes + "}";
	}



	

}
