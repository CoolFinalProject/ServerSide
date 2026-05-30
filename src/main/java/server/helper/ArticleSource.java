package server.helper;

import java.util.Date;
public class ArticleSource {
	private String webSource;
	private String author;
	private Date publishDate;
	private Date scrapeDate;
	
	
	
	public ArticleSource() {};
	
	public ArticleSource(String webSource, String author, Date publishDate, Date scrapeDate) {
		super();
		this.webSource = webSource;
		this.author = author;
		this.publishDate = publishDate;
		this.scrapeDate = scrapeDate;
	}
	public String getWebSource() {
		return webSource;
	}
	public void setWebSource(String webSource) {
		this.webSource = webSource;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getScrapeDate() {
		return scrapeDate;
	}
	public void setScrapeDate(Date scrapeDate) {
		this.scrapeDate = scrapeDate;
	}
	@Override
	public String toString() {
		return "ArticleSource [webSoucre=" + webSource + ", author=" + author + ", publishDate=" + publishDate
				+ ", scrapeDate=" + scrapeDate + "]";
	}
	
	
	
	
	
}
