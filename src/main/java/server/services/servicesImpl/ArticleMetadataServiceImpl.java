package server.services.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleMetadataDto;
import server.convertions.ArticleMetadataConvertion;
import server.entities.ArticleEntities.ArticleMetadataEntity;
import server.repositories.ArticleMetadataRepository;
import server.services.ArticleMetadataService;

@Service
public class ArticleMetadataServiceImpl implements ArticleMetadataService{

    @Autowired
    ArticleMetadataRepository metadataRepository;

    @Override
    public Page<ArticleMetadataDto> getAllArticleMetadata(Pageable pageable) {
        Page<ArticleMetadataEntity> page = metadataRepository.findAll(pageable);
        return page.map(entity -> ArticleMetadataConvertion.entityToDto(entity));
    }

}
