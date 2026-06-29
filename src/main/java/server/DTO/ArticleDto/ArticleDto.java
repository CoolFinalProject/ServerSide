package server.DTO.ArticleDto;

import java.util.List;
import java.util.Map;

import server.enums.ArticleCategory;
import server.helper.ArticleSource;
public class ArticleDto extends ArticleMetadataDto{

	private String text;
	private Map<String, Object> details;

	
	public ArticleDto() {
		super();

	}
	public ArticleDto(ArticleSource source, String description, List<ArticleCategory> categories, String text,
			Map<String, Object> details) {
		super(source, description, categories);
		this.text = text;
		this.details = details;
	}

	public ArticleDto(ArticleMetadataDto metadata, String text, Map<String, Object> details) {
		super(metadata);
		this.text = text;
		this.details = details;
	}

	public ArticleDto(ArticleDto other) {
		super(other);
		this.text = other.text;
		this.details = other.details != null ? Map.copyOf(other.details) : null;
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
		return "ArticleDto [text=" + text + ", details=" + details + "]";
	}
	
	
	
}
