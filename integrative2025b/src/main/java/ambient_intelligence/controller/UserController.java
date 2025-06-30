package ambient_intelligence.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ambient_intelligence.boundary.NewUserBoundary;
import ambient_intelligence.boundary.UserBoundary;
import ambient_intelligence.logic.UserService;


//// USERS RELATED API COMMANDS:
/// -- according to the specs 3 functions required - 
///Create new user
///Login valid user and retrieve details
///Update user details







@RestController
@RequestMapping(path = {"/ambient-intelligence/user"})
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		// TODO Auto-generated constructor stub
		this.userService=userService;
	}

	
	@PostMapping( consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	
	/// gets Json of user and returns json of user
	///
	///
	public UserBoundary createNewUser(@RequestBody NewUserBoundary user) 
	{
		System.err.println("*** createNewUser(" + user + ")");
		UserBoundary returned = this.userService.createNewUser(user);
		return returned;
	}
	
	@GetMapping(  path = {"/{systemId}/{email}"}  , produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary getSpecificUser( @PathVariable("systemId") String systemId, @PathVariable("email") String email ) {
		String userId = UserService.genId(systemId, email);
		System.err.println("*** getUser(" + userId+")");
		
		return this.userService.getUser(userId);
	}
	
	@PutMapping(  path = {"/{systemId}/{email}"}  ,  consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateSepecificUser(
				@PathVariable("systemId") String systemId, 
				@PathVariable("email") String email, 
				@RequestBody UserBoundary boundaryForUpdate) 
	{
		String userId = UserService.genId(systemId, email);
		System.err.println("*** userService.update(" + userId + ", " + boundaryForUpdate + ")");
		this.userService.update(userId,boundaryForUpdate);
	}
	
	
	

	
}
