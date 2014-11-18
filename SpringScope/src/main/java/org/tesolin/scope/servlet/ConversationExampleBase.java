package org.tesolin.scope.servlet;

import java.util.Collection;
import java.util.Map;

import org.tesolin.scope.scoped.Call;

public interface ConversationExampleBase {

	Call call() throws InterruptedException;

	Map<Integer, Collection<String>> calls();

}