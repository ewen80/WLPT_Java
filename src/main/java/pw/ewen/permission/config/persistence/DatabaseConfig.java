package pw.ewen.permission.config.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
//import org.apache.tomcat.jdbc.pool.DataSource;

@Configuration
@EnableJpaRepositories(basePackages="pw.ewen.permission.repository")
public class DatabaseConfig {

	@Bean
	public DataSource datasource(){
//		DataSource dSource = new DataSource();
		DriverManagerDataSource dSource = new DriverManagerDataSource();
//		dSource.setDriverClassName("com.mysql.jdbc.Driver");
		dSource.setUrl("jdbc:mysql://localhost:3306/Permission?useSSL=false&userUnicode=true&characterEncoding=utf8");
		dSource.setUsername("root");
		dSource.setPassword("801112");
		return dSource;
	}
	
	@Bean
	public JpaVendorAdapter JpaVendorAdapter(){
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		return adapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter){
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan("pw.ewen.permission.entity");
		
//		Properties props = new Properties();
//		props.setProperty("SerializationFeature.FAIL_ON_EMPTY_BEANS", "false");
//		emfb.setJpaProperties(props);
		
		emfb.afterPropertiesSet();
		return emfb;
	}
}
