package org.tesolin.scope.servlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Future;

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

	private final Logger logger = LoggerFactory.getLogger(ConversationExample.class);

	@Autowired
	private AsyncListenableTaskExecutor taskExecutor;

	@Autowired
	private ScopedFactory scopedFactory;
	
	@PostConstruct
	public void init() {
		logger.info("Initializing the singleton entrypoint");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tesolin.scope.servlet.ConversationExampleBase#call()
	 */
	@Override
	@ConversationTransaction
	public Call call() throws InterruptedException {
		int i=0;
		Collection<Future<?>> futures = new ArrayList<Future<?>>();
		while (i++<5) {
			Future<?> futureTask = 
					taskExecutor.submit(scopedFactory.getMultiThreadedConversation());
			futures.add(futureTask);
		}
		continued:while(true) {
			Thread.sleep(200);
			for (Future<?> futureTask : futures) {
				if (!futureTask.isDone()) {
					continue continued;
				}
			}
			break;
		}
		return scopedFactory.getCall();
	}
}
