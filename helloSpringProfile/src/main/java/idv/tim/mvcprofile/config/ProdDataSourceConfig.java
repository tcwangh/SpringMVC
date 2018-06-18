package idv.tim.mvcprofile.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiObjectFactoryBean;



@Configuration
@Profile("production")
public class ProdDataSourceConfig implements DataSourceConfig{
	
	private static final Logger logger = LoggerFactory.getLogger(ProdDataSourceConfig.class);
	
	  @Bean
	  public DataSource getDataSource() throws IllegalArgumentException, NamingException {
		  logger.info("get datasource from Production profile");
		  JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		  jndiObjectFactoryBean.setJndiName("java:jboss/datasources/appDataSource");
		  jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
		  jndiObjectFactoryBean.setLookupOnStartup(false);
		  jndiObjectFactoryBean.afterPropertiesSet();
		  //jndiObjectFactoryBean.setResourceRef(true);
		  return (DataSource) jndiObjectFactoryBean.getObject();
	  }
	  
}
