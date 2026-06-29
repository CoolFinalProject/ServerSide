package server.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import server.DTO.ArticleDto.ArticleMetadataDto;

public interface ArticleMetadataService {

    public Page<ArticleMetadataDto> getAllArticleMetadata(Pageable pageable);
}
