package top.ninng.octopus.storage;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map 缓存
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Component
public class MapCache {


    @ConditionalOnProperty(prefix = "cache", value = "type", havingValue = "map", matchIfMissing = true)
    @Bean
    @Primary
    public Cache getMapCache() {
        return new MapCacheSimple();
    }

    private static class MapCacheSimple implements Cache {

        private Map<Object, Object> cache = new ConcurrentHashMap<>();
        private Map<Object, Long> timeout = new ConcurrentHashMap<>();
        private Map<Object, Long> saveTime = new ConcurrentHashMap<>();

        @Override
        public Object get(String key) {
            if (!cache.containsKey(key)) {
                return null;
            }

            long timeout = this.timeout.get(key);
            if (timeout == -1) {
                return cache.get(key);
            }
            long saveTime = this.saveTime.get(key);
            if ((System.currentTimeMillis() - saveTime) > timeout) {
                // 缓存到期
                cache.remove(key);
                this.timeout.remove(key);
                this.saveTime.remove(key);
            }
            return cache.get(key);
        }

        @Override
        public void set(String key, String value, long millisecondsToExpire) {
            timeout.put(key, millisecondsToExpire);
            cache.put(key, value);
            saveTime.put(key, System.currentTimeMillis());
        }

        @Override
        public void set(String key, String value) {
            set(key, value, -1);
        }
    }
}
