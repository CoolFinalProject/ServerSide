package server.services;

import java.util.Map;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserUpdateDto;

public interface UserService {
	public UserDto signUpUser(String token);
	public UserDto authenticateByName(UserAuthenticateDto userAuthDto);
	public UserDto getUserFromToken(String token);
	public UserDto updateUserData(String id,UserUpdateDto userToUpdate);
    public UserDto updateUserPreferences(String token, Map<String, Float> genrePreferences);
    public Map<String, Float> getUserPreferences(String token);

/////
/// 
/// 

	public void deleteAllUsers();
}
