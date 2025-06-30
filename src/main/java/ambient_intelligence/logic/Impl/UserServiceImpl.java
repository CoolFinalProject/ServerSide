package ambient_intelligence.logic.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ambient_intelligence.boundary.NewUserBoundary;
import ambient_intelligence.boundary.UserBoundary;
import ambient_intelligence.crud.UserCrud;
import ambient_intelligence.data.UserEntity;
import ambient_intelligence.data.UserRole;
import ambient_intelligence.excptions.AlreadyInUseException;
import ambient_intelligence.excptions.BadRequestException;
import ambient_intelligence.excptions.InvalidInputException;
import ambient_intelligence.helper.Converter;
import ambient_intelligence.logic.UserService;

@Service
public class UserServiceImpl implements UserService{
	private UserCrud userCrud;

    private String systemId; 
	
	
	public UserServiceImpl(UserCrud userCrud) {
		this.userCrud = userCrud;
	}
	
	@Override
	public UserBoundary createNewUser(NewUserBoundary user) {
		if(!UserBoundary.isValidEmail(user.getEmail()))
		{
			throw new InvalidInputException("Email must be in a valid format, e.g., myemail@example.org");
		}

		Optional<UserEntity> existing = this.userCrud.findById(UserService.genId(systemId, user.getEmail()));
		if (!existing.isEmpty())
		{
			throw new AlreadyInUseException("Email already in use!");
		}
		
		if (user.getRole()==null)
			throw new InvalidInputException("Role cannot be null!");
		if(user.getAvatar() ==null || user.getAvatar().isBlank())
			throw new InvalidInputException("Avatar cannot be empty!");
		if(user.getUsername()==null || user.getUsername().isBlank())
			throw new InvalidInputException("User name cannot be empty");
		
		UserEntity entity = new UserEntity
				(
				user.getEmail(),
				user.getUsername(),
				UserService.genId(systemId, user.getEmail()),
				user.getRole(),
				user.getAvatar()
				);
		entity = this.userCrud.save(entity);
		return Converter.userToBoundary(entity);
	}
	
	
	@Value("${spring.application.name}")
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary getUser(String userId) {
		UserEntity entity = this.userCrud.findById(userId).orElseThrow(() -> new ambient_intelligence.excptions.NotFoundException("user has not been found!"));
		
		return Converter.userToBoundary(entity);
	}

	@Override
	public void update(String userId,UserBoundary boundaryForUpdate) 
	{
		UserEntity existing = this.userCrud.findById(userId).orElseThrow(()->new ambient_intelligence.excptions.NotFoundException("no message to update by id: " + userId));
/// Mail && Id field would not change so we wont even check that field
/// so we need to check for Age Name and Role changes only!
		
		
		
/// ------------ AGE CHECK
		
		
		if (!boundaryForUpdate.getAvatar().isBlank())
		{
			existing.setAvatar(boundaryForUpdate.getAvatar());
		}

/// ------------- NAME CHECK		
		
		if(!boundaryForUpdate.getUsername().isBlank())
		{
			existing.setUsername(boundaryForUpdate.getUsername());
		}
		// if not name found we change nothing
		
///  ------------- ROLE CHECK
		if (boundaryForUpdate.getRole()!=null)
		{
			existing.setRole(boundaryForUpdate.getRole());
		}// else change nothing
		
		this.userCrud.save(existing);
	}
	
	@Deprecated
	@Override
	public void deleteAll() {
		throw new BadRequestException("function deleteAll() is deprecated");	
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteAll(String systemId,String email)
	{
		UserBoundary ub=getUser(systemId+"_"+email);
		if (ub.getRole()!=UserRole.ADMIN)
		{
			throw new BadRequestException("Authorization failed!");
		}
		this.userCrud.deleteAll();
		
	}
	
	
	@Override
	@Deprecated
	public List<UserBoundary> getAll() {
		throw new BadRequestException("function getAll() is deprecated");
		 
	}
	@Override
	@Transactional(readOnly =true)
	public List<UserBoundary> getAll(String SystemId,String email,int size,int page)
	{
		UserBoundary ub=getUser(systemId+"_"+email);
		if (ub.getRole()!=UserRole.ADMIN)
		{
			throw new BadRequestException("Authorization failed!");
		}
        return 		
    			this.userCrud
    			.findAll(
    				PageRequest.of(page, size))
    			.stream() // Stream<Entities>
    			.map(Converter::userToBoundary) // Stream<Boundary>
    			.toList(); // List<Boundary>
	}
	

}
