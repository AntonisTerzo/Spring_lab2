package dev.antonis.spring_lab2.configuration;

import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;

@Configuration
@EnableRetry
@EnableCaching
public class ApplicationConfig {
    @Bean
    public GeolatteGeomModule geolatteGeomModule() {
        return new GeolatteGeomModule();
    }
    @Bean
    RestClient restClient(){
        return RestClient.create();
    }
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }
}
