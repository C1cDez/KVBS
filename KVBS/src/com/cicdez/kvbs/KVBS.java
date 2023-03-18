package com.cicdez.kvbs;

public interface KVBS {
    byte getType();
    byte[] getBytes();

    byte[] generateLengthParameter();

    Object getData();

    public static class ByteKVBS implements KVBS {
        private final byte data;
        public ByteKVBS(byte data) {
            this.data = data;
        }

        @Override
        public byte getType() {
            return Bytes.BYTE;
        }

        @Override
        public byte[] getBytes() {
            return new byte[] {data};
        }

        @Override
        public byte[] generateLengthParameter() {
            return new byte[] {1, 1};
        }

        @Override
        public Object getData() {
            return data;
        }
    }

    public static class ShortKVBS implements KVBS {
        private final short data;
        public ShortKVBS(short data) {
            this.data = data;
        }

        @Override
        public byte getType() {
            return Bytes.SHORT;
        }

        @Override
        public byte[] getBytes() {
            return Bytes.fromShort(data);
        }

        @Override
        public byte[] generateLengthParameter() {
            return new byte[] {1, 2};
        }

        @Override
        public Object getData() {
            return data;
        }
    }

    public static class IntegerKVBS implements KVBS {
        private final int data;
        public IntegerKVBS(int data) {
            this.data = data;
        }

        @Override
        public byte getType() {
            return Bytes.INTEGER;
        }

        @Override
        public byte[] getBytes() {
            return Bytes.fromInteger(data);
        }

        @Override
        public byte[] generateLengthParameter() {
            return new byte[] {1, 4};
        }

        @Override
        public Object getData() {
            return data;
        }
    }

    public static class LongKVBS implements KVBS {
        private final long data;
        public LongKVBS(long data) {
            this.data = data;
        }

        @Override
        public byte getType() {
            return Bytes.LONG;
        }

        @Override
        public byte[] getBytes() {
            return Bytes.fromLong(data);
        }

        @Override
        public byte[] generateLengthParameter() {
            return new byte[] {1, 8};
        }

        @Override
        public Object getData() {
            return data;
        }
    }

    public static class FloatKVBS implements KVBS {
        private final float data;
        public FloatKVBS(float data) {
            this.data = data;
        }

        @Override
        public byte getType() {
            return Bytes.FLOAT;
        }

        @Override
        public byte[] getBytes() {
            return Bytes.fromFloat(data);
        }

        @Override
        public byte[] generateLengthParameter() {
            return new byte[] {1, 4};
        }

        @Override
        public Object getData() {
            return data;
        }
    }

    public static class DoubleKVBS implements KVBS {
        private final double data;
        public DoubleKVBS(double data) {
            this.data = data;
        }

        @Override
        public byte getType() {
            return Bytes.DOUBLE;
        }

        @Override
        public byte[] getBytes() {
            return Bytes.fromDouble(data);
        }

        @Override
        public byte[] generateLengthParameter() {
                return new byte[] {1, 8};
        }

        @Override
        public Object getData() {
            return data;
        }
    }

    public static class BooleanKVBS implements KVBS {
        private final boolean data;
        public BooleanKVBS(boolean data) {
            this.data = data;
        }

        @Override
        public byte getType() {
            return Bytes.BOOLEAN;
        }

        @Override
        public byte[] getBytes() {
            return new byte[] {(byte) (data ? 1 : 0)};
        }

        @Override
        public byte[] generateLengthParameter() {
            return new byte[] {1, 1};
        }

        @Override
        public Object getData() {
            return data;
        }
    }

    public static class StringKVBS implements KVBS {
        private final String data;
        public StringKVBS(String data) {
            this.data = data;
        }

        @Override
        public byte getType() {
            return Bytes.STRING;
        }

        @Override
        public byte[] getBytes() {
            return Bytes.fromString(data);
        }

        @Override
        public byte[] generateLengthParameter() {
            return Bytes.generateLengthParameter(data.length());
        }

        @Override
        public Object getData() {
            return data;
        }
    }
}
