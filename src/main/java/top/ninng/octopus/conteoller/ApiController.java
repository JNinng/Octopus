package top.ninng.octopus.conteoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.ninng.octopus.service.ApiService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * API 控制器
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@RestController
@RequestMapping(path = "/api")
public class ApiController {

    ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping(path = "/**", method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("get: " + request.getRequestURL());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            System.out.println("\t" + name + ": " + value);
        }
        return "get ok";
    }

    @RequestMapping(path = "/**", method = RequestMethod.POST)
    public Object post(HttpServletRequest request) {
        System.out.println("post: " + request.getRequestURL());
        return apiService.post(request);
    }
}
