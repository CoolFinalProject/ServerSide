package server.convertions;

import server.DTO.ArticleDto.ArticleDto;
import server.entities.ArticleEntities.ArticleEntity;

public class ArticleConvertion {
public static ArticleDto entityToDto(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setArticleId(entity.getArticleId());
        dto.setSource(entity.getSource());
        dto.setTitle(entity.getTitle());
        dto.setText(entity.getText());
        dto.setDetails(entity.getDetails());
        dto.setCategories(entity.getCategories());
        return dto;
    }

    public static ArticleEntity dtoToEntity(ArticleDto dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setSource(dto.getSource());
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getText());
        entity.setDetails(dto.getDetails());
        entity.setCategories(dto.getCategories());
        return entity;
    }
}
