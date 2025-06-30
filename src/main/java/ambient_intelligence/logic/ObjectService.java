package ambient_intelligence.logic;

import java.util.List;
import java.util.Optional;

import ambient_intelligence.boundary.NewObjectBoundary;
import ambient_intelligence.boundary.ObjectBoundary;
import ambient_intelligence.boundary.ObjectChildIdBoundary;

public interface ObjectService {

	@Deprecated
	ObjectBoundary createNewObject(NewObjectBoundary object);
	ObjectBoundary createNewObject(String userSystemId,String userEmail,NewObjectBoundary object);
	
	List<ObjectBoundary> getAllByTypeAndStatus(String userSystemId,String userEmail,String type,String status,int size,int page);
	List<ObjectBoundary> getAllByType(String userSystemId,String userEmail,String type,int size,int page);
	List<ObjectBoundary> getAllByAlias(String userSystemId,String userEmail,String alias,int size,int page);
	public List<ObjectBoundary> getAllByAliasPattern(String userSystemId, String userEmail, String aliasPattern, int size, int page);
	
	@Deprecated
	List<ObjectBoundary> getAllChildren(String parentSystemId,String parentObjectId);
	List<ObjectBoundary> getAllChildren(String userSystemId,String userEmail,String parentSystemId,String parentObjectId,int size,int page);
	
	ObjectBoundary getParent(String childSystemId,String childObjectId);
	
	@Deprecated
	List<ObjectBoundary> getAllObjects();
	List<ObjectBoundary> getAllObjects(String userSystemId,String email,int size,int page);
	
	@Deprecated
	ObjectBoundary getSpecificObject(String systemId, String objectId);
	ObjectBoundary getSpecificObject(String userSystemId,String email,String objectSystemId,String objectId);
	
	@Deprecated
	void bindObjects(String parentSystemId,String parentObjectId,ObjectChildIdBoundary child);
	void bindObjects(String userSystemId,String email,String parentSystemId,String parentObjectId,ObjectChildIdBoundary child);
	
	@Deprecated
	void update(String systemId,String objectId, ObjectBoundary boundaryForUpdate);
	void update(String userSystemId,String email,String objectSystemId,String objectId, ObjectBoundary boundaryForUpdate);
	
	@Deprecated
	void deleteAll();
	public void deleteAll(String systemId, String email);
	
}
