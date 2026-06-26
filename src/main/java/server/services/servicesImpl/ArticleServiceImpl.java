package server.services.servicesImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleDto;
import server.convertions.ArticleConvertion;
import server.entities.ArticleEntities.ArticleEntity;
import server.enums.ArticleCategory;
import server.helper.ArticleSource;
import server.repositories.ArticleRepository;
import server.services.ArticleService;
import server.helper.OpenAiService;
import server.services.ScrapeService;
import server.DTO.ArticleDto.SummarizedArticleDto;
import server.services.UserService;

@Service
public class ArticleServiceImpl implements ArticleService{

	
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private OpenAiService openAiService;
    @Autowired
    private ScrapeService scrapeService;
    @Autowired
    private UserService userService;
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
		ArticleDto sample = new ArticleDto("IamCoolArticle", new ArticleSource("Ynet", "Me", new Date(), new Date()), "I am a title", "I am text", null, null);
		ArticleEntity entity = ArticleConvertion.dtoToEntity(sample);
		// default ttl is set in ArticleEntity
        articleRepository.save(entity);
	}

	@Override
	public void deleteAllArticles() {
		articleRepository.deleteAll();
	}

    @Override
    public List<SummarizedArticleDto> personalizedFeed(String token) {

        Map<String, Float> preferences = userService.getUserPreferences(token);

        List<ArticleDto> articles = getAllArticles();
        articles = buildBalancedFeedCandidates(articles, preferences, 15);
        List<SummarizedArticleDto> summarizedArticles = new ArrayList<>();

        int count = 0;

        for (ArticleDto article : articles) {

            if (count >= 15) {
                break;
            }


            List<ArticleSource> sources = new ArrayList<>();
            sources.add(article.getSource());

            List<ArticleDto> scrapedArticles = scrapeService.scrapeArticles(sources);

            if (scrapedArticles.isEmpty()) {
                System.out.println("Scrape returned empty");
                continue;
            }

            ArticleDto scrapedArticle = scrapedArticles.get(0);
            String text = scrapedArticle.getText();

            if (text == null || text.isBlank()) {
                System.out.println("Scraped text is empty");
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

    //checking according to user's preferences if the article has one of his preferred categories. if not - ignore article
    /*private boolean matchesUserPreferences(ArticleDto article, Map<String, Float> preferences) {

        if (preferences == null || preferences.isEmpty()) {
            return true;
        }

        if (article.getCategories() == null || article.getCategories().isEmpty()) {
            return false;
        }

        for (ArticleCategory category : article.getCategories()) {
            Float preferenceValue = preferences.get(category.name());

            if (preferenceValue != null && preferenceValue > 0) {
                return true;
            }
        }

        return false;
    }*/

    private double calculatePreferenceScore(ArticleDto article, Map<String, Float> preferences) {

        if (preferences == null || preferences.isEmpty()) {
            return 1.0;
        }

        if (article.getCategories() == null || article.getCategories().isEmpty()) {
            return 0.0;
        }

        double score = 0.0;

        for (ArticleCategory category : article.getCategories()) {
            Float preferenceValue = preferences.get(category.name());

            if (preferenceValue != null) {
                score += preferenceValue;
            }
        }

        return score / article.getCategories().size();
    }
    private List<ArticleDto> buildBalancedFeedCandidates(
            List<ArticleDto> articles,
            Map<String, Float> preferences,
            int limit
    ) {
        if (preferences == null || preferences.isEmpty()) {
            return articles.stream()
                    .limit(limit)
                    .toList();
        }

        List<ArticleDto> result = new ArrayList<>();

        Map<String, List<ArticleDto>> articlesByCategory = new java.util.HashMap<>();

        for (ArticleDto article : articles) {
            if (article.getCategories() == null || article.getCategories().isEmpty()) {
                continue;
            }

            for (ArticleCategory category : article.getCategories()) {
                Float weight = preferences.get(category.name());

                if (weight != null && weight > 0) {
                    articlesByCategory
                            .computeIfAbsent(category.name(), k -> new ArrayList<>())
                            .add(article);
                }
            }
        }

        double totalWeight = preferences.values().stream()
                .filter(value -> value != null && value > 0)
                .mapToDouble(Float::doubleValue)
                .sum();

        for (Map.Entry<String, Float> entry : preferences.entrySet()) {
            String categoryName = entry.getKey();
            Float weight = entry.getValue();

            if (weight == null || weight <= 0) {
                continue;
            }

            List<ArticleDto> categoryArticles =
                    articlesByCategory.getOrDefault(categoryName, List.of());

            int quota = Math.max(1, (int) Math.round((weight / totalWeight) * limit));

            for (ArticleDto article : categoryArticles) {
                if (result.size() >= limit || quota <= 0) {
                    break;
                }

                if (!result.contains(article)) {
                    result.add(article);
                    quota--;
                }
            }
        }

        return result;
    }
}
