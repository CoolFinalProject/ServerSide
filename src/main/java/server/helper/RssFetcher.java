package server.helper;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class RssFetcher {

    public ArticleSource[] fetchAndPrint(String feedUrl) {
        try {
            URL url = new URL(feedUrl);
            XmlReader reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);



            List<SyndEntry> entries = feed.getEntries();
            ArticleSource[] articleSources = new ArticleSource[entries.size()];
            for (int i = 0; i < entries.size(); i++) {
                articleSources[i] = new ArticleSource();
                articleSources[i].setWebSoucre(entries.get(i).getTitle());
                articleSources[i].setAuthor(entries.get(i).getAuthor());
                articleSources[i].setPublishDate(entries.get(i).getPublishedDate());
                articleSources[i].setScrapeDate(new Date());
            }
            return articleSources;
        } catch (Exception e) {
            e.printStackTrace();
        }
      throw new server.exceptions.NotFoundException("failed to fetch RSS data");
    }
}