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



	@PostMapping(path = "/newUser",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto signUpUser(@RequestBody UserAuthenticateDto userAuthDto)
	{
		return userService.signUpUser(userAuthDto);
	}

	@PostMapping(path = "/auth/byName",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto authenticateByName(@RequestBody UserAuthenticateDto userAuthDto)
	{
		return userService.authenticateByName(userAuthDto);
	}
	@PutMapping(path = "updateUser/{id}")
	public UserDto updateUserData(@PathVariable("id") String id, @RequestBody UserUpdateDto entity) {
		return userService.updateUserData(id, entity);
	}
    @PutMapping(
            path = "/{id}/preferences",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto updateUserPreferences(
            @PathVariable("id") String id,
            @RequestBody Map<String, Float> genrePreferences
    ) {
        return userService.updateUserPreferences(id, genrePreferences);
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
            path = "/{id}/preferences",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Float> getUserPreferences(@PathVariable("id") String id) {
        return userService.getUserPreferences(id);
    }
}
