package top.ninng.octopus.entry;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * API 键值信息和返回数据配置
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Component
public class Api implements Serializable {

    public String url = "";
    /**
     * 返回 键 列表
     */
    public List<String> returnKeys = new ArrayList<>();
    /**
     * 返回 值类型 列表
     */
    public List<String> returnValueTypes = new ArrayList<>();
    /**
     * 值为 1 时，返回非数组对象
     */
    @JSONField(name = "returnSum")
    public int returnSum = 1;
    /**
     * 请求 键 列
     */
    public List<String> requestKeys = new ArrayList<>();
    /**
     * 请求 值类型 列表：string,int,float,token
     * <p>
     * string&4: 字符串数值，长度为 4
     */
    public List<String> requestValueTypes = new ArrayList<>();
    private ArrayList<String> types;

    public Api() {
        init();
    }

    public Api(ArrayList<String> requestKeys, ArrayList<String> requestValueTypes) {
        this.requestKeys = requestKeys;
        this.requestValueTypes = requestValueTypes;
        init();
    }

    @Override
    public String toString() {
        return "Api{" +
                "url='" + url + '\'' +
                ", returnKeys=" + returnKeys +
                ", returnValueTypes=" + returnValueTypes +
                ", returnSum=" + returnSum +
                ", requestKeys=" + requestKeys +
                ", requestValueTypes=" + requestValueTypes +
                '}';
    }

    public List<String> getRequestKeys() {
        return requestKeys;
    }

    public void setRequestKeys(List<String> requestKeys) {
        this.requestKeys = requestKeys;
    }

    public List<String> getRequestValueTypes() {
        return requestValueTypes;
    }

    public void setRequestValueTypes(List<String> requestValueTypes) {
        this.requestValueTypes = requestValueTypes;
    }

    public List<String> getReturnKeys() {
        return returnKeys;
    }

    public void setReturnKeys(List<String> returnKeys) {
        this.returnKeys = returnKeys;
    }

    public int getReturnSum() {
        return returnSum;
    }

    public void setReturnSum(int returnSum) {
        this.returnSum = returnSum;
    }

    public List<String> getReturnValueTypes() {
        return returnValueTypes;
    }

    public void setReturnValueTypes(List<String> returnValueTypes) {
        this.returnValueTypes = returnValueTypes;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void init() {
        types = new ArrayList<>();
        types.add("string");
        types.add("int");
        types.add("float");
        types.add("date");
        types.add("token");
    }
}
