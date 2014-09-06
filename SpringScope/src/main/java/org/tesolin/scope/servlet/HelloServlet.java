package org.tesolin.scope.servlet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloServlet {

	@Autowired
	private ConversationExampleBase conversation;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String showIndex() {
		return "Hello world";
	}

	@ResponseBody
	@RequestMapping(value = "/call", method = RequestMethod.GET)
	public String call(@RequestParam String username) throws IOException,
			InterruptedException {

		StringBuffer out = new StringBuffer();
		// then write the data of the response
		if (username != null && username.length() > 0) {
			out.append("<h2>Hello, " + username + "!</h2><br/><br/>");
			out.append(String.format("<p>Your Conversation : [%s]</p>",
					conversation.call()));
		}
		return out.toString();
	}

}
