package server.services.servicesImpl;

import org.springframework.stereotype.Service;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserTokenDto;
import server.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public String authenticateByName(UserAuthenticateDto userAuthDto) 
	{	
		throw new server.exceptions.UnsupportedOperationException("authenticateByName() "+userAuthDto.toString());
	}

	@Override
	public UserDto getUserFromToken(UserTokenDto userTokenDto) 
	{
		throw new server.exceptions.UnsupportedOperationException("getUserFromToken() "+ userTokenDto.toString());
	}

	@Override
	public UserDto updateUserData(String id, UserDto userToUpdate) {
		throw new server.exceptions.UnsupportedOperationException("Unimplemented method 'updateUserData'");
	}

}
