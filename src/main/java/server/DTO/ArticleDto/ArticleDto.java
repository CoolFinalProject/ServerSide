package server.DTO.ArticleDto;

import java.util.Map;

import server.helper.ArticleSource;

public class ArticleDto {

	private String articleId; //UUId
	private ArticleSource source;
	private String title;
	private String text;
	private Map<String, Object> details;
	
	
	public ArticleDto() {};
	public ArticleDto(String articleId, ArticleSource source, String title, String text, Map<String, Object> details) {
		super();
		this.articleId = articleId;
		this.source = source;
		this.title = title;
		this.text = text;
		this.details = details;
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
	@Override
	public String toString() {
		return "ArticleDto [articleId=" + articleId + ", source=" + source + ", title=" + title + ", text=" + text
				+ ", details=" + details + "]";
	}
	
	
}
