package org.tesolin.scope.scoped;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("conversation")
@Component
public class CallImpl implements Call {

	private final Logger logger = LoggerFactory.getLogger(CallImpl.class);

	private Collection<String> blabla;

	@PostConstruct
	private void initialize() {
		logger.info("Initializing scoped communication buffer");
		blabla = new ArrayList<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tesolin.scope.scoped.WordsBase#addWord(java.lang.String)
	 */
	@Override
	public void addWord(String word) {
		blabla.add(word);
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
