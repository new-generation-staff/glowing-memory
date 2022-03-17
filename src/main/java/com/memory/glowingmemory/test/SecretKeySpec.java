package com.memory.glowingmemory.test;

/**
 * @author zc
 */
public class SecretKeySpec {


    /**
     * The secret key.
     *
     * @serial
     */
    private byte[] key;

    /**
     * The name of the algorithm associated with this key.
     *
     * @serial
     */
    private String algorithm;

    public SecretKeySpec(byte[] key, String algorithm) {
        if (key == null || algorithm == null) {
            throw new IllegalArgumentException("Missing argument");
        }
        if (key.length == 0) {
            throw new IllegalArgumentException("Empty key");
        }
        this.key = key.clone();
        this.algorithm = algorithm;
    }
}
