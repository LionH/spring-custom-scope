package org.tesolin.scope.scoped;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value="conversation", proxyMode=ScopedProxyMode.INTERFACES)
@Component
public class CallImpl implements Call {

	private final Logger logger = LoggerFactory.getLogger(CallImpl.class);

	private Collection<String> blabla;

	@PostConstruct
	private void initialize() {
		logger.info("Initializing scoped communication buffer");
		blabla = CollectionUtils.synchronizedCollection(new ArrayList<String>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tesolin.scope.scoped.WordsBase#addWord(java.lang.String)
	 */
	@Override
	public void addWord(String word) {
		blabla.add(word);
		logger.debug(
				"<-- Adding word [{}] to conversation on thread:[{}] on obj:[{}]",
				word,
				Thread.currentThread().getName(),
				this.hashCode());
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
