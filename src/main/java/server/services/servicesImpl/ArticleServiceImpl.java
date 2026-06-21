package server.services.servicesImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleDto;
import server.convertions.ArticleConvertion;
import server.entities.ArticleEntities.ArticleEntity;
import server.helper.ArticleSource;
import server.repositories.ArticleRepository;
import server.services.ArticleService;
import server.helper.OpenAiService;
import server.services.ScrapeService;
import server.DTO.ArticleDto.SummarizedArticleDto;
@Service
public class ArticleServiceImpl implements ArticleService{

	
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private OpenAiService openAiService;
    @Autowired
    private ScrapeService scrapeService;
    @Override
    public ArticleDto getRawArticleData(String articleId) {
        ArticleEntity entity = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found: " + articleId));

        return ArticleConvertion.entityToDto(entity);
    }

	@Override
	public ResponseEntity<List<ArticleDto>> searchArticlesByText(String text) {
		throw new server.exceptions.UnsupportedOperationException("Unimplemented method 'searchArticlesByText'");
	}

	@Override
	public List<ArticleDto> getAllArticles() {
    	Iterable<ArticleEntity> entities = articleRepository.findAll();
    	List<ArticleDto> articleDtos = new ArrayList<>();

    	for (ArticleEntity entity : entities) {
			/// NOTICE  --- entities that were removed after ttl expired will still exist as null in the repository!!
			if (entity !=null)   
      			articleDtos.add(ArticleConvertion.entityToDto(entity));
    	}

    	return articleDtos;
	}

	@Override
	public void createSample() {
		ArticleDto sample = new ArticleDto("IamCoolArticle", new ArticleSource("Ynet", "Me", new Date(), new Date()), "I am a title", "I am text", null);
		ArticleEntity entity = ArticleConvertion.dtoToEntity(sample);
		// default ttl is set in ArticleEntity
        articleRepository.save(entity);
	}

	@Override
	public void deleteAllArticles() {
		articleRepository.deleteAll();
	}

    @Override
    public List<SummarizedArticleDto> pipelineTest() {

        List<ArticleDto> articles = getAllArticles();

        List<SummarizedArticleDto> summarizedArticles = new ArrayList<>();

        int count = 0;

        for (ArticleDto article : articles) {

            if (count >= 5) {
                break;
            }

            List<ArticleSource> sources = new ArrayList<>();
            sources.add(article.getSource());

            List<ArticleDto> scrapedArticles = scrapeService.scrapeArticles(sources);

            if (scrapedArticles.isEmpty()) {
                continue;
            }

            ArticleDto scrapedArticle = scrapedArticles.get(0);
            String text = scrapedArticle.getText();

            if (text == null || text.isBlank()) {
                continue;
            }

            SummarizedArticleDto summarizedArticle = new SummarizedArticleDto(article);
            summarizedArticle.setText(text);
            summarizedArticle.setSummarizedText(openAiService.summarizeNeutral(text));

            summarizedArticles.add(summarizedArticle);
            count++;
        }

        return summarizedArticles;
    }

}
