package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories(basePackages = "server.repositories.mongo")
@EnableRedisRepositories(basePackages = "server.repositories.redis")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
