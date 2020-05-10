package com.dimotim.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonSerializer {
    public static <T> T readJson(String file, Class<T> type) throws IOException {
        return readJson(new File(file).toURI().toURL(), TypeToken.get(type));
    }

    public static <T> T readJson(URL url, Class<T> type) throws IOException {
        return readJson(url,TypeToken.get(type));
    }

    public static <T> T readJson(URL url, TypeToken<T> type) throws IOException {
        try(InputStream is=url.openStream(); InputStreamReader isr=new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return new Gson().fromJson(isr,type.getType());
        }
    }

    public static void writeJson(File file, Object obj) throws IOException {
        byte[] b=writeJson(obj);
        try(FileOutputStream fos=new FileOutputStream(file)) {
            fos.write(b);
        }
    }

    public static byte[] writeJson(Object obj) throws IOException {
        try(ByteArrayOutputStream baos=new ByteArrayOutputStream(); OutputStreamWriter osw=new OutputStreamWriter(baos,StandardCharsets.UTF_8)) {
            new Gson().toJson(obj,osw);
            osw.flush();
            return baos.toByteArray();
        }
    }
}
