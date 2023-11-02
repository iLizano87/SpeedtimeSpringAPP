package com.speedtime.chrono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ChronoApplication {

	@Configuration
	public class StaticResourceConfiguration implements WebMvcConfigurer {

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(0);
		}

		public static void main(String[] args) {
			SpringApplication.run(ChronoApplication.class, args);
		}

		@RequestMapping("/error")
		public String handleError() {
			// Manejar la página de error aquí
			return "error";
		}
	}
}
