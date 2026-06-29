package server.services.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import server.entities.ArticleEntities.ArticleMetadataEntity;
import server.helper.RssFetcher;
import server.repositories.ArticleMetadataRepository;
import server.services.RssService;
import server.enums.ArticleCategory;
import server.helper.OpenAiService;

import java.util.List;

import server.DTO.ArticleDto.ArticleMetadataDto;
import server.convertions.ArticleMetadataConvertion;

@Service
public class RssServiceImpl implements RssService {

    @Autowired
    private ArticleMetadataRepository articleMetadataRepository;

    @Autowired
    private OpenAiService openAiService;

    private final RssFetcher rssFetcher = new RssFetcher();

    @Value("${rss.urls}")
    private List<String> rssUrls;

    @Override
    public void fetchAndSaveAllRssSources() {

        int totalSaved = 0;
        int totalSkipped = 0;

        for (String rssUrl : rssUrls) {
            try {
                ArticleMetadataDto[] articles = rssFetcher.fetchAndPrint(rssUrl);
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
        System.out.println("Articles in repository: " + articleMetadataRepository.count());
    }

    private SaveResult saveArticles(ArticleMetadataDto[] articles) {

        int saved = 0;
        int skipped = 0;

        if (articles == null) {
            return new SaveResult(saved, skipped);
        }

        for (ArticleMetadataDto article : articles) {

            if (article == null) {
                skipped++;
                continue;
            }

            String articleUrl = article.getSource().getWebSource();

            if (articleUrl == null || articleUrl.isBlank()) {
                skipped++;
                continue;
            }

            if (articleMetadataRepository.existsById(articleUrl)) {
                skipped++;
                continue;
            }

            ArticleMetadataEntity entity = ArticleMetadataConvertion.dtoToEntity(article);
            try {
                List<ArticleCategory> categories = openAiService.classifyCategories(
                        article.getSource().getTitle(),
                        article.getDescription(),
                        article.getSource().getWebSource()
                );

                if (categories == null || categories.isEmpty()) {
                    categories = List.of(ArticleCategory.GENERAL);
                }

                entity.setCategories(categories);

            } catch (Exception e) {
                entity.setCategories(List.of(ArticleCategory.GENERAL));
            }
            articleMetadataRepository.insert(entity); 
            
            

            saved++;
        }

        return new SaveResult(saved, skipped);
    }

    private record SaveResult(int saved, int skipped) {}
}
