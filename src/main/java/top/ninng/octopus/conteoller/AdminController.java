/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.ninng.octopus.conteoller;

import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.ninng.octopus.service.AdminService;
import top.ninng.octopus.storage.ApiMappingContainer;

/**
 * 管理控制器
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Controller
public class AdminController {

    private AdminService adminService;
    private ApiMappingContainer apiMappingContainer;

    public AdminController(AdminService adminService, ApiMappingContainer apiMappingContainer) {
        this.adminService = adminService;
        this.apiMappingContainer = apiMappingContainer;
    }

    @RequestMapping("/_config")
    public ModelAndView config() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("keys", apiMappingContainer.getApiParamKeyMap());
        modelAndView.addObject("values", apiMappingContainer.getApiParamValueMap());
        modelAndView.setViewName("config");
        return modelAndView;
    }

    @RequestMapping("/_")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/_info")
    public ModelAndView info() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("info", JSON.toJSONString(adminService.info()));
        modelAndView.setViewName("info");
        return modelAndView;
    }
}
