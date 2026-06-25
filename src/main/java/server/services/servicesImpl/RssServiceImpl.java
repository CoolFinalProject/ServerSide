package server.services.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.entities.ArticleEntities.ArticleEntity;
import server.helper.ArticleSource;
import server.helper.RssFetcher;
import server.repositories.ArticleRepository;
import server.services.RssService;
import server.enums.ArticleCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RssServiceImpl implements RssService {

    @Autowired
    private ArticleRepository articleRepository;

    private final RssFetcher rssFetcher = new RssFetcher();

    @Value("${rss.urls}")
    private List<String> rssUrls;

    @Override
    public void fetchAndSaveAllRssSources() {

        int totalSaved = 0;
        int totalSkipped = 0;

        for (String rssUrl : rssUrls) {
            try {
                ArticleSource[] articles = rssFetcher.fetchAndPrint(rssUrl);
                SaveResult result = saveArticles(articles);

                totalSaved += result.saved();
                totalSkipped += result.skipped();

                System.out.println("Finished RSS source: " + rssUrl);
                System.out.println("Saved from source: " + result.saved());
                System.out.println("Skipped from source: " + result.skipped());

            } catch (Exception e) {
                System.out.println("Failed to fetch RSS source: " + rssUrl);
                e.printStackTrace();
            }
        }

        System.out.println("RSS fetch finished");
        System.out.println("Total saved new articles: " + totalSaved);
        System.out.println("Total skipped articles: " + totalSkipped);
        System.out.println("Articles in repository: " + articleRepository.count());
    }

    private SaveResult saveArticles(ArticleSource[] articles) {

        int saved = 0;
        int skipped = 0;

        if (articles == null) {
            return new SaveResult(saved, skipped);
        }

        for (ArticleSource article : articles) {

            if (article == null) {
                skipped++;
                continue;
            }

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
            entity.setCategories(List.of(ArticleCategory.GENERAL));
            articleRepository.save(entity);
            saved++;
        }

        return new SaveResult(saved, skipped);
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

    private record SaveResult(int saved, int skipped) {
    }
}
