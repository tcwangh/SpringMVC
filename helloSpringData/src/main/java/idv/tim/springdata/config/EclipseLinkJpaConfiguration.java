package idv.tim.springdata.config;

//https://bitbucket.org/flexguse/spring-data-jpa-demo/src
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="idv.tim.springboot.persistence", entityManagerFactoryRef = "getEntityManagerFactoryBean")
public class EclipseLinkJpaConfiguration{
	
	@Bean
	public DataSource getDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("java:jboss/datasources/appDataSource");
		jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
		jndiObjectFactoryBean.setLookupOnStartup(false);
		jndiObjectFactoryBean.afterPropertiesSet();
		//jndiObjectFactoryBean.setResourceRef(true);
		return (DataSource) jndiObjectFactoryBean.getObject();
	}
	
	@Bean
    public EclipseLinkJpaVendorAdapter getEclipseLinkJpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.MySQLPlatform");
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }
	
	@Bean
    public JpaDialect jpaDialect() {
        return new EclipseLinkJpaDialect();
    }
		
	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() throws IllegalArgumentException, NamingException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        Properties props = new Properties();
        props.put("eclipselink.weaving", "static");
        props.setProperty("eclipselink.logging.level", "FINEST");
        props.setProperty("eclipselink.logging.parameters", "true");
        em.setDataSource(getDataSource());
        em.setJpaDialect(jpaDialect());
        em.setPackagesToScan("idv.tim.springdata.model");
        em.setPersistenceUnitName("appDataSourceJPA");
        em.setJpaProperties(props);
        //LoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        //em.setLoadTimeWeaver(loadTimeWeaver);
        //DatabasePlatform dp = new MySQLPlatform();
        em.setJpaVendorAdapter(getEclipseLinkJpaVendorAdapter());
        return em;
    }
	
	
	@Bean(name="transactionManager") 
	public JpaTransactionManager jpaTransManager() throws IllegalArgumentException, NamingException{
		JpaTransactionManager jtManager = new JpaTransactionManager(
				getEntityManagerFactoryBean().getObject());
		return jtManager;
	}

}
