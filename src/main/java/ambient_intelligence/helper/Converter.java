package ambient_intelligence.helper;

import ambient_intelligence.boundary.CommandBoundary;
import ambient_intelligence.boundary.ObjectBoundary;
import ambient_intelligence.boundary.UserBoundary;
import ambient_intelligence.data.CommandEntity;
import ambient_intelligence.data.ObjectEntity;
import ambient_intelligence.data.UserEntity;

public class Converter {

	
	


	public static CommandEntity CommandtoEntity(CommandBoundary commandBoundary) {
		CommandEntity ce=new CommandEntity();
		
		ce.setCommand(commandBoundary.getCommand());
		ce.setCommandId(commandBoundary.getCommandId().getSystemId()+"_"+commandBoundary.getCommandId().getId());
		ce.setTargetObject(commandBoundary.getTargetObject().getSystemId()+"_"+commandBoundary.getTargetObject().getId());
		ce.setDetails(commandBoundary.getCommandAttributes());
		ce.setInvokedBy(commandBoundary.getInvokedBy().getUserId().getSystemId()+"_"+commandBoundary.getInvokedBy().getUserId().getEmail());
		ce.setInvocationTimeStamp(commandBoundary.getInvocationTimeStamp());
		
		return ce;
	}
	public static CommandBoundary commandToBoundary(CommandEntity commandEntity)
	{
		CommandBoundary cb=new CommandBoundary();
		cb.setCommand(commandEntity.getCommand());
		
		String[] commandDiv = commandEntity.getCommandId().split("_");
		String[] objectDiv = commandEntity.getTargetObject().split("_");
		String[] userDiv = commandEntity.getInvokedBy().split("_");
		
		cb.setCommandId(new CommandId(commandDiv[1],commandDiv[0]));
		cb.setTargetObject(new ObjectId(objectDiv[1],objectDiv[0]));
		cb.setCommandAttributes(commandEntity.getDetails());
		cb.setInvokedBy(new CreatedBy(new UserId(userDiv[1],userDiv[0])));
		cb.setInvocationTimeStamp(commandEntity.getInvocationTimeStamp());
		return cb;
	}
	
	
	
// ------------------- USER CONVERTER
	
    public static UserEntity userToEntity(UserBoundary boundary) {
        if (boundary == null) {
            return null;
        }

        String userId = 
        		boundary.getUserId().getEmail() + "_" + 
        		boundary.getUserId().getSystemId();

        return new UserEntity(
                userId,
                boundary.getUsername(),
                boundary.getAvatar(),
                boundary.getRole(),
                boundary.getAvatar()
        );
    }

    public static UserBoundary userToBoundary(UserEntity entity) {
        if (entity == null){
            return null;
        }
        String[] div = entity.getuserId().split("_");
        
        return new UserBoundary(
                new UserId(div[1], div[0]),
                entity.getRole(),
                entity.getUsername(),
                entity.getAvatar()
        );
    }

    
// ---------------------- ObjectConverter
    
    public static ObjectEntity objectToEntity(ObjectBoundary boundary) {
    	if (boundary == null)
    		return null;
    		
        ObjectEntity entity = new ObjectEntity();
        entity.setObjectId(
        		boundary.getObjectId().getSystemId()+
        		"_"+
        		boundary.getObjectId().getId()); // Convert ObjectId to String
        entity.setType(boundary.getType());
        entity.setAlias(boundary.getAlias());
        entity.setStatus(boundary.getStatus());
        entity.setActive(boundary.isActive());
        entity.setCreationTimestamp(boundary.getCreationTimestamp());
        entity.setCreatedBy(
        		boundary.getCreatedBy().getUserId().getSystemId()+
        		"_"+
        		boundary.getCreatedBy().getUserId().getEmail()); // Example: Extract email from CreatedBy
        entity.setDetails(boundary.getDetails());
        return entity;
    }

    
    public static ObjectBoundary objectToBoundary(ObjectEntity entity) {
        if (entity == null) {
            return null;
        }

        ObjectBoundary boundary = new ObjectBoundary();
        
        String[] objectDiv = entity.getObjectId().split("_"); /// objectId is second system id is first
        String[] createdDiv = entity.getCreatedBy().split("_");
        
        boundary.setObjectId(new ObjectId(objectDiv[1],objectDiv[0]));
        boundary.setType(entity.getType());
        boundary.setAlias(entity.getAlias());
        boundary.setStatus(entity.getStatus());
        boundary.setActive(entity.isActive());
        boundary.setCreationTimestamp(entity.getCreationTimestamp());
        boundary.setCreatedBy(new CreatedBy(new UserId(createdDiv[1],createdDiv[0])));
        boundary.setDetails(entity.getDetails());
        
        return boundary;

    }
    
    
}
