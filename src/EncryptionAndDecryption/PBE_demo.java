package EncryptionAndDecryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.util.Random;

/**
 * Created by admin on 2018/9/3.
 */
public class PBE_demo {
    //盐 用于将明文进行多次混淆
    static byte[] salt = new byte[8];
    static Random r = new Random();
    static int saltCount=100;
    static{
        r.nextBytes(salt);
    }
    public static final String AL="PBEWithMD5AndDES";
    /**
     * 生成自定义口令的秘钥
     */
    public static SecretKey genKey(String kl) throws Exception{
        char[] klChar=kl.toCharArray();
        PBEKeySpec pbe=new PBEKeySpec(klChar);
        SecretKeyFactory skf=SecretKeyFactory.getInstance(AL);
        return skf.generateSecret(pbe);
    }
    /**
     * 使用口令和盐进行加密
     */
    public static  byte[] encrypt(SecretKey key,byte[] src) throws Exception{
        Cipher cipher=Cipher.getInstance(AL);
        //使用口令  盐（100次混淆）
        PBEParameterSpec parameter=new PBEParameterSpec(salt, saltCount);
        cipher.init(Cipher.ENCRYPT_MODE, key,parameter);
        //被加密之后获取的字节数组
        byte[] mcontent=cipher.doFinal(src);
        return mcontent;
    }
    /**
     * 使用口令和盐进行解密  盐和口令和混淆的次数都必须和加密之前一致
     */
    public static byte[] decrypt(SecretKey key,byte[] src) throws Exception{
        Cipher cipher=Cipher.getInstance(AL);
        //使用口令  盐（100次混淆）
        PBEParameterSpec parameter=new PBEParameterSpec(salt, saltCount);
        cipher.init(Cipher.DECRYPT_MODE, key,parameter);
        //被加密之后获取的字节数组
        byte[] mcontent=cipher.doFinal(src);
        return mcontent;
    }


    public static void main(String[] args) throws Exception {
        //SecretKey sk=new SecretKeySpec(kl.getBytes(), "DES");
        SecretKey sk=genKey("123456");
        //---------加密
        String password="tiger";
        byte[] mw=encrypt(sk, password.getBytes());
        //---------解密
        System.out.println(new String(decrypt(sk, mw)));
    }

}
