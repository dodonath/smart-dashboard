package com.synthesis.migration.smartdashboard.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		entityManagerFactoryRef = "omniaSqlEntityManager", 
		transactionManagerRef = "omniaSqlTransactionManager", 
		basePackages = "com.synthesis.migration.smartdashboard.dao.omniadb"
		)
public class OmniaDBConfiguration {
	
	
	@Value(value = "${omnia.datasource.url}")
	private String defaultPackageToScan;
	
	

	/**
	 * Default datasource definition.
	 * 
	 * @return datasource.
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.omnia.datasource")
	public DataSource omniaSqlDataSource() {
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
	@Bean(name = "omniaSqlEntityManager")
	public LocalContainerEntityManagerFactoryBean omniaSqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(omniaSqlDataSource()).packages("com.synthesis.migration.smartdashboard.entity.omniadb")
				.properties(hibernateProperties())
				.persistenceUnit("omnia")
				.build();
	}

	/**
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean(name = "omniaSqlTransactionManager")
	public PlatformTransactionManager omniaSqlTransactionManager(@Qualifier("omniaSqlEntityManager") EntityManagerFactory entityManagerFactory) 
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
