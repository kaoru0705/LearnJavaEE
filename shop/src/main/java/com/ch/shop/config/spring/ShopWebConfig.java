package com.ch.shop.config.spring;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ch.shop.controller.shop.BoardController;
import com.ch.shop.model.board.MybatisBoardDAO;
import com.ch.shop.model.board.BoardServiceImpl;


@Configuration	// 단지 xml을 대신한 설정용 클래스에 불과해!!
@EnableWebMvc		// 필수 설정(스프링이 지원하는 MVC 프레임워크를 사용하기 위한 어노테이션)
// 일일이 빈으로 등록할 필요가 없는 많이 알려진 빈들을 가리켜
// 스프링에서는 컴포넌트라 부른다. 또한 이 컴포넌트들은 패키지 위치만 설정해놓으면 스프링이 알아서 찾아내서(검색)
// 인스턴스를 자동으로 만들어준다
// MVC에서의 Controller는 @Controller 를 붙임
// MVC에서의 DAO는 @Repository 를 붙임
// MVC에서의 Service는 @Service 를 붙임
// MVC에서의 특정 분류가 딱히 없음에도 자동으로 올리고 싶다면 @Component
@ComponentScan(basePackages = {"com.ch.shop.controller.shop"})

public class ShopWebConfig extends WebMvcConfigurerAdapter{
	
	// DispatcherServlet이 하위 컨트롤러로부터 반환받은 결과 페이지에 대한 정보는 사실 완전한 JSP 경로가 아니므로,
	// 이를 해석할 수 있는 자인 ViewResolver에게 맡겨야 하는데, 이 ViewResolver 중 유달리 접두어와 접미어 방식을 이해하는 
	// ViewResolver를 InternalResourceViewResolver라고 한다.. 개발자는 이 객체에게 접두어와 접미어을 사전에 등록해 놓아야 한다.
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver rv = new InternalResourceViewResolver();
		// 접두어 등록
		rv.setPrefix("/WEB-INF/views/");
		// 접미어 등록
		rv.setSuffix(".jsp");
		return rv;
	}
	
	
	// WebMvcConfigurerAdapter 이걸 상속 받아라
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		//registry.addResourceHandler("브라우저로 접근할 주소").addResourceLocations("웹애플리케이션을 기준으로 실제 정적자원이 있는 위치");
		// 여기서는 /resources/adminlte/index.html -> /static/adminlte/index.html
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
	}
	
	// Jackson 라이브러리 사용을 설정
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());	// Jackson 객체를 넣기
	}
	
}
