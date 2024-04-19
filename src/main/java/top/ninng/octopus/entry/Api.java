package top.ninng.octopus.entry;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * API 键值信息和返回数据配置
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Component
public class Api implements Serializable {

    /**
     * 返回 键 列表
     */
    public ArrayList<String> returnKeys = new ArrayList<>();
    /**
     * 返回 值类型 列表
     */
    public ArrayList<String> returnValueTypes = new ArrayList<>();
    /**
     * 值为 1 时，返回非数组对象
     */
    public int returnSum = 1;
    /**
     * 请求 键 列
     */
    public ArrayList<String> requestKeys = new ArrayList<>();
    /**
     * 请求 值类型 列表
     */
    public ArrayList<String> requestValueTypes = new ArrayList<>();

    public Api() {
    }

    public Api(ArrayList<String> requestKeys, ArrayList<String> requestValueTypes) {
        this.requestKeys = requestKeys;
        this.requestValueTypes = requestValueTypes;
    }

    @Override
    public String toString() {
        return "Api{" +
                "returnKeys=" + returnKeys +
                ", returnValueTypes=" + returnValueTypes +
                ", returnSum=" + returnSum +
                ", requestKeys=" + requestKeys +
                ", requestValueTypes=" + requestValueTypes +
                '}';
    }
}
