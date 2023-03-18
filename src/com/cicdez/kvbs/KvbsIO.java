package com.cicdez.kvbs;

import java.io.*;

public final class KvbsIO {
    private KvbsIO() {}

    public static void write(CompoundKVBS compound, File file) throws IOException {
        write(compound, new FileOutputStream(file));
    }
    public static void write(CompoundKVBS compound, OutputStream stream) throws IOException {
        stream.write(compound.getBytes());
    }

    public static CompoundKVBS read(File file) throws IOException {
        return read(new FileInputStream(file));
    }
    public static CompoundKVBS read(InputStream stream) throws IOException {
        byte[] bytes = new byte[stream.available()];
        stream.read(bytes);
        return KvbsReader.readCompound(new ByteArrayInputStream(bytes));
    }
}
