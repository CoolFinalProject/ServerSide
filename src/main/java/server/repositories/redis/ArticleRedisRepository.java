package server.repositories.redis;

import org.springframework.data.repository.CrudRepository;

import server.entities.ArticleEntities.ArticleEntity;

public interface ArticleRedisRepository extends CrudRepository<ArticleEntity, String> {
}
