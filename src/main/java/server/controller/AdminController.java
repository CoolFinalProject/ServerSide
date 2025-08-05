package server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
