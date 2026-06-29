package server.repositories.mongo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import server.entities.UserEntities.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByUserNameAndPassWord(String userName, String password);
}
