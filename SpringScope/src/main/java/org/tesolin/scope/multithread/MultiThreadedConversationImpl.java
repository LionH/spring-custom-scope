package org.tesolin.scope.multithread;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.tesolin.scope.scoped.Call;

@Component
public class MultiThreadedConversationImpl implements MultiThreadedConversation {

	private Logger logger = LoggerFactory
			.getLogger(MultiThreadedConversationImpl.class);
	
	private enum WORDS {
		Hola,
		que,
		tal,
		me,
		llamo,
		Lionel;
	}
	
	@Autowired
	private Call words;

	@PostConstruct
	private void initialize() {
		logger.debug("Initializing MultiThreaded Call thread:[{}] obj:[{}]-->",
				Thread.currentThread().getName(), this.hashCode());
	}

	@Override
	public Future<?> execute() {
		try {
			TimeUnit.MILLISECONDS.sleep(new Random().longs(0, 5000).findFirst().getAsLong());
		} catch (InterruptedException e) {
			logger.error("Something went wrong...", e);
		}
		
		words.addMessage(String.format(
				new Random().ints(WORDS.values().length, 0, WORDS.values().length)
				.mapToObj(
						i -> WORDS.values()[i]
				).collect(Collectors.toSet()).toString()));
		
		logger.debug("<-- Ending MultiThreaded Call thread:[{}] on obj:[{}]",
				Thread.currentThread().getName(), this.hashCode());
		return new AsyncResult<Void>(null);
	}
}
