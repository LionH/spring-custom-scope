package org.tesolin.scope.multithread;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tesolin.scope.definition.ConversationScope;
import org.tesolin.scope.scoped.Call;
import org.tesolin.scope.scoped.ScopedFactory;

@Component
@Scope("prototype")
public class MultiThreadedConversationImpl implements MultiThreadedConversation{
	
	private Logger logger = LoggerFactory.getLogger(MultiThreadedConversationImpl.class);
	
	@Autowired
	private ScopedFactory factory;
	
	@Autowired
	private ConversationScope conversationScope;
	
	private String conversationId;
	
	@PostConstruct
	private void initialize() {
		logger.debug(String.format("Initializing MultiThreaded Call thread:[%s] obj:[%s]-->", 
				Thread.currentThread().getName(), this.hashCode()));
		conversationId = conversationScope.getConversationId();
	}

	/* (non-Javadoc)
	 * @see org.tesolin.scope.multithread.MultiThreadedConversation#run()
	 */
	@Override
	public void run() {
		conversationScope.registerConversation(conversationId);
		Call words = factory.getCall();
		words.addWord("Hello");
		words.addWord(Thread.currentThread().getName());
		words.addWord("Bye");
		logger.debug(String.format("<-- Ending MultiThreaded Call thread:[%s] obj:[%s]",
				Thread.currentThread().getName(), this.hashCode()));
	}
}
