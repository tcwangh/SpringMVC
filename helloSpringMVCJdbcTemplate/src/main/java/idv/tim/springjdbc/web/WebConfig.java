package idv.tim.springjdbc.web;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import idv.tim.springjdbc.dao.ContactDao;
import idv.tim.springjdbc.dao.ContactDaoImpl;


@Configuration
@EnableWebMvc
@ComponentScan("idv.tim.springjdbc.web")
@PropertySource(value= {"classpath:application.properties"})
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{
  private ApplicationContext applicationContext;
  
  public void setApplicationContext(ApplicationContext applicationContext) {
  	  this.applicationContext = applicationContext;
  }
  
  //spring will automatically bind value of property
  @Value("${spring.datasource.url}")
  private String dataSourceURL;
  
  @Value("${spring.datasource.username}")
  private String userName;
  
  @Value("${spring.datasource.password}")
  private String password;
  
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	  return new PropertySourcesPlaceholderConfigurer();
  }
  
  @Bean
  public ITemplateResolver templateResolver() {
	  SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
	  resolver.setApplicationContext(applicationContext);
	  resolver.setPrefix("/WEB-INF/views/");
	  resolver.setSuffix(".html");
	  resolver.setCharacterEncoding("UTF-8");
	  resolver.setTemplateMode(TemplateMode.HTML);
	  //resolver.setTemplateMode("HTML5");
	  return resolver;
  }
  
    
  @Bean
  public TemplateEngine templateEngine() {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    //engine.setEnableSpringELCompiler(true);
    engine.setTemplateResolver(templateResolver());
    return engine;
  }
  
  @Bean
  public ViewResolver viewResolver() {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine());
    resolver.setCharacterEncoding("UTF-8");
    resolver.setOrder( 1 );
    return resolver;
  }
  
  @Bean
  public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
      //dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
      dataSource.setUrl(dataSourceURL);
      dataSource.setUsername(userName);
      dataSource.setPassword(password);
      return dataSource;
  }
  
  @Bean
  public DataSource getJndiDataSource() throws IllegalArgumentException, NamingException {
	  JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
	  jndiObjectFactoryBean.setJndiName("java:jboss/datasources/appDataSource");
	  jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
	  jndiObjectFactoryBean.setLookupOnStartup(false);
	  jndiObjectFactoryBean.afterPropertiesSet();
	  //jndiObjectFactoryBean.setResourceRef(true);
	  return (DataSource) jndiObjectFactoryBean.getObject();
  }
  
  @Bean
  public DataSource getH2DataSource() {
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
  
     
  @Bean
  public ContactDao getContactDAO() throws IllegalArgumentException, NamingException {
      //return new ContactDaoImpl(getDataSource());
	  //return new ContactDaoImpl(getJndiDataSource());
	  return new ContactDaoImpl(getH2DataSource());
  }
      
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  
}
