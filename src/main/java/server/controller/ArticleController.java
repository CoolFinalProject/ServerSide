package server.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import server.DTO.ArticleDto.ArticleDto;
import server.DTO.ArticleDto.ArticleMetadataDto;
import server.DTO.ArticleDto.SummarizedArticleDto;
import server.helper.OpenAiService;
import server.helper.RssFetcher;
import server.services.ArticleService;
import server.services.ScrapeService;

@RestController
@RequestMapping(path= {"/article"})
public class ArticleController {
	ArticleService articleService;
	ScrapeService scrapeService;
	OpenAiService openAi;

	public ArticleController(ArticleService articleService,ScrapeService scrapeService,OpenAiService openAi)
	{
		this.articleService=articleService;
		this.scrapeService=scrapeService;
		this.openAi=openAi;
	}

	
	@GetMapping(path = "getAllArticles",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ArticleDto> getAllArticles()
	{
		return articleService.getAllArticles();
	}

    @GetMapping(path= "rawArticle")
    public ArticleDto getRawArticleData(
            @RequestParam("articleId") String articleId,
            @RequestAttribute("firebaseUid") String uid) {
        return articleService.getRawArticleData(articleId);
    }
	@GetMapping(path="searchByText")
	public ResponseEntity<List<ArticleDto>> getArticlesByText(@RequestParam(name = "text",required = false,defaultValue = "") String text)
	{
		return articleService.searchArticlesByText(text);
	}

	@GetMapping(path="summariseForUser")
	public SummarizedArticleDto summariseForUser(@RequestAttribute("firebaseUid") String uid, @RequestParam("articleId") String articleId)
	{

		return articleService.getSummarizedArticleForUser(uid, articleId);

	} 
	@GetMapping(path= "getArticlesForUser/{id}")
	public List<ArticleDto> getArticlesForUser(@PathVariable("id") String id)
	{
		return null;
	}

	@GetMapping(path= "testArticleSummarise")
	public List<SummarizedArticleDto> getArticleSummarised()
    {
		RssFetcher rssFetcher = new RssFetcher();
        List<ArticleMetadataDto> sources= new ArrayList<ArticleMetadataDto>(Arrays.asList(rssFetcher.fetchAndPrint("https://www.ynet.co.il/Integration/StoryRss2.xml")[0]) );
		List<ArticleDto> articles = scrapeService.scrapeArticles(sources);
		List<SummarizedArticleDto> sumArticles= new ArrayList<>();
		for (ArticleDto article : articles) 
		{
			SummarizedArticleDto temp= new SummarizedArticleDto(article);
            temp.setSummarizedText(
                    openAi.summarizeNeutral(
                            article.getText(),
                            OpenAiService.DEFAULT_SUMMARY_PROMPT
                    )
            );
			sumArticles.add(temp);	
		}
        return sumArticles;
    }
}
