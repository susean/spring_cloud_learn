package com.vabait.common.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * @author susean
 * @createTime 2020/1/4 下午5:53
 * @description response body
 */
@Getter
@Setter
public class ResponseProto {
    public boolean success = true;
    public String message;
    public Integer code;
}
