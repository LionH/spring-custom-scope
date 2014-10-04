package org.tesolin.scope.servlet;

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Component;
import org.tesolin.scope.definition.ConversationTransaction;
import org.tesolin.scope.scoped.Call;
import org.tesolin.scope.scoped.ScopedFactory;

@Component
public class ConversationExample implements ConversationExampleBase {
	
	private static final int CONCURRENT_THREAD = 50;

	private final Logger logger = LoggerFactory
			.getLogger(ConversationExample.class);

	@Autowired
	private AsyncListenableTaskExecutor taskExecutor;

	@Autowired
	private ScopedFactory scopedFactory;

	@PostConstruct
	public void init() {
		logger.info("Initializing the singleton entrypoint");
	}

	@Override
	@ConversationTransaction
	public Call call() throws InterruptedException {
		Collection<Future<?>> futures = IntStream.range(0, CONCURRENT_THREAD).mapToObj(i -> {
			return scopedFactory.getMultiThreadedConversation()
					.execute();
		}).collect(Collectors.toList());
		while(futures.stream().anyMatch(future -> !future.isDone())){
			logger.debug("...");
			Thread.sleep(200);
		}
		Call ret = scopedFactory.getCall();
		
		return ret;
	}
}
