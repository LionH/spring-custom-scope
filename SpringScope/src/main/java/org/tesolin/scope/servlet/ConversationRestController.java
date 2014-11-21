package org.tesolin.scope.servlet;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesolin.scope.beans.Message;

@RestController
public class ConversationRestController {
	
	private final Logger logger = LoggerFactory
			.getLogger(ConversationRestController.class);
	
	@Autowired
	private Conversation conversation;
	
	@RequestMapping(value="/restCalls",produces = "application/json")
    public Collection<Message> calls(){
        return conversation.calls();
    }
	
	@RequestMapping(value="/restCall", produces= "application/json")
	public void call() throws InterruptedException {
		logger.info("Request Call!!!");
		conversation.call();
	}
}
