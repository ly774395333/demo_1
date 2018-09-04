package EncryptionAndDecryption;

import java.security.*;

/**
 * Created by admin on 2018/9/3.
 */
public class DSA_demo2 {
    public static void main(String[] args) throws Exception {
        //��ȡ��Կ��˽Կ
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp=kpg.generateKeyPair();
        PublicKey pk=kp.getPublic();
        PrivateKey prk=kp.getPrivate();
        //ʹ��˽Կǩ��
        String message="hello my name is jiaozi";
        //���ص�byte�����ľͿ��Խ��д���
        byte[] hash=sign(message.getBytes(),prk);
        //���ڹ�Կ���û� ���ܵ���srcByte �Ϳ�����֤�Ƿ񱻴۸�
        System.out.println(verify(message.getBytes(),hash,pk));
    }
    /**
     * ǩ������ ���ص��Ǽ��ܵ�ժҪ
     */
    public static byte[] sign(byte[] content,PrivateKey pk) throws Exception{
        Signature si=Signature.getInstance("MD5WithRSA");//����ʹ��SHA1WithRSA
        si.initSign(pk);
        si.update(content);
        return si.sign();
    }

    /**
     * ��֤ǩ������  content��ʾԭ�Ŀ��ܻ�ȡԭ�ĵ�hash  hash��ʾsign�������صļ���ժҪ
     */
    public static boolean verify(byte[] content,byte[] hash,PublicKey pk) throws Exception{
        Signature si=Signature.getInstance("MD5WithRSA");//����ʹ��SHA1WithRSA
        si.initVerify(pk);
        si.update(content);
        return si.verify(hash);
    }

}
