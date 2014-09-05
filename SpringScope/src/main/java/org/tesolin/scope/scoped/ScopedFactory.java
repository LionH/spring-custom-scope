package org.tesolin.scope.scoped;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tesolin.scope.multithread.MultiThreadedConversation;

@Service
public class ScopedFactory {
	
	@Autowired
	private ObjectFactory<Call> callFactory;
	
	@Autowired
	private ObjectFactory<MultiThreadedConversation> taskFactory;
	
	public Call getCall() {
		return callFactory.getObject();
	}
	
	public MultiThreadedConversation getMultiThreadedConversation() {
		return taskFactory.getObject();
	}
}
