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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// 이 클래스는 로직 작성용이 아니라, 전통적으로 사용해왔던 스프링의 번들 등록하는 용도의 xml을 대신하기 위한 자바 클래스이다.
// 특히, 이 클래스에 등록될 번들은 비즈니스 로직을 처리하는 모델영역의 번들이므로, 서블릿 수준의 스프링컨테이너가 사용해서는 안되며
// 모든 서블릿이 접근할 수 있는 객체인 ServletContext 수준에서의 스프링컨테이너가 이 클래스를 읽어들여 번들의 인스턴스를 관리해야 한다..
@Configuration	// xml을 대신할 거야!
@ComponentScan(basePackages = {"com.ch.shop.model", "com.ch.shop.util"})
@EnableTransactionManagement
public class RootConfig extends WebMvcConfigurerAdapter{
	/*context.xml 등에 명시된 외부 자원을 JNDI 방식으로 읽어들일 수 있는 스프링의 객체*/ 
	@Bean
	public JndiTemplate jndiTemplate() {
		return new JndiTemplate();
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver rv = new InternalResourceViewResolver();
		rv.setPrefix("/WEB-INF/views/");
		rv.setSuffix(".jsp");
		return rv;
	}


	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/photo/**").addResourceLocations("file:/C:/shopdata/product/");
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());	// Jackson 객체를 넣기
	}
	

	@Bean
	public DataSource dataSource() throws NamingException{
		JndiTemplate jndi = new JndiTemplate();
		return jndi.lookup("java:comp/env/jndi/mysql", DataSource.class);
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/ch/shop/config/mybatis/config.xml"));
		
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		return sqlSessionFactoryBean.getObject();
	}
	
	/*-------------------------------------------
	  4) SqlSessionTemplat 빈 등록
	  mybatis 사용 시 쿼리문 수행을 위해서는 SqlSession을 수행
	 ------------------------------------------*/
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	/*-------------------------------------------
	  메일에 사용될 비밀번호를 가진 빈 등록
	 ------------------------------------------*/
	@Bean
	public String emailPassword(JndiTemplate jndiTemplate) throws Exception{
		return (String)jndiTemplate.lookup("java:comp/env/email/app/password");
	}
}
