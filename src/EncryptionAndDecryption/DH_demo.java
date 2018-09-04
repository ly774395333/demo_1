package EncryptionAndDecryption;

/**
 * Created by admin on 2018/9/3.
 */
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class DH_demo {
    public static KeyPair genernateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("DH");
        kpg.initialize(512);
        KeyPair kp=kpg.generateKeyPair();
        return kp;
    }
    public static byte[] encrypt(SecretKey secretKey,byte[] data) throws Exception{
        // 数据加密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }
    public static byte[] decrypt(SecretKey secretKey,byte[] data) throws Exception{
        // 数据加密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
    public static void main(String[] args) throws Exception {
        //生成A用户的公私钥对
        KeyPair apair=genernateKeyPair();
        PublicKey apubKey=apair.getPublic();
        System.out.println("B接受到A的公钥："+Base64.getEncoder().encodeToString(apubKey.getEncoded()));

        //生成B用户的公私钥对
        //A和B协商生成对象加密的秘钥过程
        //1 A发送公钥给B 模拟获取变量  传送b过程 getEncode获取byte数组后 转换base64 传送
        DHParameterSpec dhParameterSpec = ((DHPublicKey)apair.getPublic()).getParams();
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("DH");
        kpg.initialize(dhParameterSpec);//传入A的公钥
        KeyPair bpair=kpg.generateKeyPair();


        //2 B通过A的公钥和自己的公私钥 生成秘钥
        //实例化
        KeyAgreement keyAgree = KeyAgreement.getInstance("DH");

        //传入B的私钥和A的公钥
        keyAgree.init(bpair.getPrivate());
        keyAgree.doPhase(apair.getPublic(), true);
        //生成本地密钥
        SecretKey secretKey = keyAgree.generateSecret("DES");
        System.out.println("此时A生成了秘钥："+Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        //4 A通过B的公钥和自己的公私钥 生成秘钥
        //实例化
        keyAgree = KeyAgreement.getInstance("DH");
        //传入A的私钥和B的公钥
        keyAgree.init(apair.getPrivate());
        keyAgree.doPhase(bpair.getPublic(), true);
        //生成本地密钥
        SecretKey secretKey1 = keyAgree.generateSecret("DES");
        System.out.println("此时B生成了秘钥："+Base64.getEncoder().encodeToString(secretKey1.getEncoded()));


        byte[] bbb=encrypt(secretKey, "hello".getBytes());
        System.out.println(new String(decrypt(secretKey1, bbb)));

    }
}
