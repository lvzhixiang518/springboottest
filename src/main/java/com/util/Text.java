package com.util;

import com.Pojo.User;

import java.util.*;
import java.util.stream.Collectors;

public class Text {

    public static void main(String[] args) {
        getJsonBody();
    }

    public static String getJsonBody(){
        User user4 = new User(UUID.randomUUID().toString(),"张三",25,"篮球");
        User user1 = new User(UUID.randomUUID().toString(),"张名飞",84,"乒乓球");
        User user2 = new User(UUID.randomUUID().toString(),"张涵韵",65,"游泳");
        User user3 = new User(UUID.randomUUID().toString(),"周星驰",15,"足球");
        User user5 = new User(UUID.randomUUID().toString(),"你大爷",36,"跳水");
        User user6 = new User(UUID.randomUUID().toString(),"他大爷",35,"跑步");

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);
        list.add(user6);

        List<User> listPlus = list.stream().filter(str -> str.getAge()>20).sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());
        listPlus.forEach(System.out::println);
        return null;
    }

}
