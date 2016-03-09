package config.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by Dmitriy on 09.03.2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"config", "controller"})
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/WEB-INF/views/");
//        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/WEB-INF/views/");
//        registry.addResourceHandler("/img/**")
//                .addResourceLocations("/img/");
//        registry.addResourceHandler("/js/**")
//                .addResourceLocations("/js/");
    }
}
