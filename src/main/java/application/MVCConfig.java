package application;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter{

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("/login");
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/home").setViewName("/home");
        registry.addViewController("/book").setViewName("/book");
    }
}
