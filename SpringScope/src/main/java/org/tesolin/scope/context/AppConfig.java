package org.tesolin.scope.context;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAspectJAutoProxy
@Configuration
@ComponentScan({ "org.tesolin.scope.*" })
public class AppConfig {
	
	@PostConstruct
	public void init() {
		System.out.println("AppConfig initialized...");
	}
	
	@Bean
    public AsyncListenableTaskExecutor taskExecutor(@Value("60") int poolSize) {
            ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
            taskExecutor.setPoolSize(poolSize);
            return taskExecutor;
    }

}
