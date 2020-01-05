package com.vabait.usercenter.common.util;

/*
Protobuf3 + Netty4: 在socket上传输多种类型的protobuf数据
http://www.cnblogs.com/Binhua-Liu/p/5577622.html
*/

public class ByteArray {

    byte bytes[];

    public ByteArray(byte bytes[]) {
        this.bytes = bytes;
    }

    public void updateByte(int value, int position) {
        byte[] tempBytes = BytesUtil.int2bytes(value, 1, BytesOrder.LittleEndian);
        bytes[position] = tempBytes[0];
    }

    public byte[] toBytes() {
        return bytes;
    }
}