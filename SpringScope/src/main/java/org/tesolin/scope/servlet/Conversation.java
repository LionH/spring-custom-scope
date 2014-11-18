package org.tesolin.scope.servlet;

import java.util.Collection;

import org.tesolin.scope.beans.Message;
import org.tesolin.scope.scoped.Call;

public interface Conversation {

	Call call() throws InterruptedException;

	Collection<Message> calls();

}