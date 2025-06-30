package ambient_intelligence.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ambient_intelligence.boundary.NewObjectBoundary;
import ambient_intelligence.boundary.ObjectBoundary;
import ambient_intelligence.boundary.ObjectChildIdBoundary;
import ambient_intelligence.logic.Impl.ObjectServiceImpl;


@RestController
@RequestMapping(path = {"/ambient-intelligence/objects"})
public class ObjectController {

	
	  private ObjectServiceImpl objectService;
	    
	  public ObjectController(ObjectServiceImpl osi)
	  {
		  this.objectService=osi;
	  }

	    
	  @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE  ,  produces = MediaType.APPLICATION_JSON_VALUE)
	  public ObjectBoundary createNewObject(
			  @RequestParam(name = "userSystemID", required = false, defaultValue = "") String userSystemId,
	          @RequestParam(name = "userEmail", required = false, defaultValue = "") String email,
			  @RequestBody NewObjectBoundary newObject) {
		System.err.println("*** createNewObject(" + newObject + ")");
	       return this.objectService.createNewObject(userSystemId,email,newObject);
	  }
	    
	  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	  public ObjectBoundary[] getAllObjects(
			  @RequestParam(name = "userSystemID", required = false, defaultValue = "") String userSystemId,
	          @RequestParam(name = "userEmail", required = false, defaultValue = "") String email,
	          @RequestParam(name = "size", required = false, defaultValue = "7") int size,
	          @RequestParam(name = "page", required = false, defaultValue = "0") int page
			  ) {
		System.err.println("getAllObjects()");
	    return this.objectService.getAllObjects(userSystemId,email,size,page).toArray(new ObjectBoundary[0]);
	  }

	  @GetMapping( path = "/{systemID}/{objectId}", produces = MediaType.APPLICATION_JSON_VALUE)
	  public ObjectBoundary getSpecificObject(
			  @PathVariable("systemID") String systemId,
			  @PathVariable("objectId") String objectId,
	          @RequestParam(name = "userSystemID", required = false, defaultValue = "") String userSystemId,
	          @RequestParam(name = "userEmail", required = false, defaultValue = "") String email) {
		  return this.objectService.getSpecificObject(userSystemId,email,systemId,objectId);
	  }
	    
	  @PutMapping( path = "/{systemID}/{objectId}"  , consumes = MediaType.APPLICATION_JSON_VALUE)
	  public void updateObject(
			  @PathVariable("systemID") String objectSystemId
			  ,@PathVariable("objectId") String objectId,
	          @RequestBody ObjectBoundary object,
	          @RequestParam(name = "userSystemID", required = false, defaultValue = "") String userSystemId,
	          @RequestParam(name = "userEmail", required = false, defaultValue = "") String email) {
	    System.err.println("*** updateObject(" +objectSystemId+","+ objectId + ", " + object + ")");
	    this.objectService.update(userSystemId,email,objectSystemId,objectId, object);
	  }
//

}
