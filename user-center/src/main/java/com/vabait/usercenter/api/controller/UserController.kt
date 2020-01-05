package com.vabait.usercenter.api.controller

import com.vabait.usercenter.common.protocol.Const
import com.vabait.usercenter.common.protocol.ResponseObjectProto
import com.vabait.usercenter.rabbitmq.DelayedSender
import com.vabait.usercenter.storage.entity.UserEntity
import com.vabait.usercenter.storage.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@Api(value = "user-controller")
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun userLogin(userEntity: UserEntity?): ResponseObjectProto<UserEntity> {
        val objectProto: ResponseObjectProto<UserEntity> = ResponseObjectProto()
        val entity = userService.login(userEntity)
        if (entity != null) {
            objectProto.data = entity
            objectProto.code = Const.SUCCESS
        } else {
            objectProto.code = Const.ERROR_PASSWORDorNAME
        }
        return objectProto
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(userEntity: UserEntity?): ResponseObjectProto<UserEntity> {
        val objectProto: ResponseObjectProto<UserEntity> = ResponseObjectProto()
        if (userService.insert(userEntity) as Boolean) {
            objectProto.code = Const.SUCCESS
            objectProto.data = userEntity
        } else {
            objectProto.code = Const.ERROR_REGISTER
        }
        return objectProto
    }

    @ApiOperation(value = "检查用户名是否被占用")
    @ApiImplicitParams(ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "string", paramType = "path"))
    @RequestMapping(value = ["{userName}/findByUserName"], method = [RequestMethod.GET])
    fun findByUserName(@PathVariable userName: String?): ResponseObjectProto<Boolean> {
        val objectProto: ResponseObjectProto<Boolean> = ResponseObjectProto()
        if (userService.findByUserName(userName!!)) {
            objectProto.data = true
            objectProto.code = Const.SUCCESS
        } else {
            objectProto.code = Const.NOT_EXIST
        }
        return objectProto
    }

    @ApiOperation(value = "更新用户信息")
    @RequestMapping(value = ["/updateUser"], method = [RequestMethod.POST])
    fun updateUser(userEntity: UserEntity?): ResponseObjectProto<UserEntity> {
        val objectProto: ResponseObjectProto<UserEntity> = ResponseObjectProto()
        if (userService.update(userEntity)) {
            objectProto.data = userEntity
            objectProto.code = Const.SUCCESS
        } else {
            objectProto.code = Const.FAIL_UPDATE
        }
        return objectProto
    }


    @Autowired
    private val delayedSender: DelayedSender? = null

    @RequestMapping(value = ["/test"], method = [RequestMethod.GET])
    fun send() {
        delayedSender!!.send("德玛西亚", 3)
    }
}