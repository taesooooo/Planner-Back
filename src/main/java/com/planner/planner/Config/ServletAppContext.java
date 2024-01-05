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
import com.planner.planner.Interceptor.TokenInterceptor;
import com.planner.planner.Interceptor.TokenInterceptorProxy;
import com.planner.planner.Interceptor.DataAccessAuthInterceptor;
import com.planner.planner.Interceptor.RequestMethodInterceptorProxy;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.planner.planner.Controller", "com.planner.planner.Handler", "com.planner.planner.Interceptor"})
@PropertySource("classpath:config/config.properties")
public class ServletAppContext implements WebMvcConfigurer {
	@Value("${upload.path}")
	private String baseLocation;
	
	private TokenInterceptor tokenInterceptor;
	private DataAccessAuthInterceptor dataAuthInterceptor;
	
	
	public ServletAppContext(TokenInterceptor tokenInterceptor, DataAccessAuthInterceptor reviewAuthInterceptor) {
		this.tokenInterceptor = tokenInterceptor;
		this.dataAuthInterceptor = reviewAuthInterceptor;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/images/**").addResourceLocations("file:///" + baseLocation);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// URL이 없으면 제외를 뜻함, null은 모든 requetMethod를 뜻함
		// excludePath를 먼저 체크 진행함
		
		// 토큰 확인
		TokenInterceptorProxy tokenAuthInterceptorProxy = new TokenInterceptorProxy(tokenInterceptor)
				.addPath("/api/auth/logout", RequestMethod.GET)
				.addPath("/api/users/**", null)
				.excludePath("/api/users/find-email", null)
				.excludePath("/api/users/find-password", null)
				.excludePath("/api/users/change-password", null)
				.addPath("/api/upload/file-upload", RequestMethod.POST)
				.addPath("/api/upload/files/*", RequestMethod.DELETE)
				.addPath("/api/notifications/**", null)
				.eitherAddPath("/api/spots/**", RequestMethod.GET)
				.addPath("/api/spots/likes", RequestMethod.POST)
				.addPath("/api/spots/likes/*", RequestMethod.DELETE)
				.eitherAddPath("/api/planners", RequestMethod.GET)
				.eitherAddPath("/api/planners/**", RequestMethod.GET)
				.addPath("/api/planners", RequestMethod.POST)
				.addPath("/api/planners/**", RequestMethod.POST)
				.addPath("/api/planners/**", RequestMethod.PATCH)
				.addPath("/api/planners/**", RequestMethod.DELETE)
				.addPath("/api/invitation/**", RequestMethod.POST)
				.addPath("/api/invitation/**", RequestMethod.DELETE)
				.eitherAddPath("/api/reviews", RequestMethod.GET)
				.eitherAddPath("/api/reviews/*", RequestMethod.GET)
				.addPath("/api/reviews", RequestMethod.POST)
				.addPath("/api/reviews/*", RequestMethod.PATCH)
				.addPath("/api/reviews/*", RequestMethod.DELETE)
				.addPath("/api/reviews/*/comments/*", null);
		
		
		// 토큰을 이용한 데이터 권한 확인
		RequestMethodInterceptorProxy authInterpInterceptorProxy = new RequestMethodInterceptorProxy(dataAuthInterceptor)
				.addPath("/api/users/**", null)
				.excludePath("/api/users/search-member", null)
				.excludePath("/api/users/find-email", null)
				.excludePath("/api/users/find-password", null)
				.excludePath("/api/users/change-password", null)
				.addPath("/api/upload/files/*", RequestMethod.DELETE)
				.addPath("/api/notifications/**", null)
				.addPath("/api/planners/**", RequestMethod.POST)
				.addPath("/api/planners/**", RequestMethod.PATCH)
				.addPath("/api/planners/**", RequestMethod.DELETE)
				.excludePath("/api/planners/*/like", RequestMethod.POST)
				.addPath("/api/invitation/**", null)
				.addPath("/api/reviews/*", RequestMethod.PATCH)
				.addPath("/api/reviews/*", RequestMethod.DELETE)
				.addPath("/api/reviews/*/comments/*", RequestMethod.PATCH)
				.addPath("/api/reviews/*/comments/*", RequestMethod.DELETE);
		
		registry.addInterceptor(tokenAuthInterceptorProxy);
		registry.addInterceptor(authInterpInterceptorProxy);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PostTypeConverter());
		registry.addConverter(new SortCriteriaConverter());
	}
}
