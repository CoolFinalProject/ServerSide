package server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import server.entities.ArticleEntities.ArticleMetadataEntity;

public interface ArticleMetadataRepository extends MongoRepository<ArticleMetadataEntity, String>
{

}
