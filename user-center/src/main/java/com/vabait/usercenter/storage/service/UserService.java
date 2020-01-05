package com.vabait.usercenter.storage.service;

import com.vabait.usercenter.common.impl.IUser;
import com.vabait.usercenter.storage.entity.UserEntity;
import com.vabait.usercenter.storage.entity.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUser {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public boolean insert(UserEntity userEntity) {
        return userMapper.insertUser(userEntity) == 1;
    }

    @Transactional
    @Override
    public boolean delete(Long userId) {
        return true;
    }

    @Override
    public UserEntity getUser(Long userId) {
        return null;
    }


    public List<UserEntity> getUserByUserName(String userName) {
        return userMapper.getAllUser();
    }

    @Transactional
    @Override
    public boolean update(UserEntity userEntity) {
        return true;
    }

    public UserEntity login(UserEntity userEntity) {
        return userMapper.login(userEntity);
    }

    public boolean findByUserName(String userName) {
        return userMapper.findByUserName(userName) != null;
    }
}
