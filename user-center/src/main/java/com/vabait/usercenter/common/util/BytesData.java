package com.vabait.usercenter.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/*
Protobuf3 + Netty4: 在socket上传输多种类型的protobuf数据
http://www.cnblogs.com/Binhua-Liu/p/5577622.html
*/

public class BytesData {

    //private DataInputStream indata;
    //private DataOutputStream outdata;
    private ByteArrayInputStream inStream;
    private ByteArrayOutputStream outStream;
    private int dataSize;
    private int curPos;

    public BytesData(byte bytes[]) {
        inStream = new ByteArrayInputStream(bytes);
        dataSize = bytes.length;
    }

    public BytesData() {
        outStream = new ByteArrayOutputStream();
    }

    public void addShort(int value, BytesOrder bytesOrder) {
        try {
            if (bytesOrder == BytesOrder.BigEndian) {
                outStream.write(BytesUtil.short2bytesBigEndian(value));
            } else {
                outStream.write(BytesUtil.short2bytesLittleEndian(value));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addInt(int value, BytesOrder bytesOrder) {
        try {
            if (bytesOrder == BytesOrder.BigEndian) {
                outStream.write(BytesUtil.int2bytesBigEndian(value));
            } else {
                outStream.write(BytesUtil.int2bytesLittleEndian(value));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addBytes(byte[] bytes) {
        try {
            outStream.write(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] toBytes() {
        byte bytes[] = null;
        if (outStream != null) {
            bytes = outStream.toByteArray();
        } else if (inStream != null) {
            inStream.reset();
            bytes = new byte[dataSize];
            inStream.read(bytes, 0, dataSize);
            inStream.reset();
            inStream.skip(curPos);
        }

        return bytes;
    }

    public boolean nextInt(IntWrap value, int intSize, BytesOrder bytesOrder) {
        boolean ret = false;

        if (availableForNextSize(intSize)) {
            byte[] bytes = new byte[intSize];
            int readCount = inStream.read(bytes, 0, intSize);
            if (readCount > 0) {
                int v = 0;
                if (bytesOrder == BytesOrder.BigEndian) {
                    v = BytesUtil.bytes2IntBigEndian(bytes);
                } else {
                    v = BytesUtil.bytes2IntLittleEndian(bytes);
                }
                value.setInt(v);

                curPos += intSize;
                ret = true;
            }
        }

        return ret;
    }

    public boolean nextShort(IntWrap value, BytesOrder bytesOrder) {
        boolean ret = false;

        int intSize = 2;
        if (availableForNextSize(intSize)) {
            byte[] bytes = new byte[intSize];
            int readCount = inStream.read(bytes, 0, intSize);
            if (readCount > 0) {
                int v = 0;
                if (bytesOrder == BytesOrder.BigEndian) {
                    v = BytesUtil.bytes2IntBigEndian(bytes);
                } else {
                    v = BytesUtil.bytes2IntLittleEndian(bytes);
                }
                value.setInt(v);

                curPos += intSize;
                ret = true;
            }
        }

        return ret;
    }

    public boolean nextByte(IntWrap value) {
        boolean ret = false;

        int intSize = 1;
        if (availableForNextSize(intSize)) {
            byte[] bytes = new byte[intSize];
            int readCount = inStream.read(bytes, 0, intSize);
            if (readCount > 0) {
                int v = BytesUtil.bytes2IntBigEndian(bytes);
                value.setInt(v);

                curPos += intSize;
                ret = true;
            }
        }

        return ret;
    }

    public boolean nextBytes(byte bytes[], int size) {
        boolean ret = false;

        boolean available = availableForNextSize(size);
        if (available) {
            if (bytes != null && bytes.length >= size) {
                inStream.read(bytes, 0, size);
            } else {
                inStream.skip(size);
            }

            curPos += size;
            ret = true;
        }

        return ret;
    }

    public boolean skip(int size) {
        boolean ret = false;

        boolean available = availableForNextSize(size);
        if (available) {
            inStream.skip(size);

            curPos += size;
            ret = true;
        }

        return ret;
    }

    public boolean nextString(StringBuffer value, int size) {
        byte bytes[] = new byte[size];
        boolean ret = nextBytes(bytes, size);
        if (ret) {
            value.append(new String(bytes));
        }

        return ret;
    }

    public boolean nextStringTail(StringBuffer value) {
        boolean ret = false;

        int size = dataSize - curPos;
        if (size > 0) {
            byte bytes[] = new byte[size];
            ret = nextBytes(bytes, size);
            if (ret) {
                value.append(new String(bytes));
            }
        }
        return ret;
    }

    private boolean availableForNextSize(int size) {
        int avaliable = inStream.available();
        return curPos + size <= dataSize && avaliable >= size;
    }

}