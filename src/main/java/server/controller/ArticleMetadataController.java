package server.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.DTO.ArticleDto.ArticleMetadataDto;
import server.services.ArticleMetadataService;

@RestController
@RequestMapping(path= {"/articleMetadata"})
public class ArticleMetadataController {

    ArticleMetadataService metadataService;
    public ArticleMetadataController(ArticleMetadataService metadataService)
    {
        this.metadataService=metadataService;
    }

    @GetMapping(path= "getAll")
    public Page<ArticleMetadataDto> getAll(Pageable pageable)
    {
        return metadataService.getAllArticleMetadata(pageable);
    }

    
}
