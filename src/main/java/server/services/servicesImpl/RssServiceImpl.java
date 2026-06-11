package server.services.servicesImpl;

import org.springframework.stereotype.Service;
import server.entities.ArticleEntities.ArticleEntity;
import server.helper.ArticleSource;
import server.helper.RssFetcher;
import server.repositories.ArticleRepository;
import server.services.RssService;
import org.springframework.beans.factory.annotation.Autowired;
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

        for (ArticleSource article : articles) {
            ArticleEntity entity = new ArticleEntity();

            entity.setSource(article);
            entity.setTitle(article.getTitle());
            entity.setText(null);
            entity.setDetails(null);

            articleRepository.save(entity);
        }

        System.out.println("Saved " + articles.length + " articles");
        System.out.println("Articles in repository: " + articleRepository.count());
    }
}