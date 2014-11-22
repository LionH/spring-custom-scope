package org.tesolin.scope.servlet;

import java.util.Collection;

import org.tesolin.scope.beans.Message;

public interface ConversationController {

	public abstract Collection<Message> calls();

	public abstract void call() throws InterruptedException;

}