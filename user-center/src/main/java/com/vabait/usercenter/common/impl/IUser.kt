package com.vabait.usercenter.common.impl

import com.vabait.usercenter.storage.entity.UserEntity

interface IUser {
    fun insert(userEntity: UserEntity?): Boolean
    fun delete(userId: Long?): Boolean
    fun findByUserName(userName: String): Boolean
    fun getUser(userId: Long?): UserEntity?
    fun update(userEntity: UserEntity?): Boolean
    fun login(userEntity: UserEntity?): UserEntity?
}