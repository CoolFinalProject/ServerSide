package server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserTokenDto;
import server.services.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(path = {"/users"})
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	@PostMapping(path = "/auth/byName",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
	public String authenticateByName(@RequestBody UserAuthenticateDto userAuthDto)
	{
		return userService.authenticateByName(userAuthDto);
	}
	@PutMapping(path = "updateUser/{id}")
	public UserDto updateUserData(@PathVariable String id, @RequestBody UserDto entity) {
		return entity;
	}
///
///-------------NOTICE!!!!!!!!!!!!!!!!!-----------------
/// authentication with tokens should not be implemented this way!
///	tokens should be send in the header -- will be implemented later on when authentication will
/// get reworked and done properly in the future
///
///
	@GetMapping(path="/auth/{token}",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto authenticateByToken(@PathVariable("token") String token)
	{
		return userService.getUserFromToken(new UserTokenDto(token));
	}
	
}
