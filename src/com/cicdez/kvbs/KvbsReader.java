package com.cicdez.kvbs;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

final class KvbsReader {
    private KvbsReader() {}

    public static CompoundKVBS readCompound(ByteArrayInputStream stream) {
        CompoundKVBS compound = new CompoundKVBS();
        byte[] allBytes = stream.readAllBytes();
        stream.reset();

        for (int i = 0; i < allBytes.length;) {
            //Read stack-length from LengthParameter
            int lengthParameterLength = stream.read();
            i++;
            byte[] lengthParameter = Bytes.part(allBytes, i, lengthParameterLength);
            stream.skip(lengthParameterLength);
            i += lengthParameterLength;
            long length = Bytes.readLengthParameter(lengthParameter);

            readStack(Bytes.part(allBytes, i, (int) length), compound);

            stream.skip(length);
            i += length;
        }

        return compound;
    }

    private static void readStack(byte[] bytes, CompoundKVBS compound) {
        int i = 0;
        while (bytes[i] != Bytes.EQUALS) {
            i++;
        }
        compound.put(new String(Bytes.part(bytes, 0, i)),
                readValue(new ByteArrayInputStream(Bytes.part(bytes, i + 1,
                        bytes.length - i - 1))));
    }

    private static KVBS readValue(ByteArrayInputStream stream) {
        byte[] allBytes = stream.readAllBytes();
        stream.reset();
        int type = stream.read();
        byte[] valueBytes = Bytes.part(allBytes, 1, allBytes.length - 1);
        if (type == Bytes.BYTE) {
            return new KVBS.ByteKVBS(valueBytes[0]);
        } else if (type == Bytes.SHORT) {
            return new KVBS.ShortKVBS(Bytes.toShort(valueBytes));
        } else if (type == Bytes.INTEGER) {
            return new KVBS.IntegerKVBS(Bytes.toInteger(valueBytes));
        } else if (type == Bytes.LONG) {
            return new KVBS.LongKVBS(Bytes.toLong(valueBytes));
        } else if (type == Bytes.FLOAT) {
            return new KVBS.FloatKVBS(Bytes.toFloat(valueBytes));
        } else if (type == Bytes.DOUBLE) {
            return new KVBS.DoubleKVBS(Bytes.toDouble(valueBytes));
        } else if (type == Bytes.STRING) {
            return new KVBS.StringKVBS(Bytes.toString(valueBytes));
        } else if (type == Bytes.BOOLEAN) {
            return new KVBS.BooleanKVBS(valueBytes[0] == 1);
        } else if (type == Bytes.COMPOUND) {
            return readCompound(new ByteArrayInputStream(valueBytes));
        } else if (type == Bytes.ARRAY) {
            return readArray(new ByteArrayInputStream(valueBytes));
        }
        else {
            throw new IllegalStateException("Unknown type: '" + type + "'");
        }
    }

    public static ArrayKVBS<? extends KVBS> readArray(ByteArrayInputStream stream) {
        ArrayKVBS<KVBS> array = new ArrayKVBS<>();
        byte[] allBytes = stream.readAllBytes();
        stream.reset();

        int arrayHolderType = stream.read();
        if (arrayHolderType == 0) return new ArrayKVBS<>(new KVBS[0]);

        array.setType((byte) arrayHolderType);

        for (int i = 1; i < allBytes.length;) {
            //Read stack-length
            int lengthParameterLength = stream.read();
            i++;
            byte[] lengthParameter = Bytes.part(allBytes, i, lengthParameterLength);
            stream.skip(lengthParameterLength);
            i += lengthParameterLength;
            long length = Bytes.readLengthParameter(lengthParameter);

            readArrayStack(Bytes.part(allBytes, i, (int) length), array);

            stream.skip(length);
            i += length;
        }

        return array;
    }

    private static void readArrayStack(byte[] bytes, ArrayKVBS<KVBS> array) {
        array.add(readArrayValue(bytes, array.getInnerType()));
    }

    private static KVBS readArrayValue(byte[] bytes, byte type) {
        if (type == Bytes.BYTE) {
            return new KVBS.ByteKVBS(bytes[0]);
        } else if (type == Bytes.SHORT) {
            return new KVBS.ShortKVBS(Bytes.toShort(bytes));
        } else if (type == Bytes.INTEGER) {
            return new KVBS.IntegerKVBS(Bytes.toInteger(bytes));
        } else if (type == Bytes.LONG) {
            return new KVBS.LongKVBS(Bytes.toLong(bytes));
        } else if (type == Bytes.FLOAT) {
            return new KVBS.FloatKVBS(Bytes.toFloat(bytes));
        } else if (type == Bytes.DOUBLE) {
            return new KVBS.DoubleKVBS(Bytes.toDouble(bytes));
        } else if (type == Bytes.STRING) {
            return new KVBS.StringKVBS(Bytes.toString(bytes));
        } else if (type == Bytes.BOOLEAN) {
            return new KVBS.BooleanKVBS(bytes[0] == 1);
        } else if (type == Bytes.COMPOUND) {
            return readCompound(new ByteArrayInputStream(bytes));
        } else if (type == Bytes.ARRAY) {
            return readArray(new ByteArrayInputStream(bytes));
        }
        else {
            throw new IllegalStateException("Unknown type: '" + type + "'");
        }
    }
}
