package org.tesolin.scope.servlet;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DispatcherServlet {

	@Autowired
	private Conversation conversation;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String showIndex() {
		return "Hello world";
	}

	@RequestMapping(value = "/call", method = RequestMethod.GET)
	public String call(@RequestParam String username, Model model) throws IOException,
			InterruptedException {

		if (StringUtils.isNotBlank(username)) {
			model.addAttribute("username", username);
			model.addAttribute("call", conversation.call());
		}
		
		model.addAttribute("tableView", conversation.calls());
		return "callsOutput";
	}
	
	@RequestMapping(value = "/calls", method = RequestMethod.GET)
	public String calls(Model model) throws IOException,
			InterruptedException {
		
		model.addAttribute("tableView", conversation.calls());
		return "callsOutput";
	}

}
