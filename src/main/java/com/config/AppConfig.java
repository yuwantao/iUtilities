package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Created by yuwt on 2016/7/20.
 */
@Configuration
public class AppConfig {
	@Bean
	public ViewResolver freemarkerViewResolver() {
		FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
		freeMarkerViewResolver.setCache(true);
		freeMarkerViewResolver.setSuffix(".ftl");
		freeMarkerViewResolver.setContentType("text/html;charset=utf-8");
		freeMarkerViewResolver.setOrder(5);

		return freeMarkerViewResolver;
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		config.setTemplateLoaderPath("/WEB-INF/freemarker/");
		config.setDefaultEncoding("utf-8");
		return config;
	}
}
