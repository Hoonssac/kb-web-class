package org.scoula.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc // FrontController (DispatcherServlet)
@ComponentScan(basePackages = {
	"org.scoula.controller",
	"org.scoula.exception",
	"org.scoula.board.controller",
	"org.scoula.member.controller",
	"org.scoula.travel.controller",
}) // Spring MVC용 컴포넌트 등록을 위한 스캔 패키지
public class ServletConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		converters.add(stringConverter);
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setDefaultCharset(Charset.forName("UTF-8"));
		converters.add(jsonConverter);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/")
			.setViewName("forward:/resources/index.html");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/resources/**") // url이 /resources/로 시작하는 모든 경로 (정작 파일 등록)
			.addResourceLocations("/resources/"); // webapp/resources/ 경로로 매핑

		registry.addResourceHandler("/assets/**")
			.addResourceLocations("/resources/assets/");

		// Swagger UI 리소스를 위한 핸들러 설정
		registry.addResourceHandler("/swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		// Swagger WebJar 리소스 설정
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
		// Swagger 리소스 설정
		registry.addResourceHandler("/swagger-resources/**")
			.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/v2/api-docs")
			.addResourceLocations("classpath:/META-INF/resources/");
	}

	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		return resolver;
	}
}
