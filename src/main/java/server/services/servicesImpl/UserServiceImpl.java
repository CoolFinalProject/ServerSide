package server.services.servicesImpl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserTokenDto;
import server.DTO.UserDto.UserUpdateDto;
import server.convertions.UserConvertion;
import server.entities.UserEntities.UserEntity;
import server.enums.UserRole;
import server.repositories.UserRepository;
import server.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	UserRepository userRep;

	public UserServiceImpl(UserRepository userRep)
	{
		this.userRep=userRep;
	}

	@Override
	public UserDto authenticateByName(UserAuthenticateDto userAuthDto) 
	{	
		Optional<UserEntity> entity=userRep.findByUserNameAndPassWord(userAuthDto.getUserName(), userAuthDto.getPassWord());
		if (entity.isEmpty())
			throw new server.excptions.NotFoundException("One or two fields are incorrect!");
		else
			return UserConvertion.userEntityToDto(entity.get());
	}

	@Override
	public UserDto getUserFromToken(UserTokenDto userTokenDto) 
	{
		throw new server.excptions.UnsupportedOperationException("getUserFromToken() "+ userTokenDto.toString());
	}

	@Override
	public UserDto updateUserData(String id, UserUpdateDto userToUpdate) 
	{
		UserEntity entity=userRep.findById(id).orElseThrow(()-> new server.excptions.NotFoundException("User "+id+" does not exist!"));

		if (!userToUpdate.getUserName().isBlank())
			entity.setUserName(userToUpdate.getUserName());
		if (!userToUpdate.getPassWord().isBlank())
			entity.setPassWord(userToUpdate.getPassWord());
		entity.setActive(userToUpdate.isActive());
		entity.setUserRole(userToUpdate.getUserRole());
		userRep.save(entity);
		return UserConvertion.userEntityToDto(entity);
	}

	@Override
	public UserDto signUpUser(UserAuthenticateDto newUser) {
		UserDto user=new UserDto();
		user.setUserName(newUser.getUserName());
		user.setPassWord(newUser.getPassWord());
		user.setUserRole(UserRole.END_USER);
		user.setUserId(UUID.randomUUID().toString());  
		user.setCreationTime(new Date());
		// by default active = true
		userRep.save(UserConvertion.userDtoToEntity(user));
		return user;
	}

	@Override
	public void deleteAllUsers() {
		userRep.deleteAll();
	}

}
