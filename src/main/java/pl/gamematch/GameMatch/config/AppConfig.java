package pl.gamematch.GameMatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by Piotr Romanczak on 27-09-2021
 * Description: AppConfig class
 */
@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry cors) {

        // set up cors mapping
        cors.addMapping( "/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST");
    }
}



