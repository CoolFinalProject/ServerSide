package server.services.servicesImpl;

import org.springframework.stereotype.Service;

import server.DTO.UserDto.UserAuthenticateDto;
import server.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public String AuthenticateByName(UserAuthenticateDto userAuthDto) 
	{	
		return "Authentication Request received : "+ userAuthDto.getUserName()+" , "+userAuthDto.getPassWord() ;
	}

}
