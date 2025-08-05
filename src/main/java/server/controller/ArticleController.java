package server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.DTO.ArticleDto.ArticleDto;
import server.services.ArticleService;

@RestController
@RequestMapping(path= {"/article"})
public class ArticleController {
	ArticleService articleService;
	public ArticleController(ArticleService articleService)
	{
		this.articleService=articleService;
	}
	@GetMapping(path= "rawArticle/{articleId}")
	public ArticleDto getRawArticleData(@RequestParam("articleId") String articleId)
	{
		return articleService.getRawArticleData(articleId);
	}
	
}
