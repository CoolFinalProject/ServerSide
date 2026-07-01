package server.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.DTO.UserDto.UserDto;
import server.services.UserService;


@RestController
@RequestMapping(path = {"/users"})
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}

	@PostMapping(path = "/newUser",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto signUpUser(@RequestAttribute("firebaseUid") String uid) {
		return userService.signUpUser(uid);
	}


    @PutMapping(
            path = "/preferences",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto updateUserPreferences(
            @RequestAttribute("firebaseUid") String uid,
            @RequestBody Map<String, Float> genrePreferences
    ) {
        return userService.updateUserPreferences(uid, genrePreferences);
    }

	@GetMapping(path="/authByToken",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto authenticateByToken(@RequestAttribute("firebaseUid") String uid) {
		return userService.getUserByUid(uid);
	}

    @GetMapping(
            path = "/preferences",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Float> getUserPreferences(@RequestAttribute("firebaseUid") String uid) {
        return userService.getUserPreferences(uid);
    }

}
