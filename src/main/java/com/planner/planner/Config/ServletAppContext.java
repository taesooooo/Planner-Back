package com.planner.planner.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.planner.planner.Common.Converter.PostTypeConverter;
import com.planner.planner.Common.Converter.SortCriteriaConverter;
import com.planner.planner.Interceptor.AuthInterceptor;
import com.planner.planner.Interceptor.AuthInterceptorProxy;
import com.planner.planner.Interceptor.DataAccessAuthInterceptor;
import com.planner.planner.Interceptor.RequestMethodInterceptorProxy;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.planner.planner.Controller", "com.planner.planner.Handler",  "com.planner.planner.Interceptor"})
@PropertySource("classpath:config/config.properties")
public class ServletAppContext implements WebMvcConfigurer {
	@Value("${upload.path}")
	private String baseLocation;
	
	private DataAccessAuthInterceptor reviewAuthInterceptor;
	
	
	public ServletAppContext(DataAccessAuthInterceptor reviewAuthInterceptor) {
		this.reviewAuthInterceptor = reviewAuthInterceptor;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/images/**").addResourceLocations("file:///" + baseLocation);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		AuthInterceptorProxy tokenAuthInterceptorProxy = new AuthInterceptorProxy(authInterceptor())
				.excludePath("/api/auth/**", null)
				.excludePath("/api/users/find-email", null)
				.excludePath("/api/users/find-password", null)
				.excludePath("/api/users/change-password", null)
				.excludePath("/api/upload/files/**", null)
				.eitherAddPath("/api/spots/*", RequestMethod.GET)
				.eitherAddPath("/api/planners", RequestMethod.GET)
				.eitherAddPath("/api/planners/*", RequestMethod.GET)
				.eitherAddPath("/api/reviews", RequestMethod.GET)
				.eitherAddPath("/api/reviews/*", RequestMethod.GET);
		
		RequestMethodInterceptorProxy reviewAuthInterpInterceptorProxy = new RequestMethodInterceptorProxy(reviewAuthInterceptor)
				.addPath("/api/planners/**", RequestMethod.PATCH)
				.addPath("/api/planners/**", RequestMethod.DELETE)
				.addPath("/api/reviews/*", RequestMethod.PATCH)
				.addPath("/api/reviews/*", RequestMethod.DELETE)
				.addPath("/api/invitation/**", null)
				.addPath("/api/notifications/*", RequestMethod.DELETE);
		
		registry.addInterceptor(tokenAuthInterceptorProxy);
		registry.addInterceptor(reviewAuthInterpInterceptorProxy);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PostTypeConverter());
		registry.addConverter(new SortCriteriaConverter());
	}
}
