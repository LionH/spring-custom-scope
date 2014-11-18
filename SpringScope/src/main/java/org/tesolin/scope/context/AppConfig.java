package org.tesolin.scope.context;

import java.io.File;

import javax.annotation.PostConstruct;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.tesolin.scope.definition.ConversationScope;

@EnableWebMvc
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
@Configuration
@ComponentScan({ "org.tesolin.scope.*" })
public class AppConfig {
	
	private final Logger logger = LoggerFactory
			.getLogger(AppConfig.class);

	@PostConstruct
	public void init() {
		logger.info("AppConfig initialized...");
	}

	@Bean
	public AsyncListenableTaskExecutor taskExecutor(@Value("60") int poolSize) {
		ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
		taskExecutor.setPoolSize(poolSize);
		return taskExecutor;
	}

	@Bean
	public DB mapdb() {
		// configure and open database using builder pattern.
		// all options are available with code auto-completion.
		DB db = DBMaker.newFileDB(new File("testdb")).closeOnJvmShutdown()
				.encryptionEnable("password").make();
		logger.info("DB initialized...");
		return db;
	}

	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
}
