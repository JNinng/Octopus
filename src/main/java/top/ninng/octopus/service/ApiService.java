package top.ninng.octopus.service;

import org.springframework.stereotype.Service;
import top.ninng.octopus.storage.ApiContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

/**
 * API 服务
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Service
public class ApiService {

    private int index = 0;
    private ApiContainer apiContainer;

    public ApiService(ApiContainer apiContainer) {
        this.apiContainer = apiContainer;
    }

    private String getTypeData(String type) {
        switch (type) {
            case "token":
                return String.valueOf(UUID.randomUUID());
            case "string":
                return "string" + (index++);
            case "int":
                return String.valueOf(new Random().nextInt(10000));
            case "flot":
                return String.valueOf(new Random().nextFloat());
            default:
                return "";
        }
    }

    /**
     * @param request
     * @return
     */
    public Object post(HttpServletRequest request) {
        String requestURL = String.valueOf(request.getRequestURL());
        Enumeration<String> parameterNames = request.getParameterNames();

        ArrayList<String> paramsKey = new ArrayList<>();
        ArrayList<String> paramsValue = new ArrayList<>();
        // 获取预期参数
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            paramsKey.add(name);
            paramsValue.add(value);
        }
        if (!apiContainer.containsUrl(requestURL)) {
            apiContainer.put(requestURL, paramsKey, paramsValue);
        }
        return apiContainer.getReturn(requestURL);
    }
}
