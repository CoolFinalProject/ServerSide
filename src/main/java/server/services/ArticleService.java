package server.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import server.DTO.ArticleDto.ArticleDto;

public interface ArticleService {
	public ArticleDto getRawArticleData(String articleId);
	public ResponseEntity<List<ArticleDto>> searchArticlesByText(String text);
}
