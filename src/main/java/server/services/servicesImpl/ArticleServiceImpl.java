package server.services.servicesImpl;

import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleDto;
import server.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Override
	public ArticleDto getRawArticleData(String articleId) {
		throw new server.excptions.UnsupportedOperationException("getRawArticleData "+articleId);
	}

}
