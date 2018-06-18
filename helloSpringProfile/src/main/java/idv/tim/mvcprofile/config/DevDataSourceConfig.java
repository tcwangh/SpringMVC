package idv.tim.mvcprofile.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("development")
public class DevDataSourceConfig implements DataSourceConfig{
	
	  @Bean
	  public DataSource getDataSource() {
		  System.out.println("get datasource from development profile");
		  return new EmbeddedDatabaseBuilder()
				  .generateUniqueName(false)
				  .setName("testdb")
				  .setType(EmbeddedDatabaseType.H2)
				  .addScript("classpath:schema_h2.sql")
				  .addScript("classpath:data_h2.sql")
				  .setScriptEncoding("UTF-8")
				  .ignoreFailedDrops(true)
				  .build();
	      	//jdbc:h2:mem:testdb 
	  }
}
