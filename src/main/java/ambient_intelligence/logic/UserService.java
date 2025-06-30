package ambient_intelligence.logic;

import java.util.List;
import java.util.Optional;

import ambient_intelligence.boundary.NewUserBoundary;
import ambient_intelligence.boundary.UserBoundary;

public interface UserService {
	
	public UserBoundary createNewUser(NewUserBoundary user);
	public UserBoundary getUser(String userd);
	
	@Deprecated
	List<UserBoundary> getAll();
	List <UserBoundary> getAll(String systemId,String email,int size,int page);
	public void update(String userId,UserBoundary boundaryForUpdate);
	@Deprecated
	public void deleteAll();
	public void deleteAll(String systemId,String email);
	
	
	public static String genId(String systemId,String email)
	{
		return systemId+"_"+email;
	}
}
