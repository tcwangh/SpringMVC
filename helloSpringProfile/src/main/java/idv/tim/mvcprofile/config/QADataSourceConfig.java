package idv.tim.mvcprofile.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@Profile("qa")
@PropertySource(value= {"classpath:application.properties"})
public class QADataSourceConfig implements DataSourceConfig{
	
	@Value("${spring.datasource.url}")
	private String dataSourceURL;
	  
	@Value("${spring.datasource.username}")
	private String userName;
	  
	@Value("${spring.datasource.password}")
	private String password;
	
	@Bean
	public DataSource getDataSource() {
		System.out.println("get datasource from QA profile");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    //dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
	    dataSource.setUrl(dataSourceURL);
	    dataSource.setUsername(userName);
	    dataSource.setPassword(password);
	    return dataSource;
	}
}
