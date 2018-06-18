package idv.tim.mvcprofile.config;

import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
import idv.tim.mvcprofile.dao.ContactDao;
import idv.tim.mvcprofile.dao.ContactDaoImpl;



@Configuration
@EnableWebMvc
@ComponentScan("idv.tim.mvcprofile.config")
@PropertySource(value= {"classpath:application.properties"})
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{
  private ApplicationContext applicationContext;
  
  @Autowired
  private DataSourceConfig dataSourceConfig;
  
  public void setApplicationContext(ApplicationContext applicationContext) {
  	  this.applicationContext = applicationContext;
  }
  
  //spring will automatically bind value of property
  //@Value("${spring.datasource.url}")
  //private String dataSourceURL;
  
  //@Value("${spring.datasource.username}")
  //private String userName;
  
  //@Value("${spring.datasource.password}")
  //private String password;
  
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
  public ContactDao getContactDAO() throws IllegalArgumentException, NamingException {
      return new ContactDaoImpl(dataSourceConfig.getDataSource());
  }
  
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  
}
