package ambient_intelligence.helper;


public class CommandId {
	private String commandId;
	private	String systemId;
	
	public CommandId() {};
	public CommandId(String commandId, String systemId) {
		super();
		this.commandId = commandId;
		this.systemId = systemId;
	}
	public String getId() {
		return commandId;
	}
	public void setId(String id) {
		this.commandId = id;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	@Override
	public String toString() {
		return "CommandId {commandId: " + commandId + ", systemId: " + systemId + "}";
	}
	
}
