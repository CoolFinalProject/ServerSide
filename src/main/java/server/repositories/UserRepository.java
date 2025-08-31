package server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import server.entities.UserEntities.UserEntity;


public interface UserRepository extends MongoRepository<UserEntity, String>{
}