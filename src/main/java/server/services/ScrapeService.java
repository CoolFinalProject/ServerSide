package server.services;

import java.util.List;

import server.DTO.ArticleDto.ArticleDto;
import server.helper.ArticleSource;

public interface ScrapeService {

    public List<ArticleDto> scrapeArticles(List<ArticleSource> sources) ;
}