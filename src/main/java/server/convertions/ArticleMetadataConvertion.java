package server.convertions;

import server.DTO.ArticleDto.ArticleMetadataDto;
import server.entities.ArticleEntities.ArticleMetadataEntity;

public class ArticleMetadataConvertion {

    public static ArticleMetadataDto entityToDto(ArticleMetadataEntity entity) {
        ArticleMetadataDto dto = new ArticleMetadataDto();
        dto.setArticleId(entity.getArticleId());
        dto.setSource(entity.getSource());
        dto.setDescription(entity.getDescription());
        dto.setCategories(entity.getCategories());
        return dto;
    }

    public static ArticleMetadataEntity dtoToEntity(ArticleMetadataDto dto) {
        ArticleMetadataEntity entity = new ArticleMetadataEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setSource(dto.getSource());
        entity.setDescription(dto.getDescription());
        entity.setCategories(dto.getCategories());
        return entity;
    }
}
