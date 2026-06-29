package server.entities.ArticleEntities;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import server.enums.ArticleCategory;
import server.helper.ArticleSource;

///
/// NOTICE!!
/// We won't save on the data base summarized articles as this is unnecessary 
/// So this entity will be for raw article data
/// 
/// 

@RedisHash(value="articles", timeToLive=86400L)
public class ArticleEntity extends ArticleMetadataEntity{

	private String text;
	private Map<String, Object> details;


    @TimeToLive
    private Long ttl; // seconds

    public ArticleEntity() {
        super();
    }

    public ArticleEntity(ArticleSource source, String description, List<ArticleCategory> categories,
            String text, Map<String, Object> details, Long ttl) {
        super(source, description, categories);
        this.text = text;
        this.details = details;
        this.ttl = ttl;
    }

    public ArticleEntity(ArticleEntity other) {
        this.setArticleId(other.getArticleId());
        this.setSource(other.getSource());
        this.setDescription(other.getDescription());
        this.setCategories(other.getCategories() != null ? List.copyOf(other.getCategories()) : null);
        this.text = other.text;
        this.details = other.details != null ? Map.copyOf(other.details) : null;
        this.ttl = other.ttl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "ArticleEntity [text=" + text + ", details=" + details + ", ttl=" + ttl + "]";
    }

	
}
