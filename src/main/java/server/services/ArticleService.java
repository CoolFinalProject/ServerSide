package server.services;

import server.DTO.ArticleDto.ArticleDto;

public interface ArticleService {
	public ArticleDto getRawArticleData(String articleId);
}
