package pl.gamematch.GameMatch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import pl.gamematch.GameMatch.model.game.*;
import pl.gamematch.GameMatch.model.user.*;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Piotr Romanczak on 27-09-2021
 * Description: RestConfig class
 */
@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    private EntityManager entityManager;

    @Autowired
    public RestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] gameUnsupportedActions = {HttpMethod.PUT,
                HttpMethod.DELETE, HttpMethod.PATCH};
        HttpMethod[] otherUnsupportedActions = {HttpMethod.POST, HttpMethod.PUT,
                HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods
        disableHttpMethods(Game.class, config, gameUnsupportedActions);
        disableHttpMethods(GameCategory.class, config, otherUnsupportedActions);
        disableHttpMethods(GameMode.class, config, otherUnsupportedActions);
        disableHttpMethods(Platform.class, config, otherUnsupportedActions);
        disableHttpMethods(User.class, config, otherUnsupportedActions);
        disableHttpMethods(Role.class, config, otherUnsupportedActions);

        // call an internal helper method
        exposeIds(config);

        // configure cors mapping
        //cors.addMapping(config.getBasePath() + "/**").allowedOrigins(allowedOrigins);

        // configure media type
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        config.useHalAsDefaultJsonMediaType(false);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids
        //

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}