package com.beyzataylan.bookify.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        /*Spring MVC’nin web yapılandırmasını değiştirmemizi sağlayan bir interface.*/
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") //Tüm endpointler için CORS ayarını uygula.
                        .allowedMethods("GET", "POST", "PUT", "DELETE") //Sadece bu HTTP metodlarına izin ver.
                        .allowedOrigins("*"); //Tüm domainlerden gelen isteklere izin ver.
            }
        };
    }
}
