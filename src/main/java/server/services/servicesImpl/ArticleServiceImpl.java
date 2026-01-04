package server.services.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleDto;
import server.convertions.ArticleConvertion;
import server.entities.ArticleEntities.ArticleEntity;
import server.helper.ArticleSource;
import server.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
    private RedisTemplate<String, ArticleEntity> redisTemplate;

    private static final String KEY = "ARTICLE";
	@Override
	public ArticleDto getRawArticleData(String articleId) {
		throw new server.exceptions.UnsupportedOperationException("getRawArticleData "+articleId);
	}

	@Override
	public ResponseEntity<List<ArticleDto>> searchArticlesByText(String text) {
		throw new UnsupportedOperationException("Unimplemented method 'searchArticlesByText'");
	}

	@Override
	public List<ArticleDto> getAllArticles() {
			// Get all articles from Redis
        @SuppressWarnings("unchecked")
		List<ArticleEntity> articleEntities = (List<ArticleEntity>)(List<?>) redisTemplate.opsForHash().values(KEY);
        
        // Convert to DTOs
        List<ArticleDto> articleDtos = articleEntities.stream()
                .map(ArticleConvertion::entityToDto)
                .collect(Collectors.toList());
        
        // Return the list of DTOs
        return articleDtos;
	}

	@Override
	public void createSample() {
		ArticleDto sample = new ArticleDto("IamArticle", new ArticleSource("Ynet", "Me", new Date(), new Date()), "I am a title", "I am text", null);

		redisTemplate.opsForHash().put(KEY, "samp", ArticleConvertion.dtoToEntity(sample));
	}

}
