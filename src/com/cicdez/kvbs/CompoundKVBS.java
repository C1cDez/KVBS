package com.cicdez.kvbs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CompoundKVBS implements KVBS {
    private final Map<String, KVBS> map;

    public CompoundKVBS(Map<String, KVBS> defaultMap) {
        this.map = defaultMap;
    }
    public CompoundKVBS() {
        this(new HashMap<>());
    }

    public void put(String key, KVBS kvbs) {
        checkSelfReference(this, kvbs);
        map.put(key, kvbs);
    }
    public KVBS get(String key) {
        return map.get(key);
    }
    public void remove(String key) {
        map.remove(key);
    }
    public void remove(String key, KVBS kvbs) {
        map.remove(key, kvbs);
    }
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }
    public boolean containsKVBS(KVBS kvbs) {
        return map.containsValue(kvbs);
    }
    public int size() {
        return map.size();
    }

    @Override
    public byte getType() {
        return Bytes.COMPOUND;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (String key : map.keySet()) {
            KVBS value = map.get(key);
            byte[] valueBytes = value.getBytes();
            byte[] globalLength = Bytes.generateLengthParameter(key.length() + valueBytes.length + 1 + 1);
            try {
                stream.write(globalLength);
                stream.write(key.getBytes(StandardCharsets.UTF_8));
                stream.write(Bytes.EQUALS);
                stream.write(value.getType());
                stream.write(valueBytes);
            } catch (IOException ignore) {}
        }
        return stream.toByteArray();
    }

    //[name-length] [name] [value-length] [value]

    @Override
    public byte[] generateLengthParameter() {
        return Bytes.generateLengthParameter(getBytes().length);
    }

    @Override
    public Object getData() {
        return getMap();
    }

    private static void checkSelfReference(CompoundKVBS compound, KVBS kvbs) {
        if (kvbs instanceof CompoundKVBS newCompound && newCompound.equals(compound))
            throw new StackOverflowError("Self-Reference!");
    }

    public Map<String, KVBS> getMap() {
        return map;
    }

    public void putByte(String key, byte value) {
        put(key, new ByteKVBS(value));
    }
    public void putShort(String key, short value) {
        put(key, new ShortKVBS(value));
    }
    public void putInteger(String key, int value) {
        put(key, new IntegerKVBS(value));
    }
    public void putLong(String key, long value) {
        put(key, new LongKVBS(value));
    }
    public void putFloat(String key, float value) {
        put(key, new FloatKVBS(value));
    }
    public void putDouble(String key, double value) {
        put(key, new DoubleKVBS(value));
    }
    public void putBoolean(String key, boolean value) {
        put(key, new BooleanKVBS(value));
    }
    public void putString(String key, String value) {
        put(key, new StringKVBS(value));
    }
    public void putCompound(String key, CompoundKVBS compound) {
        put(key, compound);
    }
    public void putArray(String key, ArrayKVBS<?> array) {
        put(key, array);
    }

    public byte getByte(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof ByteKVBS) ? (byte) kvbs.getData() : null;
    }
    public short getShort(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof ShortKVBS) ? (short) kvbs.getData() : null;
    }
    public int getInteger(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof IntegerKVBS) ? (int) kvbs.getData() : null;
    }
    public long getLong(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof LongKVBS) ? (long) kvbs.getData() : null;
    }
    public float getFloat(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof FloatKVBS) ? (float) kvbs.getData() : null;
    }
    public double getDouble(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof DoubleKVBS) ? (double) kvbs.getData() : null;
    }
    public boolean getBoolean(String key) {
        KVBS kvbs = get(key);
        return kvbs instanceof BooleanKVBS && (boolean) kvbs.getData();
    }
    public String getString(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof StringKVBS) ? (String) kvbs.getData() : null;
    }
    public ArrayKVBS<?> getArray(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof ArrayKVBS<?>) ? (ArrayKVBS<?>) kvbs : null;
    }
    public CompoundKVBS getCompound(String key) {
        KVBS kvbs = get(key);
        return (kvbs instanceof CompoundKVBS) ? (CompoundKVBS) kvbs : null;
    }
}
