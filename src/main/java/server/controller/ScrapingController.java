package server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.helper.ArticleSource;
import server.helper.RssFetcher;


@RestController
@RequestMapping(path = {"/scrape"})
public class ScrapingController {
    


    @GetMapping(path="RSS")
    public ArticleSource[] scrapeRssData()
    {
        RssFetcher rssFetcher = new RssFetcher();
        return rssFetcher.fetchAndPrint("https://www.ynet.co.il/Integration/StoryRss2.xml");

    }

}
