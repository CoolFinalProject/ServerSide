package server.entities.ArticleEntities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import server.enums.ArticleCategory;
import server.helper.ArticleSource;
@Document(collection = "articleMetadata")
public class ArticleMetadataEntity {
    @Id
    private String articleId; 
    private ArticleSource source;
    private String description;
    private List<ArticleCategory> categories;

    public ArticleMetadataEntity() {
    }
    public ArticleMetadataEntity(ArticleSource source, String description,
            List<ArticleCategory> categories) {
        this.articleId = source.getWebSource();
        this.source = source;
        this.description = description;
        this.categories = categories;
    }

    
    public String getArticleId() {
        return articleId;
    }
    public void setArticleId(String articleId) {
        this.articleId = articleId;
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
    @Override
    public String toString() {
        return "ArticleMetadataEntity [articleId=" + articleId + ", source=" + source + ", description=" + description
                + ", categories=" + categories + "]";
    }
    
}
