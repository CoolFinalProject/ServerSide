package server.DTO.ArticleDto;

import java.util.List;

import server.enums.ArticleCategory;
import server.helper.ArticleSource;


public class ArticleMetadataDto
{
    private String articleId; 
    private ArticleSource source;
    private String description;
    private List<ArticleCategory> categories;
    
    public ArticleMetadataDto() {
    }

    public ArticleMetadataDto(ArticleSource source, String description,
            List<ArticleCategory> categories) {
        this.articleId = source.getWebSource();
        this.source = source;
        this.description = description;
        this.categories = categories;
    }

    public ArticleMetadataDto(ArticleMetadataDto other) {
        this.articleId = other.articleId;
        this.source = other.source;
        this.description = other.description;
        this.categories = other.categories != null ? List.copyOf(other.categories) : null;
    }

    public ArticleSource getSource() {
        return source;
    }
    public void setSource(ArticleSource source) {
        this.source = source;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<ArticleCategory> getCategories() {
        return categories;
    }
    public void setCategories(List<ArticleCategory> categories) {
        this.categories = categories;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "ArticleMetadataDto [articleId=" + articleId + ", source=" + source + ", description=" + description
                + ", categories=" + categories + "]";
    }




}