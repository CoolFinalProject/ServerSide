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

@Service
public class ArticleServiceImpl implements ArticleService{

	
    @Autowired
    private ArticleRepository articleRepository;
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
		/*	// Get all articles from Redis
        @SuppressWarnings("unchecked")
		List<ArticleEntity> articleEntities = (List<ArticleEntity>)(List<?>) redisTemplate.opsForHash().values(KEY);
        
        // Convert to DTOs
        List<ArticleDto> articleDtos = articleEntities.stream()
                .map(ArticleConvertion::entityToDto)
                .collect(Collectors.toList());
        
        // Return the list of DTOs
        return articleDtos;*/

    	Iterable<ArticleEntity> entities = articleRepository.findAll();

    	List<ArticleDto> articleDtos = new ArrayList<>();
    	for (ArticleEntity entity : entities) {
			if (entity !=null)
      			articleDtos.add(ArticleConvertion.entityToDto(entity));
    	}

    	return articleDtos;
	}

	@Override
	public void createSample() {
		ArticleDto sample = new ArticleDto("IamCoolArticle", new ArticleSource("Ynet", "Me", new Date(), new Date()), "I am a title", "I am text", null);

		ArticleEntity entity = ArticleConvertion.dtoToEntity(sample);

        articleRepository.save(entity);
	}

	@Override
	public void deleteAllArticles() {
		articleRepository.deleteAll();
	}

}
