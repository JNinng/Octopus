package top.ninng.octopus.storage;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import top.ninng.octopus.entry.Api;
import top.ninng.octopus.entry.ApiReturnHandler;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 存储来访 {@link top.ninng.octopus.service.ApiService#post(HttpServletRequest)} url 映射
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Component
public class ApiContainer {

    private static File configFile = new File(System.getProperty("user.dir") + File.separator + "config.yaml");

    /**
     * Map<url,api>
     */
    private Map<String, Api> apiMap = new ConcurrentHashMap<>();
    private Map<String, ApiReturnHandler> apiReturnHandlerMap = new ConcurrentHashMap<>();
    private String error = "";

    private boolean checkReturn(Api api) {
        boolean isLegal = true;
        List<String> returnKeys = api.getReturnKeys();
        List<String> returnValueTypes = api.getReturnValueTypes();
        if (api.getReturnSum() < 1 || returnKeys.size() != returnValueTypes.size()) {
            error = "返回数量或键值对匹配错误";
            return false;
        }

        String reg = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        for (String returnKey : returnKeys) {
            if (!returnKey.matches(reg) && !returnKey.equals("")) {
                isLegal = false;
                error = "键名非法";
            }
        }

        for (String returnValueType : returnValueTypes) {
            String[] split = returnValueType.split("&");
            if (!api.getTypes().contains(split[0]) && !split[0].equals("")) {
                isLegal = false;
                error = "值类型非法";
            }
        }
        return isLegal;
    }

    public boolean containsUrl(String url) {
        return apiMap.containsKey(url);
    }

    public Map<String, Api> getApiMap() {
        return apiMap;
    }

    public Map<String, ArrayList<String>> getApiParamKeyMap() {
        Map<String, ArrayList<String>> apiParamKeyMap = new HashMap<>();
        apiMap.forEach((s, api) -> apiParamKeyMap.put(s, api.requestKeys));
        return apiParamKeyMap;
    }

    public Map<String, List<String>> getApiParamValueMap() {
        if (apiMap == null) {
            return new HashMap<>();
        }
        Map<String, List<String>> apiParamValueMap = new HashMap<>();
        apiMap.forEach((s, api) -> apiParamValueMap.put(s, api.requestValueTypes));
        return apiParamValueMap;
    }

    public String getError() {
        return error;
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
            } else {
                updateHandler();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object getReturn(String url) {
        return apiReturnHandlerMap.get(url).handle();
    }

    public ArrayList<String> getUrls() {
        return new ArrayList<>(apiMap.keySet());
    }

    public void put(String url, ArrayList<String> paramsKey, ArrayList<String> paramsValue) {
        apiMap.put(url, new Api(paramsKey, paramsValue));
        updateHandler();
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

    /**
     * 更新 API 中返回信息配置
     */
    public boolean updateApiReturnConfig(Api updateApi) {
        // 移除空配置
        updateApi.setReturnKeys(updateApi.getReturnKeys().stream().filter(s -> !s.equals("")).collect(Collectors.toCollection(ArrayList::new)));
        updateApi.setReturnValueTypes(updateApi.getReturnValueTypes().stream().filter(s -> !s.equals("")).collect(Collectors.toCollection(ArrayList::new)));
        // 校验返回配置
        if (!checkReturn(updateApi)) {
            return false;
        }
        apiMap.computeIfPresent(updateApi.getUrl(), (s, api) -> {
            api.setReturnSum(updateApi.getReturnSum());
            api.setReturnKeys(updateApi.getReturnKeys());
            api.setReturnValueTypes(updateApi.getReturnValueTypes());
            return api;
        });
        updateHandler();
        saveToFile();
        return true;
    }

    public void updateHandler() {
        for (String url : apiMap.keySet()) {
            apiReturnHandlerMap.put(url, new ApiReturnHandler().pretreatment(apiMap.get(url)));
        }
    }
}
