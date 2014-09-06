package org.tesolin.scope.servlet;

import org.tesolin.scope.scoped.Call;

public interface ConversationExampleBase {

	public abstract Call call() throws InterruptedException;

}