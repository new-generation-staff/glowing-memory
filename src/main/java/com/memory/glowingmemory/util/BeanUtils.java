package com.memory.glowingmemory.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author zc
 */
public abstract class BeanUtils {

    //使用 try-with-resources 代替try-catch-finally
    @SuppressWarnings("unchecked")
    public static <T> T cloneTo(T src) throws RuntimeException {
        T dist;
        try (ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(memoryBuffer)) {
            out.writeObject(src);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));
            dist = (T) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dist;
    }
}
