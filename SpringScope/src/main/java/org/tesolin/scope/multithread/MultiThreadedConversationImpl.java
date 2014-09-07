package org.tesolin.scope.multithread;

import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.tesolin.scope.scoped.Call;

@Component
@Scope("prototype")
public class MultiThreadedConversationImpl implements MultiThreadedConversation {

	private Logger logger = LoggerFactory
			.getLogger(MultiThreadedConversationImpl.class);

	@Autowired
	private Call words;

	@PostConstruct
	private void initialize() {
		logger.debug(
				"Initializing MultiThreaded Call thread:[{}] obj:[{}]-->",
				Thread.currentThread().getName(),
				this.hashCode());
	}

	@Override
	public Future<?> execute() {
		words.addWord("Hello");
		words.addWord(Thread.currentThread().getName());
		words.addWord("Bye");
		logger.debug(
				"<-- Ending MultiThreaded Call thread:[{}] obj:[{}]",
				Thread.currentThread().getName(),
				this.hashCode());
		return new AsyncResult<Void>(null);
	}
}
