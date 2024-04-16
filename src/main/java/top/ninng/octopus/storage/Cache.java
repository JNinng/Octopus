package top.ninng.octopus.storage;

/**
 * 缓存
 *
 * @Author OhmLaw
 * @Version 1.0
 */
public interface Cache {

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 设置新值，并设置过期时间
     *
     * @param key
     * @param value
     * @param millisecondsToExpire 毫秒
     */
    void set(String key, String value, long millisecondsToExpire);

    /**
     * 设置新值
     *
     * @param key
     * @param value
     */
    void set(String key, String value);
}
