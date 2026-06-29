package server.entities.UserEntities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;



/// this class will save whether user has seen a specific article 
/// 

@Document(collection = "userArticleDelivered")
@CompoundIndex(name = "user_article_unique", def = "{'userId': 1, 'articleId': 1}", unique = true)
public class UserArticleDeliveredEntity {

    @Id
    private String id;
    private String userId;
    private String articleId;
    private Instant deliveredAt;

    public UserArticleDeliveredEntity() {
    }

    public UserArticleDeliveredEntity(String userId, String articleId, Instant deliveredAt) {
        this.id = buildId(userId, articleId);
        this.userId = userId;
        this.articleId = articleId;
        this.deliveredAt = deliveredAt;
    }

    private static String buildId(String userId, String articleId) {
        return userId + "|" + articleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Instant getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Instant deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    @Override
    public String toString() {
        return "UserArticleDeliveredEntity [id=" + id + ", userId=" + userId + ", articleId=" + articleId
                + ", deliveredAt=" + deliveredAt + "]";
    }
}
