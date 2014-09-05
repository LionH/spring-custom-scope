package org.tesolin.scope.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloServlet {

	@Autowired
	private ConversationExampleBase conversation;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex() {
        return "Hello world";
    }
	
	@RequestMapping(value="/*", method = RequestMethod.GET)
	public ModelAndView welcomePage(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, InterruptedException {
		PrintWriter out = response.getWriter();

		// then write the data of the response
		String username = request.getParameter("username");
		if (username != null && username.length() > 0) {
			out.println("<h2>Hello, " + username + "!</h2><br/><br/>");
			out.println(String.format("<p>Your Conversation : [%s]</p>", conversation.call()));
		}
		return null;
	}
	
}
