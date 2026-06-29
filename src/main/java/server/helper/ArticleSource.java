package server.helper;

import java.util.Date;
public class ArticleSource {
	private String webSource;
	private String author;
	private Date publishDate;
	private Date scrapeDate;
    private String title;

	public ArticleSource() {};
	
	public ArticleSource(String webSource, String author, Date publishDate, Date scrapeDate) {
		super();
		this.webSource = webSource;
		this.author = author;
		this.publishDate = publishDate;
		this.scrapeDate = scrapeDate;
	}
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
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
        return "ArticleSource [title=" + title
                + ", webSource=" + webSource
                + ", author=" + author
                + ", publishDate=" + publishDate
                + ", scrapeDate=" + scrapeDate + "]";
    }
}
