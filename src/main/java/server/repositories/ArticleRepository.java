package server.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import server.entities.ArticleEntities.ArticleEntity;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
}