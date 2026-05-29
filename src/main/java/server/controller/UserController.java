package server.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserUpdateDto;
import server.services.UserService;


@RestController
@RequestMapping(path = {"/users"})
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}



	@PostMapping(path = "/newUser",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto signUpUser(@RequestHeader(name="Authorization",required=false) String header)
	{
		String token = header.replace("Bearer ", "");
		return userService.signUpUser(token);
	}

	@PostMapping(path = "/auth/byName",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@Deprecated
	public UserDto authenticateByName(@RequestBody UserAuthenticateDto userAuthDto)
	{
		return userService.authenticateByName(userAuthDto);
	}
	@PutMapping(path = "updateUser/{id}")
	@Deprecated
	public UserDto updateUserData(@PathVariable("id") String id, @RequestBody UserUpdateDto entity) {
		return userService.updateUserData(id, entity);
	}
    @PutMapping(
            path = "/preferences",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto updateUserPreferences(
            @RequestHeader(name="Authorization",required=false) String header,
            @RequestBody Map<String, Float> genrePreferences
    ) {
		String token = header.replace("Bearer ", "");
        return userService.updateUserPreferences(token, genrePreferences);
    }
///
///-------------NOTICE!!!!!!!!!!!!!!!!!-----------------
/// authentication with tokens should not be implemented this way!
///	tokens should be send in the header -- will be implemented later on when authentication will
/// get reworked and done properly in the future
///
///
	@GetMapping(path="/authByToken",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto authenticateByToken(@RequestHeader(name="Authorization",required=false) String header)
	{
		String token = header.replace("Bearer ", "");
		return userService.getUserFromToken(token);
	}
    @GetMapping(
            path = "/preferences",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Float> getUserPreferences(@RequestHeader(name="Authorization",required=false) String header) {
		String token = header.replace("Bearer ", "");
        return userService.getUserPreferences(token);
    }
}
