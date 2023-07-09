package com.planner.planner.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.planner.planner.Common.Converter.PostTypeConverter;
import com.planner.planner.Common.Converter.SortCriteriaConverter;
import com.planner.planner.Interceptor.AuthInterceptor;
import com.planner.planner.Interceptor.RequestMethodInterceptorProxy;
import com.planner.planner.Interceptor.DataAccessAuthInterceptor;
import com.planner.planner.Service.ReviewService;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.planner.planner.Controller", "com.planner.planner.Handler", "com.planner.planner.Config", "com.planner.planner.Interceptor"})
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
		RequestMethodInterceptorProxy methodProxyInterceptor = new RequestMethodInterceptorProxy(authInterceptor())
				.excludePath("/api/auth/**", null)
				.excludePath("/api/upload/files/**", null)
				.excludePath("/api/spots/area-codes", RequestMethod.GET)
				.excludePath("/api/spots/lists-area", RequestMethod.GET)
				.excludePath("/api/spots/lists-keyword", RequestMethod.GET)
				.excludePath("/api/spots/lists/*", RequestMethod.GET)
				.excludePath("/api/planners", RequestMethod.GET)
				.excludePath("/api/planners/*", RequestMethod.GET)
				.excludePath("/api/reviews", RequestMethod.GET)
				.excludePath("/api/reviews/*", RequestMethod.GET);
		
		RequestMethodInterceptorProxy reviewAuthInterpInterceptorProxy = new RequestMethodInterceptorProxy(reviewAuthInterceptor)
				.addPath("/api/planners/**", RequestMethod.PATCH)
				.addPath("/api/planners/**", RequestMethod.DELETE)
				.addPath("/api/reviews/*", RequestMethod.PATCH)
				.addPath("/api/reviews/*", RequestMethod.DELETE);
		
		registry.addInterceptor(methodProxyInterceptor);
		registry.addInterceptor(reviewAuthInterpInterceptorProxy);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PostTypeConverter());
		registry.addConverter(new SortCriteriaConverter());
	}
	
	

}
