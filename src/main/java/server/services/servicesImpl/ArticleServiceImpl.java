package server.services.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleDto;
import server.convertions.ArticleConvertion;
import server.entities.ArticleEntities.ArticleEntity;
import server.repositories.ArticleRepository;
import server.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	
    @Autowired
    private ArticleRepository articleRepository;

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
	public void deleteAllArticles() {
		articleRepository.deleteAll();
	}
/* 
    @Override
    public List<SummarizedArticleDto> personalizedFeed(String token) {

        Map<String, Float> preferences = userService.getUserPreferences(token);

        List<ArticleDto> articles = getAllArticles();
        articles = buildBalancedFeedCandidates(articles, preferences, 15);
        List<SummarizedArticleDto> summarizedArticles = new ArrayList<>();

        int count = 0;

        System.out.println("Went through pipeline");
        for (ArticleDto article : articles) {

                    System.out.println("Article "+count);

            if (count >= 5) {
                break;
            }


            List<ArticleMetadataDto> metadatas = new ArrayList<>();
            metadatas.add((ArticleMetadataDto)article);

            List<ArticleDto> scrapedArticles = scrapeService.scrapeArticles(metadatas);

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
        */
}
