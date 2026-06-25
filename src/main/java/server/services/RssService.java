package server.services;

import server.helper.ArticleSource;

public interface RssService {

    void fetchAndSaveAllRssSources();

    void removeDuplicateArticles();
}