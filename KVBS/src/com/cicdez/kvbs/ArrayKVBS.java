package com.cicdez.kvbs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class ArrayKVBS<K extends KVBS> implements KVBS {
    private final List<K> array;
    private byte type;
    public ArrayKVBS(K... array) {
        this(Arrays.asList(array));
    }
    public ArrayKVBS(List<K> array) {
        this.array = array;
        this.type = (array == null || array.size() == 0) ? 0 : array.get(0).getType();
    }

    public K get(int index) {
        return array.get(0);
    }
    public boolean contains(K kvbs) {
        return array.contains(kvbs);
    }
    public int size() {
        return array.size();
    }

    public void add(K kvbs) {
        array.add(kvbs);
    }
    public void set(int index, K kvbs) {
        array.set(index, kvbs);
    }

    public void remove(K kvbs) {
        array.remove(kvbs);
    }
    public void remove(int index) {
        array.remove(index);
    }

    public byte getInnerType() {
        return type;
    }

    void setType(byte type) {
        this.type = type;
    }

    @Override
    public byte getType() {
        return Bytes.ARRAY;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(getInnerType());
        for (K kvbs : array) {
            try {
                stream.write(kvbs.generateLengthParameter());
                stream.write(kvbs.getBytes());
            } catch (IOException ignore) {}
        }

        return stream.toByteArray();
    }

    //[array-length] { [length-of-value] [value] }

    @Override
    public byte[] generateLengthParameter() {
        return Bytes.generateLengthParameter(getBytes().length);
    }

    @Override
    public Object getData() {
        return array;
    }


    public static ArrayKVBS<ByteKVBS> fromByteArray(byte... array) {
        ByteKVBS[] byteKVBS = new ByteKVBS[array.length];
        for (int i = 0; i < array.length; i++) {
            byteKVBS[i] = new ByteKVBS(array[i]);
        }
        return new ArrayKVBS<>(byteKVBS);
    }
    public static ArrayKVBS<ShortKVBS> fromShortArray(short... array) {
        ShortKVBS[] shortKVBS = new ShortKVBS[array.length];
        for (int i = 0; i < array.length; i++) {
            shortKVBS[i] = new ShortKVBS(array[i]);
        }
        return new ArrayKVBS<>(shortKVBS);
    }
    public static ArrayKVBS<IntegerKVBS> fromIntegerArray(int... array) {
        IntegerKVBS[] integerKVBS = new IntegerKVBS[array.length];
        for (int i = 0; i < array.length; i++) {
            integerKVBS[i] = new IntegerKVBS(array[i]);
        }
        return new ArrayKVBS<>(integerKVBS);
    }
    public static ArrayKVBS<LongKVBS> fromLongArray(long... array) {
        LongKVBS[] longKVBS = new LongKVBS[array.length];
        for (int i = 0; i < array.length; i++) {
            longKVBS[i] = new LongKVBS(array[i]);
        }
        return new ArrayKVBS<>(longKVBS);
    }
    public static ArrayKVBS<FloatKVBS> fromFloatArray(float... array) {
        FloatKVBS[] floatKVBS = new FloatKVBS[array.length];
        for (int i = 0; i < array.length; i++) {
            floatKVBS[i] = new FloatKVBS(array[i]);
        }
        return new ArrayKVBS<>(floatKVBS);
    }
    public static ArrayKVBS<DoubleKVBS> fromDoubleArray(double... array) {
        DoubleKVBS[] doubleKVBS = new DoubleKVBS[array.length];
        for (int i = 0; i < array.length; i++) {
            doubleKVBS[i] = new DoubleKVBS(array[i]);
        }
        return new ArrayKVBS<>(doubleKVBS);
    }
    public static ArrayKVBS<StringKVBS> fromStringArray(String... array) {
        StringKVBS[] stringKVBS = new StringKVBS[array.length];
        for (int i = 0; i < array.length; i++) {
            stringKVBS[i] = new StringKVBS(array[i]);
        }
        return new ArrayKVBS<>(stringKVBS);
    }
    public static ArrayKVBS<BooleanKVBS> fromBooleanArray(boolean... array) {
        BooleanKVBS[] booleanKVBS = new BooleanKVBS[array.length];
        for (int i = 0; i < array.length; i++) {
            booleanKVBS[i] = new BooleanKVBS(array[i]);
        }
        return new ArrayKVBS<>(booleanKVBS);
    }
}
