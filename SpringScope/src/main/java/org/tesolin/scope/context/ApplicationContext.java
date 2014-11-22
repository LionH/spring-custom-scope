package org.tesolin.scope.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationContext implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) {
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfig.class);
		context.setServletContext(container);
		ServletRegistration.Dynamic dispatcher = container.addServlet(
				"DispatcherServlet", new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.setAsyncSupported(true);
		dispatcher.addMapping("/");
	}
}
