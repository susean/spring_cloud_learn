package com.vabait.common.util;

/**
 * Created by hycx on 2017/9/2.
 */
public class IntWrap {
    private int value = 0;

    public IntWrap(int value) {
        setInt(value);
    }

    public void setInt(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
    }
}
