package org.tesolin.scope.multithread;

import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.tesolin.scope.definition.ConversationScope;
import org.tesolin.scope.scoped.Call;
import org.tesolin.scope.scoped.ScopedFactory;

@Component
@Scope("prototype")
public class MultiThreadedConversationImpl implements MultiThreadedConversation {

	private Logger logger = LoggerFactory
			.getLogger(MultiThreadedConversationImpl.class);

	@Autowired
	private ScopedFactory factory;

	@Autowired
	private ConversationScope conversationScope;

	private String conversationId;

	@PostConstruct
	private void initialize() {
		conversationId = conversationScope.getConversationId();
		logger.debug(
				"Initializing MultiThreaded Call conversation:[{}] thread:[{}] obj:[{}]-->",
				conversationId, Thread.currentThread().getName(),
				this.hashCode());
	}

	@Override
	public Future<?> execute() {
		conversationScope.registerConversation(conversationId);
		Call words = factory.getCall();
		words.addWord("Hello");
		words.addWord(Thread.currentThread().getName());
		words.addWord("Bye");
		logger.debug(
				"<-- Ending MultiThreaded Call conversation:[{}] thread:[{}] obj:[{}]",
				conversationId, Thread.currentThread().getName(),
				this.hashCode());
		return new AsyncResult<Call>(words);
	}
}
