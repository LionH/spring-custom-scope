package org.tesolin.scope.servlet;

import java.util.Collection;

import org.tesolin.scope.beans.Message;

public interface Conversation {

	void call() throws InterruptedException;

	Collection<Message> calls();

}