package server.services;

import server.DTO.UserDto.UserAuthenticateDto;


public interface UserService {
	public String AuthenticateByName(UserAuthenticateDto userAuthDto);
}
