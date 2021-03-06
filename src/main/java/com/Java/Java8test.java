package com.Java;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lvzhixiang
 * @since 2020/8/28
 */
public class Java8test {

    public static void main(String[] args) {
        /*List<String> strList = Arrays.asList(new String[]{"zhangsan", "lisi", "wangwu", "maliu"});
        List<Integer> intList = Arrays.asList(new Integer[]{1, null, 3,3, null, 4, null, 5});
        //intList.forEach(System.out::println);
        List<Integer> tt = intList.stream().distinct().filter(num->num!=null).limit(2).collect(Collectors.toList());
        tt.forEach(System.out::println);*/
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity entity = new HttpEntity("",httpHeaders);
        String ss = Base64.getEncoder().encodeToString("123".getBytes());
        byte[] yy = Base64.getDecoder().decode(ss);
        String str = new String(yy);
        System.out.println(ss);
        System.out.println(yy);
        System.out.println(str);
        Map map = new HashMap();
        map.putAll(new HashMap());
    }
}
