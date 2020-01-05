package com.vabait.usercenter.storage.entity;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    String table = "user_tb";

    Integer insertUser(UserEntity userEntity);

    Integer updateUser(UserEntity userEntity);

    @Select("select * from " + table + " where userId = #{userId}")
    UserEntity findUserById(Long userId);

    @Select("select * from " + table)
    List<UserEntity> getAllUser();

    UserEntity login(UserEntity userEntity);

    @Select("select * from " + table + " where userName=#{userName}")
    UserEntity findByUserName(String userName);
}
