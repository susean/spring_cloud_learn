package com.vabait.usercenter.storage.entity;

import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @author susean
 * @createTime 2020/1/5 下午4:51
 * @description Role
 */
@Data
public class Role {

    private int id;
    private String roleName;
    private short valid;
    private Date createTime;
    private Date updateTime;
    private Set<Permission> permissions;
}