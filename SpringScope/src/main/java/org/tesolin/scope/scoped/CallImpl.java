package org.tesolin.scope.scoped;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.tesolin.scope.definition.ConversationScope;

@Scope(value="conversation", proxyMode=ScopedProxyMode.INTERFACES)
@Component
public class CallImpl implements Call {

	private final Logger logger = LoggerFactory.getLogger(CallImpl.class);

	private Collection<String> blabla;
	
	@Autowired
	private ConversationScope scope;

	@PostConstruct
	private void initialize() {
		logger.info("Initializing scoped communication buffer");
		blabla = CollectionUtils.synchronizedCollection(new ArrayList<>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tesolin.scope.scoped.WordsBase#addWord(java.lang.String)
	 */
	@Override
	public void addWord(String word) {
		blabla.add(String.format("%s on Conversation [%s]", word, scope.getConversationId()));
		logger.debug(
				"<-- Adding word [{}] to conversation [{}] on obj [{}]",
				word,
				scope.getConversationId(),
				this.hashCode());
	}
	
	@Override
	public Collection<String> words() {
		return blabla;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tesolin.scope.scoped.WordsBase#toString()
	 */
	@Override
	public String toString() {
		return blabla.toString();
	}

	@PreDestroy
	public void dispose() {
		logger.info("Destroying scoped communication buffer");
	}
}
