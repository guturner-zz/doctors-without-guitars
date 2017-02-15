package org.guy.rpg.dwg.config;

import org.guy.rpg.dwg.interceptors.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.rossillo.spring.web.mvc.CacheControlHandlerInterceptor;

/**
 * Used to declare MVC related config.
 * Includes interceptors.
 * 
 * @author Guy
 */

@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CacheControlHandlerInterceptor());
		registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
	}

}