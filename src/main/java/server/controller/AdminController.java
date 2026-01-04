package server.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.services.UserService;

///  ADMIN CONTROLER API -- 
/// according to specs suppose to have 5 function but because we don't have commands(?) we will have 3
/// Delete all users in system
/// Delete all objects in system
///	Get all users in system
///
///as for get all objects(SleepSessions) it is already in its controller




@RestController
@RequestMapping(path = {"admin"})
public class AdminController {
    UserService userService;

    public AdminController(UserService userService)
    {
        this.userService=userService;
    }
    @DeleteMapping(path="deleteAllUsers")
    public void deleteAllUsers()
    {
        userService.deleteAllUsers();
    }
}
