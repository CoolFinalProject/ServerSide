package server.services;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserTokenDto;


public interface UserService {
	public String authenticateByName(UserAuthenticateDto userAuthDto);
	public UserDto getUserFromToken(UserTokenDto userTokenDto);
	public UserDto updateUserData(String id,UserDto userToUpdate);
}
