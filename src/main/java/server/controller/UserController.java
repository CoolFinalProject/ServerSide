package server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.DTO.UserDto.UserAuthenticateDto;
import server.services.UserService;


@RestController
@RequestMapping(path = {"/Users"})
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		// TODO Auto-generated constructor stub
		this.userService=userService;
	}
	@PostMapping(path = "/AuthenticateByName",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
	public String authenticateByName(@RequestBody UserAuthenticateDto userAuthDto)
	{
		return userService.authenticateByName(userAuthDto);
	}
	
	
}
