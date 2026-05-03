package server.services;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserTokenDto;
import server.DTO.UserDto.UserUpdateDto;

import java.util.Map;

public interface UserService {
	public UserDto signUpUser(UserAuthenticateDto newUser);
	public UserDto authenticateByName(UserAuthenticateDto userAuthDto);
	public UserDto getUserFromToken(UserTokenDto userTokenDto);
	public UserDto updateUserData(String id,UserUpdateDto userToUpdate);
    public UserDto updateUserPreferences(String id, Map<String, Float> genrePreferences);
    public Map<String, Float> getUserPreferences(String id);

/////
/// 
/// 

	public void deleteAllUsers();
}
