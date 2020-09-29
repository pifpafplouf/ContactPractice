package com.mmxx.contact.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mmxx.contact.dao.ContactDAO;
import com.mmxx.contact.dao.ContactDAOImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.mmxx.contact")
public class SpringMvcConfig implements WebMvcConfigurer {
	
	/*
	 * 
	 * example of DataBaseInfo looks like
	 * 
	 * package com.mmxx.contact.config;

		public class DataBaseInfo {	
			
			static String driver = "com.mysql.jdbc.Driver";
			static String url = "jdbc:mysql://192.168.1.110:3306/MyDataBase?autoReconnect=true&useSSL=false";
			static String user = "myUser";
			static String password = "MyPassword";
		}

	 */

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DataBaseInfo.driver);
		dataSource.setUrl(DataBaseInfo.url);
		dataSource.setUsername(DataBaseInfo.user);
		dataSource.setPassword(DataBaseInfo.password);
			
		return dataSource;			
	}
	
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		return resolver;
	}
	
	@Bean
	public ContactDAO getContactDAO() {
		return new ContactDAOImpl(getDataSource());		
	}

}
