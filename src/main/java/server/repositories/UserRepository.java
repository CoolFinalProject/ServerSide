package server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import server.entities.UserEntities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUserNameAndPassWord(String userName, String passWord);
}
