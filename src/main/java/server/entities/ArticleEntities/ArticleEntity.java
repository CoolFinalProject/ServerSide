package server.entities.ArticleEntities;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import server.helper.ArticleSource;

///
/// NOTICE!!
/// We won't save on the data base summarized articles as this is unnecessary 
/// So this entity will be for raw article data
/// 
/// 

@RedisHash(value="articles", timeToLive=10L)
public class ArticleEntity {

    @Id
    private String articleId; //UUId
	private ArticleSource source;
	private String title;
	private String text;
	private Map<String, Object> details;


    @TimeToLive
    private Long ttl; // seconds

    public ArticleEntity(){};
    public ArticleEntity(String articleId, ArticleSource source, String title, String text, Map<String, Object> details,Long ttl) {
        this.articleId = articleId;
        this.source = source;
        this.title = title;
        this.text = text;
        this.details = details;
        this.ttl=ttl;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
        return "ArticleEntity [articleId=" + articleId + ", source=" + source + ", title=" + title + ", text=" + text
                + ", details=" + details + "]";
    }

	
}
