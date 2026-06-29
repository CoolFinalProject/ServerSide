package server.repositories.mongo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import server.entities.UserEntities.UserArticleDeliveredEntity;

public interface UserArticleDeliveredRepository extends MongoRepository<UserArticleDeliveredEntity, String> {

    List<UserArticleDeliveredEntity> findByUserIdOrderByDeliveredAtDesc(String userId, Pageable pageable);

    List<UserArticleDeliveredEntity> findByUserIdOrderByDeliveredAtAsc(String userId, Pageable pageable);

    long countByUserId(String userId);

    long deleteByUserId(String userId);
}
