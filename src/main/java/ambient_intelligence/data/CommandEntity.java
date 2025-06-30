package ambient_intelligence.data;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="COMMANDS")
public class CommandEntity {

	
	@Id
	private String commandId;  // Will be generated similiar to SleepSession (?)
	private String command;
	
	 private String targetObject;
//sessionId
	private Date invocationTimeStamp;
	private String invokedBy; // userId 
	
    private Map<String, Object> details; // Command attributes stored as a JSON string in the DB

    
    
	public CommandEntity() {
		
	}



	public CommandEntity(String commandId, String command, String targetObject, Date invocationTimeStamp,
			String invokedBy, Map<String, Object> details) {
		super();
		this.commandId = commandId;
		this.command = command;
		this.targetObject = targetObject;
		this.invocationTimeStamp = invocationTimeStamp;
		this.invokedBy = invokedBy;
		this.details = details;
	}



	public Date getInvocationTimeStamp() {
		return invocationTimeStamp;
	}



	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public void setInvocationTimeStamp(Date invocationTimeStamp) {
		this.invocationTimeStamp = invocationTimeStamp;
	}


	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getInvokedBy() {
		return invokedBy;
	}



	public void setInvokedBy(String invokedBy) {
		this.invokedBy = invokedBy;
	}



	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

    
    
}
