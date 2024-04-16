package top.ninng.octopus.storage;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储来访 {@link top.ninng.octopus.service.ApiService#post(HttpServletRequest)} url 映射
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Component
public class ApiMappingContainer {

    /**
     * Map<url,paramKey>
     */
    private Map<String, ArrayList<String>> apiParamKeyMap = new ConcurrentHashMap<>();
    /**
     * Map<url,paramValue>
     */
    private Map<String, ArrayList<String>> apiParamValueMap = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        return "ApiMappingContainer{" +
                "apiParamKeyMap=" + apiParamKeyMap +
                ", apiParamValueMap=" + apiParamValueMap +
                '}';
    }

    public Map<String, ArrayList<String>> getApiParamKeyMap() {
        return apiParamKeyMap;
    }

    public Map<String, ArrayList<String>> getApiParamValueMap() {
        return apiParamValueMap;
    }

    public void put(String url, ArrayList<String> paramsKey, ArrayList<String> paramsValue) {
        apiParamKeyMap.put(url, paramsKey);
        apiParamValueMap.put(url, paramsValue);
    }
}
