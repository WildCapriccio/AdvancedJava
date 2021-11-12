package com.jike;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> myClass = new MyClassLoader().findClass("Hello");
            Method helloMethod = myClass.getMethod("hello");
            helloMethod.invoke(myClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] sourceBytes = readSource("Hello.xlass");
        decode(sourceBytes);
        return defineClass(name, sourceBytes, 0, sourceBytes.length);
    }

    @SneakyThrows
    private static byte[] readSource(String fileName) {
        InputStream resourceAsStream = new ClassPathResource(fileName).getInputStream();
        int resourceLength = resourceAsStream.available();
        byte[] resultBytes = new byte[resourceLength];
        resourceAsStream.read(resultBytes); // store resource into resultBytes

        return resultBytes;
    }

    private static void decode(byte[] sourceBytes) {
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) (255 - sourceBytes[i]);
        }
    }
}
