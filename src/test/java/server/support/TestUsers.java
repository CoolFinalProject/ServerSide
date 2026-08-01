package server.support;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import server.entities.UserEntities.UserEntity;
import server.enums.ArticleCategory;
import server.enums.UserRole;

public final class TestUsers {

	public static final Map<String, Float> DEFAULT_PREFERENCES =
			Map.of(ArticleCategory.POLITICS.name(), 0.8f);

	private TestUsers() {
	}

	public static UserEntity create(int index) {
		return new UserEntity(
				"test-" + index,
				"password",
				userId(index),
				true,
				UserRole.END_USER,
				new Date(),
				DEFAULT_PREFERENCES);
	}

	public static List<UserEntity> createMany(int count) {
		return IntStream.range(0, count)
				.mapToObj(TestUsers::create)
				.toList();
	}

	public static String userId(int index) {
		return "user-" + index;
	}

	public static List<String> userIds(int count) {
		return IntStream.range(0, count)
				.mapToObj(TestUsers::userId)
				.toList();
	}
}
