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
    //�� ���ڽ����Ľ��ж�λ���
    static byte[] salt = new byte[8];
    static Random r = new Random();
    static int saltCount=100;
    static{
        r.nextBytes(salt);
    }
    public static final String AL="PBEWithMD5AndDES";
    /**
     * �����Զ���������Կ
     */
    public static SecretKey genKey(String kl) throws Exception{
        char[] klChar=kl.toCharArray();
        PBEKeySpec pbe=new PBEKeySpec(klChar);
        SecretKeyFactory skf=SecretKeyFactory.getInstance(AL);
        return skf.generateSecret(pbe);
    }
    /**
     * ʹ�ÿ�����ν��м���
     */
    public static  byte[] encrypt(SecretKey key,byte[] src) throws Exception{
        Cipher cipher=Cipher.getInstance(AL);
        //ʹ�ÿ���  �Σ�100�λ�����
        PBEParameterSpec parameter=new PBEParameterSpec(salt, saltCount);
        cipher.init(Cipher.ENCRYPT_MODE, key,parameter);
        //������֮���ȡ���ֽ�����
        byte[] mcontent=cipher.doFinal(src);
        return mcontent;
    }
    /**
     * ʹ�ÿ�����ν��н���  �κͿ���ͻ����Ĵ���������ͼ���֮ǰһ��
     */
    public static byte[] decrypt(SecretKey key,byte[] src) throws Exception{
        Cipher cipher=Cipher.getInstance(AL);
        //ʹ�ÿ���  �Σ�100�λ�����
        PBEParameterSpec parameter=new PBEParameterSpec(salt, saltCount);
        cipher.init(Cipher.DECRYPT_MODE, key,parameter);
        //������֮���ȡ���ֽ�����
        byte[] mcontent=cipher.doFinal(src);
        return mcontent;
    }


    public static void main(String[] args) throws Exception {
        //SecretKey sk=new SecretKeySpec(kl.getBytes(), "DES");
        SecretKey sk=genKey("123456");
        //---------����
        String password="tiger";
        byte[] mw=encrypt(sk, password.getBytes());
        //---------����
        System.out.println(new String(decrypt(sk, mw)));
    }

}
