package demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import server.Application;

@SpringBootTest(classes=Application.class,webEnvironment = WebEnvironment.DEFINED_PORT)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
