package io.github.takgeun.accessible_finance_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AccessibleFinanceBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
