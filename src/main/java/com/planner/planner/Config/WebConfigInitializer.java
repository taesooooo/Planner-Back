package com.planner.planner.Config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class WebConfigInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		rootAppContext.register(RootAppDevContext.class);

		servletContext.addListener(new ContextLoaderListener(rootAppContext));

		AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
		servletAppContext.register(ServletAppContext.class);
		servletAppContext.register(SecurityContext.class);
		servletAppContext.register(JwtContext.class);

		ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcherServlet",new DispatcherServlet(servletAppContext));
		dynamic.setLoadOnStartup(1);
		dynamic.addMapping("/");

		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter("UTF-8"));
		filter.addMappingForUrlPatterns(null, false, "/*");
		
		System.out.println(System.getProperty("spring.profiles.active"));
	}

}
