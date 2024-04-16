package top.ninng.octopus.service;

import org.springframework.stereotype.Service;
import top.ninng.octopus.entry.Info;

/**
 * 管理服务
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Service
public class AdminService {

    Info info;

    public AdminService(Info info) {
        this.info = info;
    }

    /**
     * 获取系统基础信息
     *
     * @return
     */
    public Info info() {
        return info;
    }
}
