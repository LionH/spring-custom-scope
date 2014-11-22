package org.tesolin.scope.servlet;

import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesolin.scope.beans.Message;

@RestController
public class ConversationRestController implements ConversationController {
	
	private final Logger logger = LoggerFactory
			.getLogger(ConversationRestController.class);
	
	@Autowired
	private Conversation conversation;
	
	/* (non-Javadoc)
	 * @see org.tesolin.scope.servlet.ConversationController#calls()
	 */
	@Override
	@RequestMapping(value="/restCalls",produces = "application/json")
    public Collection<Message> calls(){
		int length = conversation.calls().size();
        return conversation.calls().parallelStream().filter(el->el.getId()>length-30).sorted().collect(Collectors.toSet());
    }
	
	/* (non-Javadoc)
	 * @see org.tesolin.scope.servlet.ConversationController#call()
	 */
	@Override
	@RequestMapping(value="/restCall", produces= "application/json")
	public void call() throws InterruptedException {
		logger.info("Request Call!!!");
		conversation.call();
	}
}
