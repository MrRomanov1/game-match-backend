package pl.gamematch.GameMatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by Piotr Romanczak on 27-09-2021
 * Description: AppConfig class
 */
public class AppConfig implements WebMvcConfigurer {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    @Override
    public void addCorsMappings(CorsRegistry cors) {

        // set up cors mapping
        cors.addMapping(basePath + "/**").allowedOrigins(allowedOrigins);
    }
}



