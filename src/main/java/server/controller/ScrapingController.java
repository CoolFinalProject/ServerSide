package server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.helper.ArticleSource;
import server.services.RssService;

@RestController
@RequestMapping(path = {"/scrape"})
public class ScrapingController {

    private final RssService rssService;

    public ScrapingController(RssService rssService) {
        this.rssService = rssService;
    }

    @GetMapping(path = "RSS")
    public ArticleSource[] scrapeRssData() {
        return rssService.fetchYnetRss();
    }
}