package top.ninng.octopus.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import top.ninng.octopus.entry.Api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * TODO: 2024/4/16 读配置生成 api
 *
 * @Author OhmLaw
 * @Version 1.0
 */
//@Configuration
public class ApiConfig {

    private static final String CONFIG_FILE_NAME = "config.yaml";

    public ApiConfig() {
//        init();
    }

    /**
     * 获取 jar 内默认配置文件
     *
     * @return
     */
    private static InputStream getDefaultConfigFile() {
        System.out.println(ApiConfig.class.getClassLoader().getResource(CONFIG_FILE_NAME));
        return ApiConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
    }

    /**
     * 获取运行 jar 目录下的自定义配置文件
     *
     * @return
     */
    private static FileInputStream getUserConfigFile() {
        // 定位所在目录
        String dir = System.getProperty("user.dir");
        File file = new File(dir + File.separator + CONFIG_FILE_NAME);
        if (!file.exists()) {
            file = null;
        }
        FileInputStream inputStream = null;
        try {
            if (file != null) {
                System.out.println(file.getAbsolutePath());
                inputStream = new FileInputStream(file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 优先检测自定义配置，不存在则读取默认配置
     *
     * @return
     */
    private InputStream getConfigFile() {
        InputStream inputStream = getUserConfigFile();

        if (inputStream == null) {
            inputStream = getDefaultConfigFile();
        }
        return inputStream;
    }

    private void init() {
        InputStream inputStream = getConfigFile();
        if (inputStream != null) {
            DumperOptions dumperOptions = new DumperOptions();
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
//            Yaml yaml = new Yaml(dumperOptions);
//            Map<String, String> map = yaml.load(inputStream);
//            System.out.println(map);
//            Api api = yaml.load(inputStream);
//            System.out.println(api);

            Yaml yaml = new Yaml(new Constructor(Api.class), new Representer(dumperOptions));

            if (false) {
                ArrayList<String> param = new ArrayList<>();
                param.add("id&22");
                param.add("password&/*41");
                ArrayList<String> returnParam = new ArrayList<>();
                returnParam.add("token&s5d4f");
                Api.ApiItem apiItem = new Api.ApiItem("/", "post", param, returnParam, true);
                Api.ApiItem apiItem1 = new Api.ApiItem("/", "post", new ArrayList<>(param), new ArrayList<>(returnParam),
                        true);
                ArrayList<Api.ApiItem> apis = new ArrayList<>();
                apis.add(apiItem);
                apis.add(apiItem1);

                Api api = new Api(apis);
                String dump = yaml.dump(api);
                System.out.println();
                System.out.println(dump);
            }

            if (false) {
                Api api1 = yaml.load(inputStream);
                System.out.println();
                System.out.println(api1);
            }
        }
    }
}
