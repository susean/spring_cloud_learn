package com.vabait.usercenter.common.protocol;

import lombok.Data;

/**
 * @author susean
 * @createTime 2020/1/4 下午5:57
 * @description response with dataBody
 */
@Data
public class ResponseObjectProto<T> extends ResponseProto {
    public T data;
}