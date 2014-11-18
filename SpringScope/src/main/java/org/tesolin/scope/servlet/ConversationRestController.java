package org.tesolin.scope.servlet;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesolin.scope.beans.Message;

@RestController
public class ConversationRestController {
	
	@Autowired
	private Conversation conversation;
	
	@RequestMapping(value="/restCalls",produces = "application/json")
    public Collection<Message> calls() throws InterruptedException {
		conversation.call();
        return conversation.calls();
    }
}
