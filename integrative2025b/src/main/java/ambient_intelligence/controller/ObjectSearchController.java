package ambient_intelligence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ambient_intelligence.boundary.ObjectBoundary;
import ambient_intelligence.logic.Impl.ObjectServiceImpl;

@RestController
@RequestMapping(path = {"/ambient-intelligence/objects/search"})
public class ObjectSearchController {
	private ObjectServiceImpl objectService;
	
	public ObjectSearchController(ObjectServiceImpl osi)
	{
		this.objectService =osi;
	}
	
	 @GetMapping(path ="/byAlias/{alias}")
	  public ObjectBoundary[] getAllByAlias(
			  @PathVariable("alias") String alias,
			  @RequestParam(name = "userSystemID",required = false, defaultValue = "") String userSystemId,
			  @RequestParam(name = "userEmail",required = false, defaultValue = "") String email,
			  @RequestParam(name = "size",required = false, defaultValue = "7") int size,
			  @RequestParam(name = "page",required = false, defaultValue = "0") int page
			  )
	  {
		  return this.objectService.getAllByAlias(userSystemId, email, alias, size, page).toArray(new ObjectBoundary[0]);
	  }
	@GetMapping(path = "/byType/{type}")
	public ObjectBoundary[] getAllByType(
			@PathVariable("type") String type,
			@RequestParam(name = "userSystemID",required = false, defaultValue = "") String userSystemId,
			@RequestParam(name = "userEmail",required = false, defaultValue = "") String email,
			@RequestParam(name = "size",required = false, defaultValue = "7") int size,
			@RequestParam(name = "page",required = false, defaultValue = "0") int page
			)
	{
		return objectService.getAllByType(userSystemId, email, type, size, page).toArray(new ObjectBoundary[0]);
		
	}
	@GetMapping(path = "/byType/{type}/{status}")
	public ObjectBoundary[] getAllByTypeAndStatus(
			@PathVariable("type") String type,
			@PathVariable("status") String status,
			@RequestParam(name = "userSystemID",required = false, defaultValue = "") String userSystemId,
			@RequestParam(name = "userEmail",required = false, defaultValue = "") String email,
			@RequestParam(name = "size",required = false, defaultValue = "7") int size,
			@RequestParam(name = "page",required = false, defaultValue = "0") int page
			)
	{
		return objectService.getAllByTypeAndStatus(userSystemId, email, type,status, size, page).toArray(new ObjectBoundary[0]);
		
	}
	
	  @GetMapping(path ="/search/byAliasPattern/{pattern}")
	  public ObjectBoundary[] getAllByAliasPattern(@PathVariable("pattern") String pattern,@RequestParam(name = "userSystemID",required = false, defaultValue = "") String userSystemId,
			  @RequestParam(name = "userEmail",required = false, defaultValue = "") String email,
			  @RequestParam(name = "size",required = false, defaultValue = "7") int size,
			  @RequestParam(name = "page",required = false, defaultValue = "0") int page) {
		  
		return this.objectService.getAllByAliasPattern(userSystemId, email, pattern, size, page).toArray(new ObjectBoundary[0]);
		  
	  }
}
