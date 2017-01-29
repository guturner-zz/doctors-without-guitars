package org.guy.rpg.dwg.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

/**
 * Declare Spring Beans here.
 * 
 * @author Guy
 */
@Configuration
@EnableJpaRepositories(basePackages = "org.guy.rpg.dwg.db.repositories")
@PropertySource("classpath:properties/datasource.properties")
public class AppConfig {

	// Values from src/main/resources/properties/datasource.properties
	@Value("${mysql.url}")
	private String dbUrl;
	@Value("${mysql.username}")
	private String dbUsername;
	@Value("${mysql.password}")
	private String dbPassword;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);

		return dataSource;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
