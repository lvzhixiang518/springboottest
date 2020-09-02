package com.Pojo;

import lombok.Data;

@Data
public class User {
    /**
     * 用户ID
     */
    private String UId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户年龄
     */
    private Integer age;
    /**
     * 用户爱好
     */
    private String hobby;

    public User(String UId, String userName, Integer age, String hobby) {
        this.UId = UId;
        this.userName = userName;
        this.age = age;
        this.hobby = hobby;
    }
}
