package server.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.helper.ArticleSource;
import server.services.RssService;

@Component
public class RssScheduler {

    private final RssService rssService;
    private boolean firstRun = true;

    public RssScheduler(RssService rssService) {
        this.rssService = rssService;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchRssEveryFiveMinutes() {

        if (firstRun) {
            rssService.removeDuplicateArticles();
            firstRun = false;
        }

        rssService.fetchAndSaveYnetRss();
    }
}