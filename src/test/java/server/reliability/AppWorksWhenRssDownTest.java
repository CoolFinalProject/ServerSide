package server.reliability;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import server.DTO.ArticleDto.ArticleMetadataDto;
import server.repositories.mongo.UserRepository;
import server.services.ArticleMetadataService;
import server.services.RssService;
import server.support.TestUsers;

@SpringBootTest
@TestPropertySource(properties = {
		"rss.urls=http://127.0.0.1:59999/unreachable,http://127.0.0.1:59998/unreachable"
})
class AppWorksWhenRssDownTest {

	private static final String TEST_USER_ID = TestUsers.userId(0);

	@Autowired
	private RssService rssService;

	@Autowired
	private ArticleMetadataService metadataService;

	@Autowired
	private UserRepository userRepository;


	@BeforeEach
	void seedData() {
		userRepository.save(TestUsers.create(0));
	}

	@AfterEach
	void cleanUp() {
		userRepository.deleteById(TEST_USER_ID);
	}

	@Test
	void personalizedFeedWorksWhenAllRssSourcesFail() {
		assertDoesNotThrow(() -> rssService.fetchAndSaveAllRssSources());

		List<ArticleMetadataDto> feed = metadataService.getPersonalizedFeed(TEST_USER_ID, 10);

		assertTrue(!feed.isEmpty());
	}
}
