package server.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import server.DTO.ArticleDto.ArticleMetadataDto;

public interface ArticleMetadataService {

    public Page<ArticleMetadataDto> getAllArticleMetadata(Pageable pageable);

    List<ArticleMetadataDto> getPersonalizedFeed(String userId, int size);
    long clearDeliveredArticles(String uid);

}
