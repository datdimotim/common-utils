package com.dimotim.utils;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public class FileUtils {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    public static boolean write(File file, byte[] bytes) {
        try(FileOutputStream fos=new FileOutputStream(file)) {
            fos.write(bytes);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void copyFiles(File from, File to) throws IOException {
        Files.copy(from.toPath(),to.toPath());
    }

    public static byte[] readAllBytes(File file)throws IOException{
        try(FileInputStream fis=new FileInputStream(file)) {
            return readAllBytes(fis);
        }
    }

    // java 9 source
    public static byte[] readAllBytes(InputStream is) throws IOException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        int capacity = buf.length;
        int nread = 0;
        int n;
        for (;;) {
            // read to EOF which may read more or less than initial buffer size
            while ((n = is.read(buf, nread, capacity - nread)) > 0)
                nread += n;

            // if the last call to read returned -1, then we're done
            if (n < 0)
                break;

            // need to allocate a larger buffer
            if (capacity <= MAX_BUFFER_SIZE - capacity) {
                capacity = capacity << 1;
            } else {
                if (capacity == MAX_BUFFER_SIZE)
                    throw new OutOfMemoryError("Required array size too large");
                capacity = MAX_BUFFER_SIZE;
            }
            buf = Arrays.copyOf(buf, capacity);
        }
        return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
    }
}