package server.services.servicesImpl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserUpdateDto;
import server.convertions.UserConvertion;
import server.entities.UserEntities.UserEntity;
import server.enums.UserRole;
import server.repositories.UserRepository;
import server.services.UserService;
@Service
public class UserServiceImpl implements UserService{

	UserRepository userRep;

	public UserServiceImpl(UserRepository userRep)
	{
		this.userRep=userRep;
	}

	private UserEntity getUserEntityFromToken(String token)
	{
		FirebaseToken decodedToken=getDecodedToken(token);
		// we normally won't get NotFound exception here as all users in firebase should be in database
		return userRep.findById(decodedToken.getUid()).orElseThrow(() -> new server.exceptions.NotFoundException("User does not exist in server"));

	}
	private FirebaseToken getDecodedToken(String token)
	{
		FirebaseToken decodedToken;
		try {
			decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
			return decodedToken;
		} catch (FirebaseAuthException e) {			
			throw new server.exceptions.BadRequestException("Invalid token "+token);
		}
	}

	@Override
	public UserDto authenticateByName(UserAuthenticateDto userAuthDto) 
	{	
		Optional<UserEntity> entity=userRep.findByUserNameAndPassWord(userAuthDto.getUserName(), userAuthDto.getPassWord());
		if (entity.isEmpty())
			throw new server.exceptions.NotFoundException("One or two fields are incorrect!");
		else
			return UserConvertion.userEntityToDto(entity.get());
	}

	@Override
	public UserDto getUserFromToken(String idToken) 
	{
		return UserConvertion.userEntityToDto(getUserEntityFromToken(idToken));
	}

	@Override
	public UserDto updateUserData(String id, UserUpdateDto userToUpdate) 
	{
		UserEntity entity=userRep.findById(id).orElseThrow(()-> new server.exceptions.NotFoundException("User "+id+" does not exist!"));

		if (!userToUpdate.getUserName().isBlank())
			entity.setUserName(userToUpdate.getUserName());
		if (!userToUpdate.getPassWord().isBlank())
			entity.setPassWord(userToUpdate.getPassWord());
		entity.setActive(userToUpdate.isActive());
		entity.setUserRole(userToUpdate.getUserRole());
		userRep.save(entity);
		return UserConvertion.userEntityToDto(entity);
	}

	@Override
	public UserDto signUpUser(String token) {
		
		FirebaseToken decodedToken=getDecodedToken(token);
		UserDto user=new UserDto();
		user.setUserName(decodedToken.getName());
		user.setPassWord("");
		user.setUserRole(UserRole.END_USER);
		user.setUserId(decodedToken.getUid());  
		user.setCreationTime(new Date());
		// by default active = true
		userRep.save(UserConvertion.userDtoToEntity(user));
		return user;
	}
    @Override
    public UserDto updateUserPreferences(String token, Map<String, Float> genrePreferences) {
        UserEntity entity = getUserEntityFromToken(token);
        entity.setGenrePreferences(genrePreferences);
        UserEntity savedEntity = userRep.save(entity);

        return UserConvertion.userEntityToDto(savedEntity);
    }
    @Override
    public Map<String, Float> getUserPreferences(String token) {
		UserDto user = getUserFromToken(token);
        return user.getGenrePreferences();
    }
	@Override
	public void deleteAllUsers() {
		userRep.deleteAll();
	}

}
