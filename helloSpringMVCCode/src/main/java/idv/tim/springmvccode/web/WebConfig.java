package idv.tim.springmvccode.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableWebMvc
@ComponentScan("idv.tim.springmvccode.web")
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{
  private ApplicationContext applicationContext;
  
  public void setApplicationContext(ApplicationContext applicationContext) {
  	  this.applicationContext = applicationContext;
  }
  
  
  @Bean
  public ViewResolver viewResolver() {
	  InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	  resolver.setPrefix("/WEB-INF/views/");
	  resolver.setSuffix(".jsp");
	  //resolver.setExposeContextBeansAsAttributes(true);
	  resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
	  return resolver;
  }
    
      
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  
}
