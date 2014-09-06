package org.tesolin.scope.multithread;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;

public interface MultiThreadedConversation {

	@Async
	Future<?> execute();
}