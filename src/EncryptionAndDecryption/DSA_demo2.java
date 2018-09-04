package EncryptionAndDecryption;

import java.security.*;

/**
 * Created by admin on 2018/9/3.
 */
public class DSA_demo2 {
    public static void main(String[] args) throws Exception {
        //获取公钥和私钥
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp=kpg.generateKeyPair();
        PublicKey pk=kp.getPublic();
        PrivateKey prk=kp.getPrivate();
        //使用私钥签名
        String message="hello my name is jiaozi";
        //返回的byte和明文就可以进行传输
        byte[] hash=sign(message.getBytes(),prk);
        //存在公钥的用户 接受到该srcByte 就可以验证是否被篡改
        System.out.println(verify(message.getBytes(),hash,pk));
    }
    /**
     * 签名过程 返回的是加密的摘要
     */
    public static byte[] sign(byte[] content,PrivateKey pk) throws Exception{
        Signature si=Signature.getInstance("MD5WithRSA");//或者使用SHA1WithRSA
        si.initSign(pk);
        si.update(content);
        return si.sign();
    }

    /**
     * 验证签名过程  content表示原文可能获取原文的hash  hash表示sign函数返回的加密摘要
     */
    public static boolean verify(byte[] content,byte[] hash,PublicKey pk) throws Exception{
        Signature si=Signature.getInstance("MD5WithRSA");//或者使用SHA1WithRSA
        si.initVerify(pk);
        si.update(content);
        return si.verify(hash);
    }

}
