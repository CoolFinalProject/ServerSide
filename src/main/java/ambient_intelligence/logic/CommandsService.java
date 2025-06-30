package ambient_intelligence.logic;

import java.util.List;

import ambient_intelligence.boundary.CommandBoundary;
import ambient_intelligence.boundary.NewCommandBoundary;



public interface CommandsService {
	
	@Deprecated
	public List<CommandBoundary> getAllCommands();
	public List<CommandBoundary> getAllCommands(String systemId,String email);
	
	@Deprecated
	public void deleteAllCommands();
	public void deleteAllCommands(String systemId,String email);
	public List<Object> invokeCommand(NewCommandBoundary commandBoundary);
	
	
}
