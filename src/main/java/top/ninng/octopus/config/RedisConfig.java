package top.ninng.octopus.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author OhmLaw
 * @Version 1.0
 */
@Configuration
public class RedisConfig {

    /**
     * 自定义Redis序列化器
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        //        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
        //                new Jackson2JsonRedisSerializer<Object>(Object.class);
        //        template.setDefaultSerializer(fastJsonRedisSerializer);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);

        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(factory);
        return template;
    }

//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${spring.redis.jedis.pool.max-active}")
//    private int maxActive;
//
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${spring.redis.jedis.pool.min-idle}")
//    private int minIdle;
//
//    @Bean
//    public JedisPool getJedisPool() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxTotal(maxActive);
//
//        return new JedisPool(jedisPoolConfig);
//    }
}
