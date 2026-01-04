package server.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	
	@GetMapping(path = "getAllArticles",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ArticleDto> getAllArticles()
	{
		return articleService.getAllArticles();
	}
	@GetMapping(path = "sample")
	public void createSampleArticle()
	{
		articleService.createSample();
	}
	@GetMapping(path= "rawArticle/{articleId}")
	public ArticleDto getRawArticleData(@RequestParam("articleId") String articleId)
	{
		return articleService.getRawArticleData(articleId);
	}
	@GetMapping(path="searchByText")
	public ResponseEntity<List<ArticleDto>> getArticlesByText(@RequestParam(name = "text",required = false,defaultValue = "") String text)
	{
		return articleService.searchArticlesByText(text);
	}

	@GetMapping(path= "getArticlesForUser/{id}")
	public List<ArticleDto> getArticlesForUser(@PathVariable("id") String id)
	{
		return null;
	}
}
