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

/*
 * 이 클래스는 로직을 작성하기 위함이 아니라, 애플리케이션에서 사용할 빈(객체) 들 및 그들간의 관계(weaving)을 명시하기 위한 
 * 설정 목적의 클래스이며, 쇼핑몰의 일반 유저들이 보게 되는 애플리케이션쪽 빈들을 관리한다.
 */
@Configuration	// 단지 xml을 대신한 설정용 클래스에 불과해!!
@EnableWebMvc		// 필수 설정(스프링이 지원하는 MVC 프레임워크를 사용하기 위한 어노테이션)
// 일일이 빈으로 등록할 필요가 없는 많이 알려진 빈들을 가리켜
// 스프링에서는 컴포넌트라 부른다. 또한 이 컴포넌트들은 패키지 위치만 설정해놓으면 스프링이 알아서 찾아내서(검색)
// 인스턴스를 자동으로 만들어준다
// MVC에서의 Controller는 @Controller 를 붙임
// MVC에서의 DAO는 @Repository 를 붙임
// MVC에서의 Service는 @Service 를 붙임
// MVC에서의 특정 분류가 딱히 없음에도 자동으로 올리고 싶다면 @Component
@ComponentScan(basePackages = {"com.ch.shop.controller", "com.ch.shop.model"})

public class UserWebConfig extends WebMvcConfigurerAdapter{
	
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
	
	/*
	 * 스프링이 MVC 프레임워크 중 컨트롤러 영역만을 지원하는 것이 아니라, 데이터베이스 관련 제어도 지원하므로,
	 * 지금까지 순수하게 사용해왔던 mybatis를 스프링이 지원하는 mybatis로 전환해본다.
	 * 스프링이 지원하는 데이터 연동 기술을 사용하려면, spring jdbc 라이브러리를 추가해야 한다...
	 * spring jdbc 검색 
	 */
	
	/*----------------------------------------------------------------
	 * 1) 개발자가 사용하고 싶은 데이터소스를 결정
	 * 		- 톰캣이 지원하는 JNDI를 사용할 예정
	 *----------------------------------------------------------------*/
	public DataSource dataSource() throws NamingException{
		JndiTemplate jndi = new JndiTemplate();
		return jndi.lookup("java:comp/env/jndi/mysql", DataSource.class);
	}
	
	/*----------------------------------------------------------------
	 * 2) 트랜잭션 매니저 등록
	 * 		- 스프링은 개발자가 사용하는 기술이 JDBC, Mybatis, Hibernate, JPA 이건 상관없이
	 * 			일관된 방법으로 트랜잭션을 처리할 수 있는 방법을 제공해주는데, 개발자는 자신이 사용하는 기술에 따라
	 * 			적절한 트랜잭션 매니저를 등록해야 한다.
	 * 			예) JDBC 사용 시 - DataSourceTransactionManager를 빈으로 등록해야 함
	 * 			예) Hibernate 사용 시 - HibernateTransactionManager를 빈으로 등록해야 함
	 * 			예) Mybatis 사용 시 - DataSourceTransactionManager를 빈으로 등록해야 함
	 * 										특히 Mybatis의 경우 JDBC와 동일한 DataSourceTransactionManager를 사용하는 이유는?
	 * 										사실 Mybatis는 내부적으로, JDBC를 사용하기 때문임...
	 * 			그리고 이 모든 트랜잭션 매니저의 최상단 객체가 바로 PlatformTransactionManager 이다.
	 *----------------------------------------------------------------*/
	@Bean
	public PlatformTransactionManager transactionManager(SqlSessionFactory sqlSessionFactory) {
		return new DataSourceTransactionManager(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource());
	}
	
	
	/*----------------------------------------------------------------
	 * 3) SqlSession을 관리하는 mybatis의 SqlSessionFactory(순수 mybatis에서 SqlSession들을 다루는)를 빈으로 등록
	 *----------------------------------------------------------------*/
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		
		// 순수 mybatis 프레임워크 자체에서 지원하는 객체가 아니라, mybatis-spring에서 지원하는 객체인
		// SqlSessionFactoryBean (끝에 Bean)을 이용하여 설정 xml 파일을 로드한다.
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		// 패키지에 포함된 파일의 유형이 클래스가 아닌 경우 더 이상 패키지로 표현하지 말고, 일반 디렉토리로 취급해야 한다..
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/ch/shop/config/mybatis/config.xml"));
		
		sqlSessionFactoryBean.setDataSource(dataSource());
		
		return sqlSessionFactoryBean.getObject();
	}
	
	/*----------------------------------------------------------------
	 * 4) SqlSessionTemplate 빈으로 등록
	 * mybatis 사용 시 쿼리문 수행을 위해서는 SqlSession을 이용했으나, mybatis-spring에서는 SqlSessionTemplate 객체를 사용해야 함
	 *----------------------------------------------------------------*/
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	// 스프링프레임웍을 지배하는 개발원리 중 하나인 DI를 구현하려면 개발자는 사용할 객체들을 미리 빈으로 등록해야 한다..
	
	// Autowired를 안 했다면 weaving을 해야 한다.
//	@Bean
//	public BoardDAO boardDAO() {
//		return new BoardDAO();
//	}
//	@Bean
//	public BoardService boardService(BoardDAO boardDAO) {
//		return new BoardService(boardDAO);
//	}
	
	// DispatcherServlet은 컨트롤러에 대한 매핑만 수행하면 되며, 정적자원(css, js, html, image 등)에 대해서는 직접 처리하지 않게 하기
	// DispatcherServlet이 관여하지 않는다.
	/*
	 *	<servlet-mapping>
        	<servlet-name>default</servlet-name>
        	<url-pattern>/</url-pattern>
    	</servlet-mapping>
    	
    	<servlet>
        	<servlet-name>default</servlet-name>
        	<servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
        	<init-param>
            	<param-name>debug</param-name>
            	<param-value>0</param-value>
        	</init-param>
        	<init-param>
            	<param-name>listings</param-name>
            	<param-value>false</param-value>
        	</init-param>
        	<load-on-startup>1</load-on-startup>
    	</servlet>
    	web.xml in Tomcat가 있는데 
    	web.xml DispatcherServlet이 먼저 관여해서 /resources/adminlte/index.html를 못 찾았다.
	 */
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
