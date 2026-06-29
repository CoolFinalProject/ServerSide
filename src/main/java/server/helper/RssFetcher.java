package server.helper;

import java.net.URL;
import java.util.Date;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import server.DTO.ArticleDto.ArticleMetadataDto;

public class RssFetcher {

    public ArticleMetadataDto[] fetchAndPrint(String feedUrl) {
        try {
            URL url = new URL(feedUrl);
            XmlReader reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);



            List<SyndEntry> entries = feed.getEntries();
            ArticleMetadataDto[] articleMetadata = new ArticleMetadataDto[entries.size()];
            for (int i = 0; i < entries.size(); i++) {
                SyndEntry entry = entries.get(i);
                ArticleSource source = new ArticleSource();
                source.setTitle(entry.getTitle());
                source.setWebSource(entry.getLink());
                source.setAuthor(entry.getAuthor());
                source.setPublishDate(entry.getPublishedDate());
                source.setScrapeDate(new Date());

                String description = null;
                if (entry.getDescription() != null) {
                    description = entry.getDescription().getValue();
                }

                articleMetadata[i] = new ArticleMetadataDto(source, description, null);
            }
            return articleMetadata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new server.exceptions.NotFoundException("failed to fetch RSS data");
    }
}