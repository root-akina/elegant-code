package ${base.basePackage}.project.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    /**
     * 修改spring-cache默认前缀为:
     */
    @Bean
    public CacheKeyPrefix cacheKeyPrefix() {
        return cacheName -> cacheName + ":";
    }

    /**
     * 申明缓存管理器，会创建一个切面（aspect）并触发Spring缓存注解的切点（pointcut）
     * 根据类或者方法所使用的注解以及缓存的状态，这个切面会从缓存中获取数据，
     * 将数据添加到缓存之中或者从缓存中移除某个值
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //对key的序列化操作：String
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        //对value的序列化操作：json
        GenericJackson2JsonRedisSerializer valuesSerializer = new GenericJackson2JsonRedisSerializer();
        //配置config,指定超时时间记得key val 序列化处理
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .computePrefixWith(cacheKeyPrefix())
            //指定全局默认超时时间【600S】
            .entryTtl(Duration.ofSeconds(600))
            //配置key的序列化方式
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
            //配置value的序列化方式
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valuesSerializer));
            //关闭空值的存储
            //.disableCachingNullValues();
            //使用CustomRedisCacheManager进行初始化
            //使用建造者进行初始化
           
			<#if option.datacache=="open">
			java.util.List<String> keyList=new java.util.ArrayList<>();
			//todo: 添加要处理缓存的列表
            //keyList.add("sale");  
            return new ${base.basePackage}.cache.core.CustomRedisCacheManager(redisConnectionFactory,config,keyList);
			<#else>
			return RedisCacheManager.builder(redisConnectionFactory)
                  .cacheDefaults(config)
                    .build();
			</#if>

    }
}
