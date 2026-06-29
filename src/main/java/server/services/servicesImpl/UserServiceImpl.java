package server.services.servicesImpl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import server.DTO.UserDto.UserAuthenticateDto;
import server.DTO.UserDto.UserDto;
import server.DTO.UserDto.UserUpdateDto;
import server.convertions.UserConvertion;
import server.entities.UserEntities.UserEntity;
import server.enums.UserRole;
import server.repositories.mongo.UserArticleDeliveredRepository;
import server.repositories.mongo.UserRepository;
import server.services.UserService;
@Service
public class UserServiceImpl implements UserService{

	UserRepository userRep;
	UserArticleDeliveredRepository deliveredRepository;

	public UserServiceImpl(UserRepository userRep, UserArticleDeliveredRepository deliveredRepository)
	{
		this.userRep=userRep;
		this.deliveredRepository=deliveredRepository;
	}

	private UserEntity getUserEntityByUid(String uid) {
		return userRep.findById(uid).orElseThrow(() -> new server.exceptions.NotFoundException("User does not exist in server"));
	}

	@Override
	public UserDto getUserByUid(String uid) {
		return UserConvertion.userEntityToDto(getUserEntityByUid(uid));
	}

	public UserDto getUserfromUid(String uid)
	{
		return getUserByUid(uid);
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
	public UserDto signUpUser(String uid) {
		try {
			UserRecord firebaseUser = FirebaseAuth.getInstance().getUser(uid);
			UserDto user = new UserDto();
			user.setUserName(firebaseUser.getDisplayName());
			user.setPassWord("");
			user.setUserRole(UserRole.END_USER);
			user.setUserId(uid);
			user.setCreationTime(new Date());
			userRep.save(UserConvertion.userDtoToEntity(user));
			return user;
		} catch (FirebaseAuthException e) {
			throw new server.exceptions.BadRequestException("Invalid uid " + uid);
		}
	}

    @Override
    public UserDto updateUserPreferences(String uid, Map<String, Float> genrePreferences) {
        UserEntity entity = getUserEntityByUid(uid);
        entity.setGenrePreferences(genrePreferences);
        UserEntity savedEntity = userRep.save(entity);

        return UserConvertion.userEntityToDto(savedEntity);
    }
    @Override
    public Map<String, Float> getUserPreferences(String uid) {
		return getUserByUid(uid).getGenrePreferences();
    }

    @Override
    public long clearDeliveredArticles(String uid) {
        getUserfromUid(uid);
        return deliveredRepository.deleteByUserId(uid);
    }

	@Override
	public void deleteAllUsers() {
		userRep.deleteAll();
	}

}
