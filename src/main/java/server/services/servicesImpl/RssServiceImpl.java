package server.services.servicesImpl;

import org.springframework.stereotype.Service;
import server.entities.ArticleEntities.ArticleEntity;
import server.helper.ArticleSource;
import server.helper.RssFetcher;
import server.repositories.ArticleRepository;
import server.services.RssService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RssServiceImpl implements RssService {
    @Autowired
    private ArticleRepository articleRepository;
    private final RssFetcher rssFetcher = new RssFetcher();

    @Override
    public ArticleSource[] fetchYnetRss() {
        return rssFetcher.fetchAndPrint("https://www.ynet.co.il/Integration/StoryRss2.xml");

    }

    @Override
    public void fetchAndSaveYnetRss() {
        ArticleSource[] articles = fetchYnetRss();

        int saved = 0;
        int skipped = 0;

        for (ArticleSource article : articles) {
            String articleUrl = article.getWebSource();

            if (articleUrl == null || articleUrl.isBlank()) {
                skipped++;
                continue;
            }

            if (articleRepository.existsById(articleUrl)) {
                skipped++;
                continue;
            }

            ArticleEntity entity = new ArticleEntity();

            entity.setArticleId(articleUrl);
            entity.setSource(article);
            entity.setTitle(article.getTitle());
            entity.setText(null);
            entity.setDetails(null);

            articleRepository.save(entity);
            saved++;
        }

        System.out.println("Saved new articles: " + saved);
        System.out.println("Skipped duplicate articles: " + skipped);
        System.out.println("Articles in repository: " + articleRepository.count());
    }

    @Override
    public void removeDuplicateArticles() {
        Iterable<ArticleEntity> allArticles = articleRepository.findAll();
        Map<String, String> seenUrls = new HashMap<>();
        List<String> duplicateIds = new ArrayList<>();

        for (ArticleEntity entity : allArticles) {


            if (entity == null || entity.getSource() == null) {
                continue;
            }

            String url = entity.getSource().getWebSource();

            if (url == null || url.isBlank()) {
                continue;
            }

            if (seenUrls.containsKey(url)) {
                duplicateIds.add(entity.getArticleId());
            } else {
                seenUrls.put(url, entity.getArticleId());
            }
        }

        articleRepository.deleteAllById(duplicateIds);

        System.out.println("Duplicate cleanup finished");
        System.out.println("Removed duplicate articles: " + duplicateIds.size());
    }
}