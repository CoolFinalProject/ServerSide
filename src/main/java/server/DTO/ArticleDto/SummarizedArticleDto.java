package server.DTO.ArticleDto;

import java.util.List;
import java.util.Map;

import server.enums.ArticleCategory;
import server.helper.ArticleSource;

public class SummarizedArticleDto extends ArticleDto{
	private String forUserId; //user Id in which the summarization is for 
	private String summarizedText;

	public SummarizedArticleDto() {
		super();
	}

	public SummarizedArticleDto(ArticleSource source, String description, List<ArticleCategory> categories,
			String text, Map<String, Object> details, String forUserId, String summarizedText) {
		super(source, description, categories, text, details);
		this.forUserId = forUserId;
		this.summarizedText = summarizedText;
	}

	public SummarizedArticleDto(ArticleDto other) {
		super(other);
		this.forUserId = null;
		this.summarizedText = null;
	}

	public SummarizedArticleDto(SummarizedArticleDto other) {
		super(other);
		this.forUserId = other.forUserId;
		this.summarizedText = other.summarizedText;
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
		return "SummarizedArticleDto [forUserId=" + forUserId + ", summarizedText=" + summarizedText+"]";
	}
	




	
}
