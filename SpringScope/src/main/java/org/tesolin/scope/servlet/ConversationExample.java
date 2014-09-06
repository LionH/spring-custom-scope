package org.tesolin.scope.servlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Future;
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
		logger.info("Main Thread [{}]", Thread.currentThread().getName());
		Collection<Future<?>> futures = new ArrayList<Future<?>>();
		IntStream.range(0, 5).forEach(
				i -> {
					futures.add(scopedFactory.getMultiThreadedConversation()
							.execute());
				});
		for (Future<?> futureTask : futures) {
			if (!futureTask.isDone()) {
				Thread.sleep(200);
				continue;
			}
		}
		return scopedFactory.getCall();
	}
}
