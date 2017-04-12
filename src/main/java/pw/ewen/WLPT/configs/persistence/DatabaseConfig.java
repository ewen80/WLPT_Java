package pw.ewen.WLPT.configs.persistence;

//import org.apache.tomcat.jdbc.pool.DataSource;

//@Configuration
//@EnableJpaRepositories(basePackages="pw.ewen.WLPT.repositories")
public class DatabaseConfig {

//	@Bean
//	public DataSource datasource(){
////		DataSource dSource = new DataSource();
//		DriverManagerDataSource dSource = new DriverManagerDataSource();
////		dSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dSource.setUrl("jdbc:mysql://localhost:3306/Permission?useSSL=false&userUnicode=true&characterEncoding=utf8");
//		dSource.setUsername("root");
//		dSource.setPassword("801112");
//		return dSource;
//	}
//
//	@Bean
//	public JpaVendorAdapter jpaVendorAdapter(){
//		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//		adapter.setDatabase(Database.MYSQL); //采用MYSQL数据库
////		adapter.setDatabase(Database.H2); //采用H2数据库
//		adapter.setShowSql(true);
//		adapter.setGenerateDdl(false);
//		return adapter;
//	}
//
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//			DataSource dataSource,
//			JpaVendorAdapter jpaVendorAdapter){
//		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
//		emfb.setDataSource(dataSource);
//		emfb.setJpaVendorAdapter(jpaVendorAdapter);
//		emfb.setPackagesToScan("pw.ewen.WLPT.domains");
//
////		Properties props = new Properties();
////		props.setProperty("SerializationFeature.FAIL_ON_EMPTY_BEANS", "false");
////		emfb.setJpaProperties(props);
//
//		emfb.afterPropertiesSet();
//		return emfb;
//	}
}
