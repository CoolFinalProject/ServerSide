package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import server.Application;
import server.entities.UserEntities.UserEntity;
import server.enums.ArticleCategory;
import server.enums.UserRole;
import server.repositories.mongo.UserRepository;
import server.services.ArticleMetadataService;

@SpringBootTest(classes = Application.class)
class ApplicationTests {

	private static final int USER_COUNT = 250;
	private static final Map<String, Float> PREFERENCES = Map.of(ArticleCategory.POLITICS.name(), 0.8f);

	@Autowired
	private ArticleMetadataService metadataService;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void createTestUsers() {
		List<UserEntity> users = new ArrayList<>(USER_COUNT);
		Date now = new Date();

		for (int i = 0; i < USER_COUNT; i++) {
			users.add(new UserEntity(
					"test-" + i,
					"password",
					"user-" + i,
					true,
					UserRole.END_USER,
					now,
					PREFERENCES));
		}

		userRepository.saveAll(users);
	}

	@AfterEach
	void deleteTestUsers() {
		userRepository.deleteAllById(
				IntStream.range(0, USER_COUNT).mapToObj(i -> "user-" + i).toList());
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getPersonalizedFeed_handlesConcurrentRequests() throws Exception {
		long startTime = System.currentTimeMillis();

		int totalRequests = USER_COUNT;
		ExecutorService executor = Executors.newFixedThreadPool(250);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch done = new CountDownLatch(totalRequests);

		for (int i = 0; i < totalRequests; i++) {
			String userId = "user-" + i;
			executor.submit(() -> {
				try {
					start.await();
					metadataService.getPersonalizedFeed(userId, 10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					done.countDown();
				}
			});
		}

		start.countDown();
		assertTrue(done.await(50, TimeUnit.SECONDS));
		executor.shutdown();

		long elapsedMs = System.currentTimeMillis() - startTime;
		System.out.println("Completed " + totalRequests + " requests in " + elapsedMs + " ms");
	}

}
