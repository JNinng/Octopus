package top.ninng.octopus.entry;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author OhmLaw
 * @Version 1.0
 */
public class Api {

    private ArrayList<ApiItem> apis;

    public Api() {
    }

    public Api(ArrayList<ApiItem> apis) {
        this.apis = apis;
    }

    @Override
    public String toString() {
        return "Api{" +
                "apis=" + apis +
                '}';
    }

    public ArrayList<ApiItem> getApis() {
        return apis;
    }

    public void setApis(ArrayList<ApiItem> apis) {
        this.apis = apis;
    }

    public static class ApiItem {

        private String path;
        private String type;
        private List<String> param;
        private List<String> returnParam;
        private boolean cache;

        public ApiItem() {
        }

        public ApiItem(String path, String type, List<String> param, List<String> returnParam, boolean cache) {
            this.path = path;
            this.type = type;
            this.param = param;
            this.returnParam = returnParam;
            this.cache = cache;
        }

        @Override
        public String toString() {
            return "ApiItem{" +
                    "path='" + path + '\'' +
                    ", type='" + type + '\'' +
                    ", param=" + param +
                    ", returnParam=" + returnParam +
                    ", cache=" + cache +
                    '}';
        }

        public List<String> getParam() {
            return param;
        }

        public void setParam(List<String> param) {
            this.param = param;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public List<String> getReturnParam() {
            return returnParam;
        }

        public void setReturnParam(List<String> returnParam) {
            this.returnParam = returnParam;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isCache() {
            return cache;
        }

        public void setCache(boolean cache) {
            this.cache = cache;
        }
    }
}
