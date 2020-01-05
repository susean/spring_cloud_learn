package com.vabait.usercenter.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hycx on 2017/9/20.
 */
public class StringUtil {
    public static Date dateFromStr8(String dateStr) {
        Date date = null;
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String toDateTimeStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getQueryParamUrl(String url, Map params) {
        final StringBuffer buffer = new StringBuffer();
        if (params.size() > 0) {
            params.forEach((k, v) -> {
                if (k != null && v != null) {
                    if (buffer.length() == 0) {
                        buffer.append(url + "?" + k + "=" + v);
                    } else {
                        buffer.append("&" + k + "=" + v);
                    }
                }
            });
        } else {
            buffer.append(url);
        }

        return buffer.toString();
    }

    public static String getQueryPathUrl(String url, List params) {
        final StringBuffer buffer = new StringBuffer(url);
        if (params.size() > 0) {
            params.forEach(param -> {
                buffer.append("/" + param);
            });
        }

        return buffer.toString();
    }

    public static <T> T json2Object(String json, Class<T> clss) {
        //将json字符串转换成对象
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T map = null;
        try {
            map = mapper.readValue(json, clss);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static Map json2Map(String json) {
        //将json字符串转换成对象
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = null;
        try {
            map = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
////转换对象类型
//        SomethingPOJO pojo = objectMapper.convertValue(map, SomethingPOJO.class);
////将对象转换成json字符串
//        Sting string = objectMapper.writeValueAsString(pojo);
////将json字符串转换成List
//        JavaType javaType = mapper.getTypeFactory()
//                .constructParametricType(List.class, Person.class);
//        List<Person> jsonToPersonList = objectMapper.readValue(arrayToJson, mapType);

        return map;
    }

    public static List json2List(String json) {
        //将json字符串转换成对象
        ObjectMapper objectMapper = new ObjectMapper();
        List list = null;
        try {
            list = objectMapper.readValue(json, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String object2json(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static boolean antMatch(String path, String pattern) {
        PathMatcher matcher = new AntPathMatcher();
        // 完全路径url方式路径匹配
        //String requestPath="/user/list.htm?username=aaa&departmentid=2&pageNumber=1&pageSize=20";//请求路径
        //String patternPath="/user/list.htm**";//路径匹配模式

        // 不完整路径uri方式路径匹配
        // String requestPath="/app/pub/login.do";//请求路径
        // String patternPath="/**/login.do";//路径匹配模式
        // 模糊路径方式匹配
        // String requestPath="/app/pub/login.do";//请求路径
        // String patternPath="/**/*.do";//路径匹配模式
        // 包含模糊单字符路径匹配
        //String requestPath = "/app/pub/login.do";// 请求路径
        //String patternPath = "/**/lo?in.do";// 路径匹配模式
        return matcher.match(pattern, path);
    }

    public static boolean antMatch(String path, String... patterns) {
        boolean ret = false;
        String[] arr = patterns;
        for (int i = 0; i < arr.length; i++) {
            if (antMatch(path, arr[i])) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    public static boolean arrayMatch(String path, String[] patterns) {
        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i].equals(path)) {
                return true;
            }
        }

        return false;
    }

    public static int getLength(String str) {
        if (str != null) {
            return str.length();
        }

        return 0;
    }

    public static Object[] concatArray(Object[] arr1, Object[] arr2) {
        if (arr1 != null && arr2 != null) {
            int strLen1 = arr1.length;// 保存第一个数组长度
            int strLen2 = arr2.length;// 保存第二个数组长度
            arr1 = Arrays.copyOf(arr1, strLen1 + strLen2);// 扩容
            System.arraycopy(arr2, 0, arr1, strLen1, strLen2);// 将第二个数组与第一个数组合并
        } else if (arr2 != null) {
            arr1 = arr2;
        }

        return arr1;
    }

    public static String[] objectArray2StringArray(Object[] objs) {
        String[] strs = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            strs[i] = objs[i].toString();
        }

        return strs;
    }

    /**
     * MySQL如何有效的存储IP地址及字符串IP和数值之间如何转换
     * https://blog.csdn.net/mhmyqn/article/details/48653157
     * <p>
     * A.B.C.D，转换成整数：将D左移0位，C左移8位，B左移16位，A左移24位，然后相加。
     */
    public static int ip2Int(String ip) {
        String[] ips = ip.split("\\.");
        int ipFour = 0;
        //因为每个位置最大255，刚好在2进制里表示8位
        for (String ip4 : ips) {
            Integer ip4a = Integer.parseInt(ip4);
            //这里应该用+也可以,但是位运算更快
            ipFour = (ipFour << 8) | ip4a;
        }
        return ipFour;
    }

    /**
     * 将整数转化X为对应的二进制形式，记为ABCD，每个字母表示8位，将X右移24位即可得到A，将X中的A部分置为0，右移16位即可得到B，同理得到C和D，用“.”连接起来。
     */
    public static String int2Ip(Integer ip) {
        //思路很简单，每8位拿一次，就是对应位的IP
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            int ipa = (ip >> (8 * i)) & (0xff);
            sb.append(ipa + ".");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

}
