package com.cicdez.kvbs;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Bytes {
    private Bytes() {}

    public static byte[] fromShort(short value) {
        return ByteBuffer.allocate(Short.BYTES).putShort(value).array();
    }

    public static byte[] fromInteger(int value) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
    }

    public static byte[] fromLong(long value) {
        return ByteBuffer.allocate(Long.BYTES).putLong(value).array();
    }

    public static byte[] fromFloat(float value) {
        return ByteBuffer.allocate(Float.BYTES).putFloat(value).array();
    }

    public static byte[] fromDouble(double value) {
        return ByteBuffer.allocate(Double.BYTES).putDouble(value).array();
    }

    public static byte fromChar_UTF_8(char value) {
        return (byte) value;
    }

    public static byte[] fromString(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    public static short toShort(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort();
    }

    public static int toInteger(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static long toLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static float toFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat();
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static char toChar(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getChar();
    }

    public static String toString(byte[] bytes) {
        return new String(bytes);
    }

    static byte[] fromList(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    public static byte[] part(byte[] bytes, int offset, int length) {
        byte[] part = new byte[length];
        for (int i = 0; i < length; i++) {
            part[i] = bytes[offset + i];
        }
        return part;
    }

    public static byte[] generateLengthParameter(long length) {
        if (length < 0) return null;
        byte[] longBytes = fromLong(length);
        int i = 0;
        while (longBytes[i] == 0) i++;
        int minBytes = longBytes.length - i;
        byte[] bytes = new byte[minBytes + 1];
        bytes[0] = (byte) minBytes;
        System.arraycopy(longBytes, i, bytes, 1, minBytes);
        return bytes;
    }

    public static long readLengthParameter(byte[] bytes) {
        byte[] longBytes = new byte[8];
        for (int i = 0; i < longBytes.length - bytes.length; i++) {
            longBytes[i] = 0;
        }
        for (int i = longBytes.length - bytes.length; i < longBytes.length; i++) {
            longBytes[i] = bytes[i - (longBytes.length - bytes.length)];
        }
        return toLong(longBytes);
    }

    //skip 00 (0), 09 (9), 0A (10) and 0D (14)

    public static final byte EQUALS = -0x01;

    //Types
    public static final byte ARRAY = 0x07;
    public static final byte COMPOUND = 0x08;

    public static final byte BYTE = 0x10;
    public static final byte SHORT = 0x11;
    public static final byte INTEGER = 0x12;
    public static final byte LONG = 0x13;
    public static final byte FLOAT = 0x14;
    public static final byte DOUBLE = 0x15;
    public static final byte BOOLEAN = 0x16;
    public static final byte STRING = 0x17;
}
