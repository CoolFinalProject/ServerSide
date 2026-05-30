package server.DTO.ArticleDto;

import java.util.Map;

import server.helper.ArticleSource;

public class SummarizedArticleDto extends ArticleDto{
	private String forUserId; //user Id in which the summarization is for 
	private String summarizedText;
	

	public SummarizedArticleDto() {super();};
	
	public SummarizedArticleDto(String articleId, ArticleSource source, String title, String text,Map<String, Object> details,String forUserId,String summarizedText) {
		super(articleId, source, title, text, details);
		this.forUserId = forUserId;
		this.summarizedText = summarizedText;
	}
	public SummarizedArticleDto(ArticleDto article) {
		super(article.getArticleId(), article.getSource(), article.getTitle(), article.getText(), article.getDetails());
		this.forUserId = null;
		this.summarizedText = null;
	}

	public String getForUserId() {
		return forUserId;
	}

	public void setForUserId(String forUserId) {
		this.forUserId = forUserId;
	}
	
	public String getSummarizedText() {
		return summarizedText;
	}

	public void setSummarizedText(String summarizedText) {
		this.summarizedText = summarizedText;
	}

	@Override
	public String toString() {
		return "SummarizedArticleDto [forUserId=" + forUserId + ", summarizedText=" + summarizedText + "]";
	}


	
}
