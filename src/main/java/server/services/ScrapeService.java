package server.services;

import java.util.List;

import server.DTO.ArticleDto.ArticleDto;
import server.DTO.ArticleDto.ArticleMetadataDto;

public interface ScrapeService {

    public List<ArticleDto> scrapeArticles(List<ArticleMetadataDto> sources) ;
    public ArticleDto scrapeArticle(ArticleMetadataDto source) ;

}