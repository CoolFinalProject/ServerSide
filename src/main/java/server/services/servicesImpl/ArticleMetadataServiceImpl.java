package server.services.servicesImpl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleMetadataDto;
import server.convertions.ArticleMetadataConvertion;
import server.entities.ArticleEntities.ArticleMetadataEntity;
import server.entities.UserEntities.UserArticleDeliveredEntity;
import server.enums.ArticleCategory;
import server.repositories.mongo.ArticleMetadataRepository;
import server.repositories.mongo.UserArticleDeliveredRepository;
import server.services.ArticleMetadataService;
import server.services.UserService;

@Service
public class ArticleMetadataServiceImpl implements ArticleMetadataService{

    private static final int CANDIDATE_POOL_SIZE = 200;
    private static final int MAX_DELIVERED_PER_USER = 1000;
    private static final int DELIVERED_LOOKUP_LIMIT = 1000;
    private static final int RECENT_DAYS = 14;

    @Autowired
    ArticleMetadataRepository metadataRepository;

    @Autowired
    UserArticleDeliveredRepository deliveredRepository;

    @Autowired
    UserService userService;

    /*
    @Autowired
    ScrapeService scrapeService;

    @Autowired
    OpenAiService openAiService;
    */

    @Override
    public Page<ArticleMetadataDto> getAllArticleMetadata(Pageable pageable) {
        Page<ArticleMetadataEntity> page = metadataRepository.findAll(pageable);
        return page.map(entity -> ArticleMetadataConvertion.entityToDto(entity));
    }
        @Override
    public Page<ArticleMetadataDto> getByCategory(String category, Pageable pageable) {
        Page<ArticleMetadataEntity> page = metadataRepository.findByCategory(category, pageable);
        return page.map(entity-> ArticleMetadataConvertion.entityToDto(entity));
    }
        @Override
    public ArticleMetadataDto getMetadataById(String articleId) {
        
        return ArticleMetadataConvertion.entityToDto(metadataRepository.findById(articleId).orElseThrow(()-> new server.exceptions.NotFoundException(articleId+" Not Found")));
    }
    @Override
    public long clearDeliveredArticles(String uid) {
        userService.getUserByUid(uid);
        return deliveredRepository.deleteByUserId(uid);
    }

    @Override
    public List<ArticleMetadataDto> getPersonalizedFeed(String userId, int size) {
        Map<String, Float> preferences = userService.getUserPreferences(userId);
        List<String> deliveredArticleIds = loadDeliveredArticleIds(userId);

        Date since = Date.from(Instant.now().minus(RECENT_DAYS, ChronoUnit.DAYS));
        Pageable candidatePage = PageRequest.of(0, CANDIDATE_POOL_SIZE,
                Sort.by(Sort.Direction.DESC, "source.publishDate"));

        List<ArticleMetadataEntity> candidates = metadataRepository.findRecentCandidatesExcluding(
                since, deliveredArticleIds, candidatePage);

        List<ArticleMetadataEntity> ranked = rankCandidates(candidates, preferences);

        List<ArticleMetadataDto> feed = new ArrayList<>();
        for (ArticleMetadataEntity candidate : ranked) {
            if (feed.size() >= size) {
                break;
            }

            ArticleMetadataDto metadata = ArticleMetadataConvertion.entityToDto(candidate);
            feed.add(metadata);
            recordDelivery(userId, candidate.getArticleId());

            /*
            List<ArticleDto> scrapedArticles = scrapeService.scrapeArticles(List.of(metadata));
            if (scrapedArticles.isEmpty()) {
                continue;
            }

            ArticleDto scrapedArticle = scrapedArticles.get(0);
            String text = scrapedArticle.getText();
            if (text == null || text.isBlank()) {
                continue;
            }

            SummarizedArticleDto summarizedArticle = new SummarizedArticleDto(scrapedArticle);
            summarizedArticle.setForUserId(userId);
            summarizedArticle.setSummarizedText(openAiService.summarizeNeutral(text));

            feed.add(summarizedArticle);
            */
        }

        trimDeliveries(userId);
        return feed;
    }

    private List<String> loadDeliveredArticleIds(String userId) {
        Pageable page = PageRequest.of(0, DELIVERED_LOOKUP_LIMIT,
                Sort.by(Sort.Direction.DESC, "deliveredAt"));
        return deliveredRepository.findByUserIdOrderByDeliveredAtDesc(userId, page).stream()
                .map(UserArticleDeliveredEntity::getArticleId)
                .collect(Collectors.toList());
    }

    private List<ArticleMetadataEntity> rankCandidates(
            List<ArticleMetadataEntity> candidates,
            Map<String, Float> preferences) {
        return candidates.stream()
                .sorted(Comparator
                        .comparingDouble((ArticleMetadataEntity article) -> finalScore(article, preferences))
                        .reversed()
                        .thenComparing(this::publishDateForSort, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    private Date publishDateForSort(ArticleMetadataEntity article) {
        if (article.getSource() == null) {
            return null;
        }
        return article.getSource().getPublishDate();
    }

    private double finalScore(ArticleMetadataEntity article, Map<String, Float> preferences) {
        return 0.85 * calculatePreferenceScore(article, preferences)
                + 0.15 * calculateRecencyScore(publishDateForSort(article));
    }

    private double calculatePreferenceScore(ArticleMetadataEntity article, Map<String, Float> preferences) {
        if (preferences == null || preferences.isEmpty()) {
            return 1.0;
        }

        if (article.getCategories() == null || article.getCategories().isEmpty()) {
            return 0.0;
        }

        double score = 0.0;
        for (ArticleCategory category : article.getCategories()) {
            Float preferenceValue = preferences.get(category.name());
            if (preferenceValue != null) {
                score += preferenceValue;
            }
        }

        return score / article.getCategories().size();
    }

    private double calculateRecencyScore(Date publishDate) {
        if (publishDate == null) {
            return 0.0;
        }

        long ageMs = System.currentTimeMillis() - publishDate.getTime();
        long maxAgeMs = RECENT_DAYS * 24L * 60L * 60L * 1000L;
        if (ageMs >= maxAgeMs) {
            return 0.0;
        }

        return 1.0 - ((double) ageMs / maxAgeMs);
    }

    private void recordDelivery(String userId, String articleId) {
        if (articleId == null || articleId.isBlank()) {
            return;
        }

        String deliveryId = userId + "|" + articleId;
        if (deliveredRepository.existsById(deliveryId)) {
            return;
        }

        deliveredRepository.save(new UserArticleDeliveredEntity(userId, articleId, Instant.now()));
    }

    private void trimDeliveries(String userId) {
        long count = deliveredRepository.countByUserId(userId);
        if (count <= MAX_DELIVERED_PER_USER) {
            return;
        }

        int toDelete = (int) (count - MAX_DELIVERED_PER_USER);
        Pageable page = PageRequest.of(0, toDelete, Sort.by(Sort.Direction.ASC, "deliveredAt"));
        List<UserArticleDeliveredEntity> oldest = deliveredRepository.findByUserIdOrderByDeliveredAtAsc(userId, page);
        deliveredRepository.deleteAll(oldest);
    }


}
