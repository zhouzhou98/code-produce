package com.knowledge.server.common;

import cn.hutool.crypto.symmetric.AES;


public class AES128Utils {
    private static String aesType = "CBC";
    private static String secretPadding = "PKCS7Padding";
    private static String secret = "GzcccPCall012345";
    private static String iv = "GzcccCallDYgjCE0";

    public static String encryption(String data){
        AES aes = new AES(aesType, secretPadding,
                // 密钥，不可更改 (16位)
                secret.getBytes(),
                // iv加盐
                iv.getBytes());
        // 加密为16进制表示
        String encryptHex = aes.encryptHex(data);
        return encryptHex;
    }

    /**
     * 解密
     * @param data
     * @return
     */
    public static String decryption(String data){
        AES aes = new AES(aesType, secretPadding,
                // 密钥，不可更改 (16位)
                secret.getBytes(),
                // iv加盐
                iv.getBytes());
        // 加密为16进制表示
        String decryptStr = aes.decryptStr(data);
        return decryptStr;
    }

    public static void main(String[] args) {
        String encryption = encryption("oSE7x4q0Q-TXfGFESvdSoT9wqhq0");
        // 加密: 7e6ac9def677931c7b6e16b0fa015ae0111d81ac915a4bc08ed333e7011a75c0
        System.out.println("加密: "+encryption);

        String decryption = decryption(encryption);
        // 解密: oSE7x4q0Q-TXfGFESvdSoT9wqhq0
        System.out.println("解密: "+decryption);
    }
}
