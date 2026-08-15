package server.services;

import java.util.Map;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserUpdateDto;

public interface UserService {
	public UserDto signUpUser(String uid);
	public UserDto authenticateByName(UserAuthenticateDto userAuthDto);
	public UserDto getUserByUid(String uid);
	public UserDto updateUserData(String id,UserUpdateDto userToUpdate);
    public UserDto updateUserPreferences(String uid, Map<String, Float> genrePreferences);
    public Map<String, Float> getUserPreferences(String uid);
    public String getUserSummaryPrompt(String uid);
    public String updateSummaryPromptFromFeedback(String uid, String feedback);

/////
/// 
/// 

	public void deleteAllUsers();
}
