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
        registry.addViewController("/tea").setViewName("/tea");
        registry.addViewController("/403").setViewName("/403");
        registry.addViewController("/stationery").setViewName("/stationery");
        registry.addViewController("/employee/add_book").setViewName("employee/add_book");
        registry.addViewController("/employee/update_book").setViewName("employee/update_book");
        registry.addViewController("/employee/add_st").setViewName("employee/add_st");
        registry.addViewController("/employee/add_tea").setViewName("employee/add_tea");
        registry.addViewController("/employee/view").setViewName("employee/view");
        registry.addViewController("/user/buy_book").setViewName("user/buy_book");
        registry.addViewController("/user/buy_st").setViewName("user/buy_st");
        registry.addViewController("/user/buy_tea").setViewName("user/buy_tea");
        registry.addViewController("/user/generate").setViewName("user/generate");
        registry.addViewController("/employee/generate").setViewName("employee/generate");
        registry.addViewController("/admin/view").setViewName("admin/view");
        registry.addViewController("/admin/update").setViewName("admin/update");
        registry.addViewController("/admin/add").setViewName("admin/add");
    }
}
