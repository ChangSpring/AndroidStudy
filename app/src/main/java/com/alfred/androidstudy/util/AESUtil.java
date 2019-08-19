package com.alfred.androidstudy.util;

import android.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author :  Alfred
 * @date : 2019-08-14 17:27
 */
public class AESUtil {

    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 生成秘钥
     *
     * @return Base64编码的秘钥
     */
    public static String generateSecretAESKey() {
        try {
            // 获取Key生成器实例,一般一个实例可以多次用来生成秘钥
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            // 利用用户密码作为随机数初始化出128位的key生产者。SecureRandom 是生产安全随机数序列，password.getBytes()是种子
            // 只要种子相同，序列就一样，所以解密只要有password就行
            keyGenerator.init(256);
            // 生成密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 返回基本编码格式的密钥。如果此密钥不支持编码，则返回null
            byte[] keyBytes = secretKey.getEncoded();
            // 生成的秘钥转换成Base64编码,加、解密时需要用Base64还原秘钥
            return Base64.encodeToString(keyBytes, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param plaintext 明文
     * @param key 秘钥
     * @return Base64编码的密文
     */
    public static String encryptByAES(String plaintext, String key) {
        try {
            // Base64还原秘钥
            byte[] keyBytes = Base64.decode(key.getBytes(), Base64.DEFAULT);
            // 还原密钥对象
            // 转换为 AES 专用密钥
            SecretKey secretKey = new SecretKeySpec(keyBytes, AES);
            // 加密初始化实例
            //创建密码器
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            // CBC模式需要添加一个参数IvParameterSpec，ECB模式则不需要
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] result = cipher.doFinal(plaintext.getBytes("UTF-8"));
            // 生成的密文转换成Base64编码出文本,解密时需要用Base64还原出密文
            return Base64.encodeToString(result, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param ciphertext 密文
     * @param key 秘钥
     * @return 明文
     */
    public static String decryptByAES(String ciphertext, String key) {

        try {
            // Base64还原秘钥
            byte[] keyBytes = Base64.decode(key.getBytes(), Base64.DEFAULT);
            // 还原密钥对象
            SecretKey secretKey = new SecretKeySpec(keyBytes, AES);
            // 加密初始化实例
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            // Base64还原密文
            byte[] cipherBytes = Base64.decode(ciphertext, Base64.DEFAULT);
            byte[] result = cipher.doFinal(cipherBytes);
            return new String(result, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
