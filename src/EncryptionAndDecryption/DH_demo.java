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
        // ���ݼ���
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }
    public static byte[] decrypt(SecretKey secretKey,byte[] data) throws Exception{
        // ���ݼ���
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
    public static void main(String[] args) throws Exception {
        //����A�û��Ĺ�˽Կ��
        KeyPair apair=genernateKeyPair();
        PublicKey apubKey=apair.getPublic();
        System.out.println("B���ܵ�A�Ĺ�Կ��"+Base64.getEncoder().encodeToString(apubKey.getEncoded()));

        //����B�û��Ĺ�˽Կ��
        //A��BЭ�����ɶ�����ܵ���Կ����
        //1 A���͹�Կ��B ģ���ȡ����  ����b���� getEncode��ȡbyte����� ת��base64 ����
        DHParameterSpec dhParameterSpec = ((DHPublicKey)apair.getPublic()).getParams();
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("DH");
        kpg.initialize(dhParameterSpec);//����A�Ĺ�Կ
        KeyPair bpair=kpg.generateKeyPair();


        //2 Bͨ��A�Ĺ�Կ���Լ��Ĺ�˽Կ ������Կ
        //ʵ����
        KeyAgreement keyAgree = KeyAgreement.getInstance("DH");

        //����B��˽Կ��A�Ĺ�Կ
        keyAgree.init(bpair.getPrivate());
        keyAgree.doPhase(apair.getPublic(), true);
        //���ɱ�����Կ
        SecretKey secretKey = keyAgree.generateSecret("DES");
        System.out.println("��ʱA��������Կ��"+Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        //4 Aͨ��B�Ĺ�Կ���Լ��Ĺ�˽Կ ������Կ
        //ʵ����
        keyAgree = KeyAgreement.getInstance("DH");
        //����A��˽Կ��B�Ĺ�Կ
        keyAgree.init(apair.getPrivate());
        keyAgree.doPhase(bpair.getPublic(), true);
        //���ɱ�����Կ
        SecretKey secretKey1 = keyAgree.generateSecret("DES");
        System.out.println("��ʱB��������Կ��"+Base64.getEncoder().encodeToString(secretKey1.getEncoded()));


        byte[] bbb=encrypt(secretKey, "hello".getBytes());
        System.out.println(new String(decrypt(secretKey1, bbb)));

    }
}
