package com.dongxiang.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件md5加密类
 * 
 * @author Anthony
 * @version 1.0
 * @createTime 2016-4-22
 */
public class MD5Utils {



    public static String md5(byte[] data) throws NoSuchAlgorithmException, IOException {
        return getMd5InputStream(new ByteArrayInputStream(data));
    }
    public static String md5(File file) throws NoSuchAlgorithmException, IOException {
        return getMd5InputStream(new FileInputStream(file));
    }
    public static String md5(InputStream fis) throws NoSuchAlgorithmException, IOException  {
        return getMd5InputStream(fis);
    }

    public static String getMd5(String filename) throws NoSuchAlgorithmException, IOException {
        return getMd5InputStream(new FileInputStream(filename));
    }

    private static String getMd5InputStream(InputStream fis) throws NoSuchAlgorithmException, IOException {

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        byte[] b = complete.digest();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result .append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }


    /**
     * MD5加密
     * @param source 加密内容
     * @return 加密后的内容（String类型）
     * @throws UnsupportedEncodingException
     */
    public static final String md5(String source)  {
        String dest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray;
            try {
                byteArray = source.getBytes("UTF-8");
                byte[] md5Bytes = md5.digest(byteArray);
                StringBuffer hexValue = new StringBuffer();
                for (int i = 0; i < md5Bytes.length; i++) {
                    int val = (md5Bytes[i]) & 0xff;
                    if (val < 16)
                        hexValue.append("0");
                    hexValue.append(Integer.toHexString(val));
                }
                dest = hexValue.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return dest;
    }
}
