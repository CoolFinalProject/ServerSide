package ambient_intelligence.logic.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ambient_intelligence.boundary.CommandBoundary;
import ambient_intelligence.boundary.NewCommandBoundary;
import ambient_intelligence.boundary.ObjectBoundary;
import ambient_intelligence.boundary.UserBoundary;
import ambient_intelligence.crud.CommandCrud;
import ambient_intelligence.data.CommandEntity;
import ambient_intelligence.data.UserRole;
import ambient_intelligence.excptions.BadRequestException;
import ambient_intelligence.excptions.InvalidInputException;
import ambient_intelligence.helper.CommandId;
import ambient_intelligence.helper.Converter;
import ambient_intelligence.logic.CommandsService;
import ambient_intelligence.logic.ObjectService;
import ambient_intelligence.logic.UserService;
import ambient_intelligence.SleepSession.SleepScoringUtils;

@Service
public class CommandServiceImpl implements CommandsService{
	private UserService us;
	private ObjectService os;
	private CommandCrud commandCrud;
	private String systemId;
	public CommandServiceImpl(CommandCrud commandCrud,UserService us, ObjectService os)
	{
		this.commandCrud=commandCrud;
		this.us=us;
		this.os = os;
	}
	
	
	 	@Override
	    @Transactional
	    public List<Object> invokeCommand(NewCommandBoundary commandBoundary) {    	
		 	if ((commandBoundary.getCommand() == null) || (commandBoundary.getCommand().isBlank())) {
		 		throw new InvalidInputException("Command must not be null");
		 	}
		 	if ((commandBoundary.getTargetObject() == null)) {
		 		throw new InvalidInputException("Target object must not be null");
		 	}
		 	if ((commandBoundary.getInvocationTimeStamp() == null)) {
		 		throw new InvalidInputException("Invocation Time Stamp must not be null");
		 	}
		 	if (commandBoundary.getInvokedBy() == null) {
		 		throw new InvalidInputException("There must be a user that invoked the command");
		 	}
		 	
	       // check for authorization
	        commandBoundary.setInvocationTimeStamp(new Date());
	      
	        CommandEntity entity = new CommandEntity(systemId + "_" + UUID.randomUUID().toString(), 
	        		commandBoundary.getCommand(), 
	        		commandBoundary.getTargetObject().getSystemId()+ "_" + commandBoundary.getTargetObject().getId(),
	        		commandBoundary.getInvocationTimeStamp(),
	        		commandBoundary.getInvokedBy().getUserId().getSystemId() + "_" + commandBoundary.getInvokedBy().getUserId().getEmail(),
	        		commandBoundary.getCommandAttributes());
	        
	        commandCrud.save(entity);
	        CommandBoundary com = Converter.commandToBoundary(entity);
	        
	        if (com.getCommand().equals("getQuality")) {
		 		//SleepScoringUtils.evaluateSleepSession();
	        	
		 		ObjectBoundary obj = os.getSpecificObject(
		 				com.getInvokedBy().getUserId().getSystemId(),
		 				com.getInvokedBy().getUserId().getEmail(),
		 				com.getTargetObject().getSystemId(), 
		 				com.getTargetObject().getId());
		 		int score;
		 		if (obj.getType().equals("Session"))
		 			
		 			if(com.getCommandAttributes().containsKey("-l"))
		 			{
		 				score = SleepScoringUtils.evaluateLight(obj.getDetails());
		 			} 
		 			else if(com.getCommandAttributes().containsKey("-v"))
		 				score=SleepScoringUtils.evaluateSound(obj.getDetails());
		 			else
		 				score =SleepScoringUtils.evaluateSleepSession(obj.getDetails());
		 		else
		 			throw new BadRequestException("targetObject is invalid!");
		 		
		 		return List.of(score);
		 		
		 		
		 		//calculate
		 	}
	        
	        
	        
	        return List.of(
	            Map.of(
	                "commandId", entity.getCommandId(),
	                "command", commandBoundary.getCommand(),
	                "targetId", commandBoundary.getTargetObject(),
	                "invokedDate", commandBoundary.getInvocationTimeStamp(),
	                "invokedBy",commandBoundary.getInvokedBy(),
	                "details", commandBoundary.getCommandAttributes()
	            )
	        );
	    }
	
	
	
	
	@Override
	@Deprecated
	public List<CommandBoundary> getAllCommands() {
		throw new BadRequestException("getAllCommands is deprecated!");
	}
	@Override
	@Transactional(readOnly = true)
	public List<CommandBoundary> getAllCommands(String systemId,String email)
	{
		UserBoundary ub=us.getUser(UserService.genId(systemId, email));
		if(ub.getRole()!=UserRole.ADMIN)
			throw new BadRequestException("Authorization failed!");
		
		List<CommandBoundary> list = new ArrayList<>();
        for (CommandEntity entity : this.commandCrud.findAll()) {
            list.add(Converter.commandToBoundary(entity));
        }
        return list;
	}

	@Deprecated
	@Override
	public void deleteAllCommands() {
		throw new BadRequestException("Authorization failed");
	}
	@Override
	public void deleteAllCommands(String systemId,String email) 
	{
		UserBoundary ub=us.getUser(UserService.genId(systemId, email));
		if (ub.getRole()!=UserRole.ADMIN)
			throw new BadRequestException("Authorization failed");
		commandCrud.deleteAll();
	}
	@Value("${spring.application.name}")
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
}
