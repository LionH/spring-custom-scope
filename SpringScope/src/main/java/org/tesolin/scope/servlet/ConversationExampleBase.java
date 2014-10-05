package org.tesolin.scope.servlet;

import java.util.Map;

import org.tesolin.scope.scoped.Call;

public interface ConversationExampleBase {

	Call call() throws InterruptedException;

	Map<Integer,String> calls();

}