package idv.tim.springdata.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@ComponentScan({"idv.tim.springdata.config","idv.tim.springdata.persistence"})
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
 
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  
}
