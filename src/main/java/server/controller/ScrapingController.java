package server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.helper.ArticleSource;


@RestController
@RequestMapping(path = {"/scrape"})
public class ScrapingController {
    


    @GetMapping(path="RSS")
    public ArticleSource[] scrapeRssData()
    {
        throw new server.exceptions.UnsupportedOperationException("This function scrapeRssData is not yet implemented");
    }

}
//totya