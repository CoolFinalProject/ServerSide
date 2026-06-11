package server.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.helper.ArticleSource;
import server.services.RssService;

@Component
public class RssScheduler {

    private final RssService rssService;

    public RssScheduler(RssService rssService) {
        this.rssService = rssService;
    }

    @Scheduled(fixedRate = 300000)
    public void fetchRssEveryFiveMinutes() {
        rssService.fetchAndSaveYnetRss();
    }
}