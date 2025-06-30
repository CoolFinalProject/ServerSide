package ambient_intelligence.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ambient_intelligence.boundary.ObjectBoundary;
import ambient_intelligence.boundary.ObjectChildIdBoundary;
import ambient_intelligence.logic.Impl.ObjectServiceImpl;

@RestController
@RequestMapping(path = {"/ambient-intelligence/objects"})
public class ObjectRelationController {

	
	  private ObjectServiceImpl objectService;
	    
	  public ObjectRelationController(ObjectServiceImpl osi)
	  {
		  this.objectService=osi;
	  }

	  
	  
	  
	  
	  
	  @PutMapping( path = "/{parentSystemID}/{parentObjectId}/children"  , consumes = MediaType.APPLICATION_JSON_VALUE)
	  public void bindObjects(
			  @PathVariable("parentSystemID") String parentSystemId
			  ,@PathVariable("parentObjectId") String parentObjectId,
			  @RequestParam(name = "userSystemID", required = false, defaultValue = "") String userSystemId,
	          @RequestParam(name = "userEmail", required = false, defaultValue = "") String email,
	          @RequestBody ObjectChildIdBoundary object) 
	  {
		  objectService.bindObjects(userSystemId,email,parentSystemId, parentObjectId, object);
	  }
	  
	  @GetMapping( path = "/{parentSystemID}/{parentObjectId}/children",produces = MediaType.APPLICATION_JSON_VALUE)
	  public ObjectBoundary[] getAllChildren(
			  @PathVariable("parentSystemID") String parentSystemId,
			  @PathVariable("parentObjectId") String parentObjectId,
			  @RequestParam(name = "userSystemID", required = false, defaultValue = "") String userSystemId,
	          @RequestParam(name = "userEmail", required = false, defaultValue = "") String email,
			  @RequestParam(name = "size", required = false, defaultValue = "7") int size,
	          @RequestParam(name = "page", required = false, defaultValue = "0") int page
			  ) {
		System.err.println("getAllObjects(String,String,String,String,int,int)");
	    return this.objectService.getAllChildren(userSystemId,email,parentSystemId,parentObjectId,size,page).toArray(new ObjectBoundary[0]);
	  }
	  
	  @GetMapping( path = "/{childSystemID}/{childObjectId}/parents",produces = MediaType.APPLICATION_JSON_VALUE)
	  public ObjectBoundary getParent(
			  @PathVariable("childSystemID") String parentSystemId
			  ,@PathVariable("childObjectId") String parentObjectId
			  ) {
		System.err.println("getParent()");
	    return this.objectService.getParent(parentSystemId,parentObjectId);
	  }
}
