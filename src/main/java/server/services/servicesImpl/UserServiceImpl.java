package server.services.servicesImpl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserTokenDto;
import server.convertions.UserConvertion;
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
	public String authenticateByName(UserAuthenticateDto userAuthDto) 
	{	
		throw new server.excptions.UnsupportedOperationException("authenticateByName() "+userAuthDto.toString());
	}

	@Override
	public UserDto getUserFromToken(UserTokenDto userTokenDto) 
	{
		throw new server.excptions.UnsupportedOperationException("getUserFromToken() "+ userTokenDto.toString());
	}

	@Override
	public UserDto updateUserData(String id, UserDto userToUpdate) {
		throw new server.excptions.UnsupportedOperationException("Unimplemented method 'updateUserData'");
	}

	@Override
	public UserDto signUpUser(UserAuthenticateDto newUser) {
		UserDto user=new UserDto();
		user.setUserName(newUser.getUserName());
		user.setPassWord(newUser.getPassWord());
		user.setUserRole(UserRole.END_USER);
		user.setUserId(UUID.randomUUID().toString());  
		// by default active = true
		userRep.save(UserConvertion.userDtoToEntity(user));
		return user;
	}

}
