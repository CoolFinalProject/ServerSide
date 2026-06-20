package server.services;

import server.helper.ArticleSource;

public interface RssService {

    ArticleSource[] fetchYnetRss();
    void fetchAndSaveYnetRss();

    void removeDuplicateArticles();
}