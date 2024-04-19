package top.ninng.octopus.storage;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import top.ninng.octopus.entry.Api;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
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

    private static File configFile = new File(System.getProperty("user.dir") + File.separator + "config.yaml");

    /**
     * Map<url,api>
     */
    private Map<String, Api> apiMap = new ConcurrentHashMap<>();

    public Map<String, Api> getApiMap() {
        return apiMap;
    }

    public Map<String, ArrayList<String>> getApiParamKeyMap() {
        Map<String, ArrayList<String>> apiParamKeyMap = new HashMap<>();
        apiMap.forEach((s, api) -> apiParamKeyMap.put(s, api.requestKeys));
        return apiParamKeyMap;
    }

    public Map<String, ArrayList<String>> getApiParamValueMap() {
        if (apiMap == null) {
            return new HashMap<>();
        }
        Map<String, ArrayList<String>> apiParamValueMap = new HashMap<>();
        apiMap.forEach((s, api) -> apiParamValueMap.put(s, api.requestValueTypes));
        return apiParamValueMap;
    }

    @PostConstruct
    private void getFileConfig() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        Yaml yaml = new Yaml(new Constructor(Map.class), new Representer(dumperOptions));
        try {
            apiMap = yaml.load(new FileInputStream(configFile));
            if (apiMap == null) {
                apiMap = new ConcurrentHashMap<>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getUrls() {
        return new ArrayList<>(apiMap.keySet());
    }

    public void put(String url, ArrayList<String> paramsKey, ArrayList<String> paramsValue) {
        apiMap.put(url, new Api(paramsKey, paramsValue));
        saveToFile();
    }

    private void saveToFile() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        Yaml yaml = new Yaml(new Constructor(Api.class), new Representer(dumperOptions));
        try {
            String dump = yaml.dump(apiMap);
            FileOutputStream fileOutputStream = new FileOutputStream(configFile);
            OutputStreamWriter outputStreamWriter = null;
            try {
                outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                outputStreamWriter.write(dump);
                outputStreamWriter.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
