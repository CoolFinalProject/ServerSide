package server.services.servicesImpl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleDto;
import server.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Override
	public ArticleDto getRawArticleData(String articleId) {
		throw new server.excptions.UnsupportedOperationException("getRawArticleData "+articleId);
	}

	@Override
	public ResponseEntity<List<ArticleDto>> searchArticlesByText(String text) {
		throw new UnsupportedOperationException("Unimplemented method 'searchArticlesByText'");
	}

}
