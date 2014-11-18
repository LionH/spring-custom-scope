package org.tesolin.scope.definition;

import java.util.UUID;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class ConversationScopedAdvice {

	private final Logger log = LoggerFactory
			.getLogger(ConversationScopedAdvice.class);

	@Autowired
	private ConversationScope conversationScope;

	@Before("@annotation(ConversationTransaction)")
	public void beforeConversation() throws Throwable {
		String uuid = UUID.randomUUID().toString();
		log.debug("Begining conversation {}", uuid);
		conversationScope.registerConversation(uuid);
	}

	@After("@annotation(ConversationTransaction)")
	public void afterConversation() throws Throwable {
		log.debug("Ending conversation {}", conversationScope.endConversation());
	}
}
