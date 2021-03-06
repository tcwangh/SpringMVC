package idv.tim.repository.config;

import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
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


@Configuration
@EnableWebMvc
@ComponentScan({"idv.tim.repository.config","idv.tim.repository.persistence"})
@PropertySource(value= {"classpath:application.properties"})
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{
  private ApplicationContext applicationContext;
  
  public void setApplicationContext(ApplicationContext applicationContext) {
  	  this.applicationContext = applicationContext;
  }
     
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
      
  //@Bean
  //public JdbcTemplate jdbcTemplate(DataSource dataSource) {
  //    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
  //    jdbcTemplate.setResultsMapCaseInsensitive(true);
  //    return jdbcTemplate;
  //}
    
  
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  
}
