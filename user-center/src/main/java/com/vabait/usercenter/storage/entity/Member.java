package com.vabait.usercenter.storage.entity;

import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @author susean
 * @createTime 2020/1/5 下午4:50
 * @description entity
 */
@Data
public class Member{

    private int id;
    private String memberName;
    private String password;
    private String mobile;
    private String email;
    private short sex;
    private Date birthday;
    private Date createTime;
    private Set<Role> roles;

}