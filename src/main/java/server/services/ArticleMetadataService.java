package server.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import server.DTO.ArticleDto.ArticleMetadataDto;

public interface ArticleMetadataService {

    public Page<ArticleMetadataDto> getAllArticleMetadata(Pageable pageable);
    List<ArticleMetadataDto> getPersonalizedFeed(String userId, int size);
    public Page<ArticleMetadataDto> getByCategory(String category,Pageable pageable);
    Page<ArticleMetadataDto> searchArticles(String text, Pageable pageable);
    public ArticleMetadataDto getMetadataById(String articleId);
    long clearDeliveredArticles(String uid);

}
