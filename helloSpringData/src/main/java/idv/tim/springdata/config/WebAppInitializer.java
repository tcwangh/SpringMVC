package idv.tim.springdata.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer{
	
	public void onStartup(ServletContext container) throws ServletException {
		
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(WebConfig.class);
		dispatcherContext.setServletContext(container);
		ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	
	}

}
