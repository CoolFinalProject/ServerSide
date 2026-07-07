package server.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springdoc.core.annotations.ParameterObject;

import server.DTO.ArticleDto.ArticleMetadataDto;
import server.enums.ArticleCategory;
import server.services.ArticleMetadataService;

@RestController
@RequestMapping(path= {"/articleMetadata"})
public class ArticleMetadataController {

    ArticleMetadataService metadataService;

    public ArticleMetadataController(ArticleMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping(path= "getAll")
    public Page<ArticleMetadataDto> getAll(@ParameterObject Pageable pageable) {
        return metadataService.getAllArticleMetadata(pageable);
    }

    @GetMapping(path = "categories")
    public List<String> getCategories() {
        return Arrays.stream(ArticleCategory.values())
                .map(ArticleCategory::name)
                .toList();
    }

    @GetMapping(path = "personalizedFeed")
    public ResponseEntity<List<ArticleMetadataDto>> getPersonalizedFeed(
            @RequestAttribute("firebaseUid") String uid,
            @RequestParam(defaultValue = "10",name = "size") int size) {
        return ResponseEntity.ok(metadataService.getPersonalizedFeed(uid, size));
    }
    @GetMapping(path = "getByCategory")
    public Page<ArticleMetadataDto> getByCategory(@RequestParam(required=true,name="category")String category, @ParameterObject Pageable pageable)
    {
        return metadataService.getByCategory(category, pageable);
    }

    @GetMapping(path = "search")
    public Page<ArticleMetadataDto> search(
            @RequestParam(name = "text", required = false, defaultValue = "") String text,
            @ParameterObject Pageable pageable) {
        return metadataService.searchArticles(text, pageable);
    }

    @DeleteMapping(path = "/deliveredArticles")
    public long clearDeliveredArticles(@RequestAttribute("firebaseUid") String uid) {
        return metadataService.clearDeliveredArticles(uid);
    }

}
