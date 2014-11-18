package org.tesolin.scope.servlet;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentNavigableMap;
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
import org.springframework.stereotype.Component;
import org.tesolin.scope.definition.ConversationTransaction;
import org.tesolin.scope.multithread.MultiThreadedConversation;
import org.tesolin.scope.scoped.Call;
import org.tesolin.scope.scoped.ScopedFactory;

@Component
public class ConversationExample implements ConversationExampleBase {

	private static final int CONCURRENT_THREAD = 10;

	private final Logger logger = LoggerFactory
			.getLogger(ConversationExample.class);

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
		ConcurrentNavigableMap<Integer, Collection<String>> map = mapdb.getTreeMap("calls");
		map.clear();
		mapdb.commit();
	}

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
		ConcurrentNavigableMap<Integer, Collection<String>> map = mapdb.getTreeMap("calls");

		Atomic.Integer keyinc = mapdb.getAtomicInteger("call_keyinc");

		map.put(keyinc.incrementAndGet(), ret.words());

		mapdb.commit();

		return ret;
	}

	@Override
	public Map<Integer, Collection<String>> calls() {
		// open existing an collection (or create new)
		ConcurrentNavigableMap<Integer, Collection<String>> map = mapdb.getTreeMap("calls");
		return map;
	}

	@PreDestroy
	public void dispose() {
		mapdb.close();
	}
}
