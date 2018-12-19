package com.synthesis.migration.smartdashboard.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "defaultSqlEntityManager", 
		transactionManagerRef = "defaultSqlTransactionManager", 
		basePackages = "com.synthesis.migration.smartdashboard.dao.defaultdb"
		)
public class DBConfiguartion {
	
	

	/**
	 * Default datasource definition.
	 * 
	 * @return datasource.
	 */
	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.h2.datasource")
	public DataSource defaultSqlDataSource() {
		return DataSourceBuilder
				.create()
				.build();
	}

	/**
	 * Entity manager definition. 
	 *  
	 * @param builder an EntityManagerFactoryBuilder.
	 * @return LocalContainerEntityManagerFactoryBean.
	 */
	@Primary
	@Bean(name = "defaultSqlEntityManager")
	public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(defaultSqlDataSource()).packages("com.synthesis.migration.smartdashboard.entity.defaultdb")
				.properties(hibernateProperties())
				.persistenceUnit("defaultdb")
				.build();
	}

	/**
	 * @param entityManagerFactory
	 * @return
	 */
	@Primary
	@Bean(name = "defaultSqlTransactionManager")
	public PlatformTransactionManager defaultSqlTransactionManager(@Qualifier("defaultSqlEntityManager") EntityManagerFactory entityManagerFactory) 
	{
		return new JpaTransactionManager(entityManagerFactory);
	}

	private Map<String, Object> hibernateProperties() 
	{

		Resource resource = new ClassPathResource("hibernate.properties");
		try
		{
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			return properties.entrySet().stream()
					.collect(Collectors.toMap(
							e -> e.getKey().toString(),
							e -> e.getValue())
							);
		} 
		catch (IOException e) 
		{
			return new HashMap<String, Object>();
		}
	}
}



