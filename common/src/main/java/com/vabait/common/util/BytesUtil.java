package com.vabait.common.util;

/**
 * Created by hycx on 2017/9/27.
 */
public class BytesUtil {

    public static byte[] int2bytesBigEndian(int value) {
        return int2bytes(value, 4, BytesOrder.BigEndian);
    }

    public static byte[] short2bytesBigEndian(int value) {
        return int2bytes(value, 2, BytesOrder.BigEndian);
    }

    public static byte[] short2bytesLittleEndian(int value) {
        return int2bytes(value, 2, BytesOrder.LittleEndian);
    }

    public static byte[] int2bytesLittleEndian(int value) {
        return int2bytes(value, 4, BytesOrder.LittleEndian);
    }

    public static byte[] int2bytes(int value, int size, BytesOrder bytesOrder) {
        byte[] bLocalArr = new byte[size];
        for (int i = 0; i < size; i++) {
            byte b = (byte) (value >> 8 * i & 0xFF);
            if (bytesOrder == BytesOrder.BigEndian) {
                bLocalArr[size - 1 - i] = b;
            } else {
                bLocalArr[i] = b;
            }
        }
        return bLocalArr;
    }

    public static int bytes2IntBigEndian(byte[] bytes) {
        return bytes2Int(bytes, BytesOrder.BigEndian);
    }

    public static int bytes2IntLittleEndian(byte[] bytes) {
        return bytes2Int(bytes, BytesOrder.LittleEndian);
    }

    private static int bytes2Int(byte[] bytes, BytesOrder bytesOrder) {
        int iOutcome = 0;
        byte bLoop;
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            bLoop = bytes[i];
            if (bytesOrder == BytesOrder.BigEndian) {
                iOutcome += (bLoop & 0xFF) << (8 * (len - 1 - i));
            } else {
                iOutcome += (bLoop & 0xFF) << (8 * i);
            }
        }
        return iOutcome;
    }

    public static String bytes2HexString(byte[] bytes, boolean upperCase) {
        String ret = "";

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex;
        }

        if (upperCase) {
            return ret.toUpperCase();
        } else {
            return ret;
        }
    }

    public static byte[] byte2bits(int b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    public static int bits2byte(byte[] bits) {
        int value = 0;
        for (int i = 7; i >= 0; i--) {
            value += bits[i] << (7 - i);
        }
        return value;
    }
}
