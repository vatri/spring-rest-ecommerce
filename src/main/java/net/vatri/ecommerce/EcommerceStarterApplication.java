package net.vatri.ecommerce;

import net.vatri.ecommerce.cache.Cache;
import net.vatri.ecommerce.cache.JacksonObjectMappper;
import net.vatri.ecommerce.cache.ObjectMapper;
import net.vatri.ecommerce.cache.RedisCache;
import net.vatri.ecommerce.storage.StorageProperties;
import net.vatri.ecommerce.storage.StorageService;
import net.vatri.ecommerce.validators.GroupValidator;
import net.vatri.ecommerce.validators.OrderValidator;
import net.vatri.ecommerce.validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.validation.Validator;
import redis.clients.jedis.Jedis;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EcommerceStarterApplication{

	public static void main(String[] args) {
		SpringApplication.run(EcommerceStarterApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
//			storageService.deleteAll();
			storageService.init();
		};
	}

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    @Bean
    public Validator productValidator(){
	    return new ProductValidator();
    }
    @Bean
    public Validator groupValidator(){
        return new GroupValidator();
    }
    @Bean
    public Validator orderValidator(){
        return new OrderValidator();
    }

    @Autowired
    private Jedis jedis;

    @Bean
    public Cache cacheObject(){
        ObjectMapper om = new JacksonObjectMappper( new com.fasterxml.jackson.databind.ObjectMapper() );
        Cache cache = new RedisCache(om, jedis);
        return cache;
    }


    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;
    @Bean
    public Jedis redisCli(){
	    return new Jedis(redisHost, redisPort);
    }
}