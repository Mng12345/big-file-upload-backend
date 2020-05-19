package com.zm.fileupload.utils;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.UnsupportedEncodingException;

/**
 * @Author zhangming
 * @Date 2020/5/18 15:16
 */
public class CharsetUtil {

    public static String guessEncoding(byte[] bytes) throws UnsupportedEncodingException {
        String[] encodings = new String[] {"GBK", "UTF-8", "GB2312"};
        for (String encoding: encodings) {
            if (bytesEqual(bytes, new String(bytes, encoding).getBytes())) {
                return encoding;
            }
        }
        return null;
    }

    private static boolean bytesEqual(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length != bytes2.length)
            return false;
        for (int i=0; i<bytes1.length; i++) {
            if (bytes1[i] != bytes2[i])
                return false;
        }
        return true;
    }

}
