package io.github.takgeun.accessible_finance_backend;

import org.springframework.boot.SpringApplication;

public class TestAccessibleFinanceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(AccessibleFinanceBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
