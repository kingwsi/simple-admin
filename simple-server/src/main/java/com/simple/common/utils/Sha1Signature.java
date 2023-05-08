package com.simple.common.utils;

import java.security.MessageDigest;
import java.util.Arrays;

public class Sha1Signature {

    /**
     * 对参数进行字典序排序并进行SHA1加密
     *
     * @param token     令牌
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return 加密后的字符串
     */
    public static String sign(String token, String timestamp, String nonce) throws Exception {
        // 将三个参数按字典序排序
        String[] arr = {token, timestamp, nonce};
        Arrays.sort(arr);

        // 将排序后的三个参数拼接成一个字符串
        String str = arr[0] + arr[1] + arr[2];

        // 使用SHA1算法对拼接后的字符串进行加密
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes("UTF-8"));
        byte[] digest = md.digest();

        // 将加密后的字节数组转换为字符串并返回
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
