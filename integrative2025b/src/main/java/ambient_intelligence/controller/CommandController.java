package ambient_intelligence.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import ambient_intelligence.boundary.CommandBoundary;
import ambient_intelligence.boundary.NewCommandBoundary;
import ambient_intelligence.logic.CommandsService;

import java.util.List;

@RestController
@RequestMapping("/ambient-intelligence/commands")
public class CommandController {

    private final CommandsService cl; // Use the interface instead of the implementation

    // Constructor to inject CommandsLogic
    public CommandController(CommandsService cl) {
        this.cl = cl; // Inject the service via constructor
    }

    // Invoke a new command
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE}, 
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Object> invokeCommand(@RequestBody NewCommandBoundary commandBoundary) 
    {
    	return cl.invokeCommand(commandBoundary);
     
    }
}


