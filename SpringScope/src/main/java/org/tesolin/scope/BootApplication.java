package org.tesolin.scope;

import org.springframework.boot.SpringApplication;
import org.tesolin.scope.context.AppConfig;

public class BootApplication {

	public static void main(String... strings) {
		SpringApplication.run(AppConfig.class, strings);
	}
}
