package top.ninng.octopus.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@ConditionalOnProperty(prefix = "cache", value = "type", havingValue = "redis", matchIfMissing = false)
@Component
public class RedisCache implements Cache {

    @Value("${spring.application.name}")
    private String prefix = "octopus";
    private RedisTemplate<Object, Object> redisTemplate;

    public RedisCache(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(prefix + ":" + key);
    }

    @Override
    public void set(String key, String value, long millisecondsToExpire) {
        redisTemplate.opsForValue().set(prefix + ":" + key, value, millisecondsToExpire, TimeUnit.MILLISECONDS);
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(prefix + ":" + key, value);
    }
}
