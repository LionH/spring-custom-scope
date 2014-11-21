package org.tesolin.scope.servlet;

import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tesolin.scope.beans.Message;
import org.tesolin.scope.definition.ConversationTransaction;
import org.tesolin.scope.multithread.MultiThreadedConversation;
import org.tesolin.scope.scoped.Call;
import org.tesolin.scope.scoped.ScopedFactory;

@Component
public class ConversationImpl implements Conversation {

	private static final int CONCURRENT_THREAD = 10;

	private final Logger logger = LoggerFactory
			.getLogger(ConversationImpl.class);

	@Autowired
	private AsyncListenableTaskExecutor taskExecutor;

	@Autowired
	private MultiThreadedConversation task;

	@Autowired
	private ScopedFactory scopedFactory;

	@Autowired
	private DB mapdb;

	@PostConstruct
	public void init() {
		logger.info("Initializing the singleton entrypoint");
		mapdb.delete("listOfMessage");
		mapdb.commit();
	}

	@Async
	@Override
	@ConversationTransaction
	public Call call() throws InterruptedException {
		Collection<Future<?>> futures = IntStream
				.range(0, new Random().nextInt(CONCURRENT_THREAD))
				.mapToObj(
						i -> scopedFactory.getMultiThreadedConversation()
								.execute()
				).collect(Collectors.toList());
		while (futures.stream().anyMatch(future -> !future.isDone())) {
			logger.debug("...");
			Thread.sleep(200);
		}
		Call ret = scopedFactory.getCall();

		// open existing an collection (or create new)
		Set<Message> set = mapdb.getTreeSet("listOfMessage");

		ret.messages().forEach(item ->{
			Atomic.Integer keyinc = mapdb.getAtomicInteger("call_keyinc");
			item.setId(keyinc.incrementAndGet());
			set.add(item);
		});

		mapdb.commit();

		return ret;
	}

	@Override
	public Set<Message> calls() {
		// open existing an collection (or create new)
		Set<Message> set = mapdb.getTreeSet("listOfMessage");
		return set;
	}

	@PreDestroy
	public void dispose() {
		mapdb.close();
	}
}
