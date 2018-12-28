package com.github.wesleyegberto.alura.springmvc.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.wesleyegberto.alura.springmvc.model.Produto;

@EnableTransactionManagement // ativa o gerenciamento de transação
public class JPAFactoryConfig {

	@Autowired
	private Environment environment;
	
	// Desenvolvimento
	// produz o DataSource separado (para tests poder produzir o dele)
	@Bean("dataSource")
	@Profile("dev")
	public DataSource getDataSourceDev() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("1234abc@");
        dataSource.setUrl("jdbc:mysql://localhost:3306/livraria");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return dataSource;
	}

	@Bean("jpaProperties")
	@Profile(value = { "dev", "test" })
	public Properties getJpaPropertiesDevTest(){
	    Properties props = new Properties();
	    props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	    props.setProperty("hibernate.show_sql", "true");
	    props.setProperty("hibernate.hbm2ddl.auto", "update");
	    return props;
	}

	@Bean("dataSource")
	@Profile("prod")
	public DataSource getDataSourceProd() throws URISyntaxException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		
	    URI dbUrl = new URI(environment.getProperty("DATABASE_URL"));

	    String jdbcUrl = String.format("jdbc:postgresql://%s:%s%s", dbUrl.getHost(), dbUrl.getPort(), dbUrl.getPath());
	    dataSource.setUrl(jdbcUrl);
	    dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
	    dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);

		return dataSource;
	}

	@Bean("jpaProperties")
	@Profile("prod")
	private Properties getJpaPropertiesProd() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		return props;
	}
	
	@Bean // produz as EMF
	public LocalContainerEntityManagerFactoryBean createEntiyMananagerFactory(DataSource dataSource, Properties jpaProperties) {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaProperties(jpaProperties);
        factoryBean.setPackagesToScan(Produto.class.getPackage().getName());

        return factoryBean;
	}

	@Bean // produz o gerenciador de transação
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
	    return new JpaTransactionManager(emf);
	}
}
