package server.repositories.mongo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import server.entities.ArticleEntities.ArticleMetadataEntity;

public interface ArticleMetadataRepository extends MongoRepository<ArticleMetadataEntity, String> {

    @Query("{ 'articleId': { $nin: ?1 }, 'source.publishDate': { $gte: ?0 } }")
    List<ArticleMetadataEntity> findRecentCandidatesExcluding(Date since, List<String> excludeArticleIds, Pageable pageable);

    @Query("{ 'categories': ?0 }")
    Page<ArticleMetadataEntity> findByCategory(String category, Pageable pageable);
}
