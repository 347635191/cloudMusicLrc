package com.music.cloud.lrc.util;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCBCUtil {
    public static String encrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = sKey.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception ignore) {
        }
        return "";
    }
}
