package ambient_intelligence.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ambient_intelligence.boundary.CommandBoundary;
import ambient_intelligence.boundary.UserBoundary;
import ambient_intelligence.logic.CommandsService;
import ambient_intelligence.logic.ObjectService;
import ambient_intelligence.logic.UserService;


///  ADMIN CONTROLER API -- 
/// according to specs suppose to have 5 function but because we don't have commands(?) we will have 3
/// Delete all users in system
/// Delete all objects in system
///	Get all users in system
///
///as for get all objects(SleepSessions) it is already in its controller




@RestController
@RequestMapping(path = {"/ambient-intelligence/admin"})
public class AdminController {

	private final UserService ul;
	
	private final CommandsService cl;
	private final ObjectService ol;
	
	public AdminController(UserService ul,ObjectService ol, CommandsService cl) 
	{
		this.ul=ul;
		this.ol=ol;
		this.cl=cl;
	}
	
	
/// 
///     Next functions are patients Functions
///
	@GetMapping(path = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] getAllUsers(
			@RequestParam(name = "userSystemID", required = false, defaultValue = "") String systemId,
			@RequestParam(name = "userEmail", required = false, defaultValue = "") String email,
			@RequestParam(name = "size", required = false, defaultValue = "7") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page
			) 
	{
		System.err.println("getAllUsers()");
	    return this.ul.getAll(systemId,email,size,page).toArray(new UserBoundary[0]);
	}
 
	@DeleteMapping(path = "/users")
	public void deleteAllUsers(
			@RequestParam(name = "userSystemID", required = false, defaultValue = "") String systemId,
			@RequestParam(name = "userEmail", required = false, defaultValue = "") String email) {
		System.err.println("*** deleteAllUsers()");
		this.ul.deleteAll(systemId,email);
	}
	
	
///
/// ------------------------------------------------------------------------------------------
/// END patients functions
///
///	SleepSession functions are next
	
	
	
	@DeleteMapping(path = "/objects")
	public void deleteAllObjects(
			@RequestParam(name = "userSystemID", required = false, defaultValue = "") String systemId
			, @RequestParam(name = "userEmail", required = false, defaultValue = "") String email
			) {
		
		System.err.println("*** deleteAllObjects()");
		this.ol.deleteAll(systemId, email);
	}
	
	
///
/// -----------------------------------------------------------------------------------------------------------
///
///	End SleepSession functions
///
///	Commands functions are next
///

    // DELETE all commands
    @DeleteMapping("/commands")
    public void deleteAllCommands(
    		@RequestParam(name = "userSystemID", required = false, defaultValue = "") String systemId,
			@RequestParam(name = "userEmail", required = false, defaultValue = "") String email) {
    	System.err.println("*** deleteAllCommands()");
        cl.deleteAllCommands(systemId,email);
    }
    
    

    // GET all commands with pagination
    @GetMapping(path = "/commands", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CommandBoundary> getAllCommands(
    		@RequestParam(name = "userSystemID", required = false, defaultValue = "") String systemId,
			@RequestParam(name = "userEmail", required = false, defaultValue = "") String email
    		) {
    	return cl.getAllCommands(systemId,email);
       
    }
	
}
