package top.ninng.octopus.entry;

import top.ninng.octopus.utils.ReflectUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * API 返回处理器，根据接口配置返回指定信息
 *
 * @Author OhmLaw
 * @Version 1.0
 */
public class ApiReturnHandler {

    private ArrayList<String> returnKeys;
    private ArrayList<String> returnValueTypes;
    private ArrayList<Integer> returnValuesLength;
    private int sum;

    private Object getValue(String key, String type, int length) {
        ArrayList<Object> objects = new ArrayList<>();
        if (length == 1) {
            switch (type) {
                case "string":
                    return key + "_" + ((System.currentTimeMillis() / 1000) % 1000000);
                case "int":
                    return new Random().nextInt();
                case "float":
                    return new Random().nextFloat();
                case "date":
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                case "token":
                    return UUID.randomUUID();
            }
        } else {
            switch (type) {
                case "string":
                    for (int j = 0; j < length; j++) {
                        objects.add(key + "_" + (System.currentTimeMillis() / 1000) % 1000000);
                    }
                    break;
                case "int":
                    for (int j = 0; j < length; j++) {
                        objects.add(new Random().nextInt());
                    }
                    break;
                case "float":
                    for (int j = 0; j < length; j++) {
                        objects.add(new Random().nextFloat());
                    }
                    break;
                case "date":
                    for (int j = 0; j < length; j++) {
                        objects.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    }
                    break;
                case "token":
                    for (int j = 0; j < length; j++) {
                        objects.add(UUID.randomUUID());
                    }
                    break;
            }
        }
        return objects;
    }

    public Object handle() {
        Map<String, Object> properties = new HashMap<>();
        for (int i = 0; i < returnKeys.size(); i++) {
            String key = returnKeys.get(i);
            properties.put(key, getValue(key, returnValueTypes.get(i), returnValuesLength.get(i)));
        }
        if (sum == 1) {
            return ReflectUtil.getTarget(new ReflectUtil.EntityClass(), properties);
        }
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            for (int j = 0; j < returnKeys.size(); j++) {
                String key = returnKeys.get(j);
                properties.put(key, getValue(key, returnValueTypes.get(j), returnValuesLength.get(j)));
            }
            objects.add(ReflectUtil.getTarget(new ReflectUtil.EntityClass(), properties));
        }
        return objects;
    }

    public ApiReturnHandler pretreatment(Api api) {
        sum = api.getReturnSum();
        ArrayList<String> sourceReturnValueTypes = api.getReturnValueTypes();
        returnKeys = api.getReturnKeys();
        returnValueTypes = new ArrayList<>();
        returnValuesLength = new ArrayList<>();

        for (int i = 0; i < sourceReturnValueTypes.size(); i++) {
            String returnValueType = sourceReturnValueTypes.get(i);
            returnValueTypes.add(returnValueType);
            String[] split = returnValueType.split("&");
            if (split.length == 1) {
                returnValuesLength.add(1);
            } else {
                returnValuesLength.add(Integer.valueOf(split[1]));
            }
            returnValueTypes.set(i, split[0]);
        }
        return this;
    }
}
