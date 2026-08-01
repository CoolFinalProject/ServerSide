package server.services;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import server.repositories.mongo.UserRepository;
import server.support.TestUsers;

@SpringBootTest
class ArticleMetadataServiceConcurrentTest {

	private static final int USER_COUNT = 250;
	private static final int REQUEST_TIMEOUT_SECONDS = 30;

	@Autowired
	private ArticleMetadataService metadataService;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void seedUsers() {
		userRepository.saveAll(TestUsers.createMany(USER_COUNT));
	}

	@AfterEach
	void deleteUsers() {
		userRepository.deleteAllById(TestUsers.userIds(USER_COUNT));
	}

	@Test
	@DisplayName("getPersonalizedFeed handles concurrent requests")
	void getPersonalizedFeed_handlesConcurrentRequests() throws Exception {
		long startTime = System.currentTimeMillis();

		ExecutorService executor = Executors.newFixedThreadPool(USER_COUNT);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch done = new CountDownLatch(USER_COUNT);

		for (int i = 0; i < USER_COUNT; i++) {
			String userId = TestUsers.userId(i);
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
		assertTrue(done.await(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS));
		executor.shutdown();

		long elapsedMs = System.currentTimeMillis() - startTime;
		System.out.println("Completed " + USER_COUNT + " requests in " + elapsedMs + " ms");
	}
}
