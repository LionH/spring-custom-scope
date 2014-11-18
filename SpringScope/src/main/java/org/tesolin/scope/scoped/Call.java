package org.tesolin.scope.scoped;

import java.util.Collection;

import org.tesolin.scope.beans.Message;

public interface Call {
	
	void addMessage(String content);

	Collection<Message> messages();
}