package com.memory.glowingmemory.util;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zc
 */
public final class HashUtils {

    private HashUtils() {
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }

        return sb.toString().toLowerCase();
    }

    public static String md5(String src) {
        return digest("MD5", src);
    }

    public static String sha256(String src) {
        return digest("SHA-256", src);
    }

    public static String digest(String algorithm, String src) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }

        final MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        md.update(src.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(md.digest());
    }
}
