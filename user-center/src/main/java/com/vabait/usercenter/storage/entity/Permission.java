package com.vabait.usercenter.storage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author susean
 * @createTime 2020/1/5 下午4:51
 * @description Permission
 */
@Data
public class Permission {

    private int id;
    private String zuulPrefix;
    private String servicePrefix;
    private String method;
    private String uri;
    private Date createTime;
    private Date updateTime;
}