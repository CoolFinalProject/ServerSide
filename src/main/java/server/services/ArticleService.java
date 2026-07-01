package server.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import server.DTO.ArticleDto.ArticleDto;
import server.DTO.ArticleDto.SummarizedArticleDto;

public interface ArticleService {
	public ArticleDto getRawArticleData(String articleId);
	public SummarizedArticleDto getSummarizedArticleForUser(String uid,String articleId);
	public ResponseEntity<List<ArticleDto>> searchArticlesByText(String text);
    public List<ArticleDto> getAllArticles();
	public void deleteAllArticles();

    //List<SummarizedArticleDto> personalizedFeed(String token);
}
