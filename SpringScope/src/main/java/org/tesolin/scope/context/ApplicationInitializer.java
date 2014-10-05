package org.tesolin.scope.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		ServletRegistration.Dynamic registration = container.addServlet("dispatcher", 
				new DispatcherServlet(new AnnotationConfigWebApplicationContext()));
        registration.setLoadOnStartup(1);
        registration.addMapping("/dispatch/*");
	}

}
