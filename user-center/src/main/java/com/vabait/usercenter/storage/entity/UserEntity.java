package com.vabait.usercenter.storage.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class UserEntity {
    private Integer userId;
    @ApiModelProperty(name = "userName", value = "用户名", dataType = "string", required = true)
    private String userName;
    @ApiModelProperty(name = "password", value = "密码", dataType = "string", required = true)
    private String password;
    @ApiModelProperty(name = "userType", value = "用户类型", dataType = "int", required = true)
    private Integer userType;
}
