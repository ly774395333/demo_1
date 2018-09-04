package EncryptionAndDecryption;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * Created by admin on 2018/9/3.
 */
public class RAS_demo {
    public static void main(String[] args) throws Exception {
        // ����RSA��Կ��
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp=kpg.generateKeyPair();
        PublicKey pk=kp.getPublic();
        PrivateKey prk=kp.getPrivate();

        //��Կ����
        Cipher cip=Cipher.getInstance("RSA");
        cip.init(Cipher.ENCRYPT_MODE, pk);
        byte[] mw=cip.doFinal("test".getBytes());
        //˽Կ����
        Cipher cip1=Cipher.getInstance("RSA");
        cip1.init(Cipher.DECRYPT_MODE, prk);
        System.out.println(new String(cip1.doFinal(mw)));


        //��ǰ�˽�������
        Map<String, Key> keyMap = RSACoder.initKey();
        String publicKey = RSACoder.getPublicKey(keyMap);
        String privateKey = RSACoder.getPrivateKey(keyMap);
        System.out.println(publicKey);
        System.out.println(privateKey);
        byte[] decodedData = RSACoder.decryptByPrivateKey("���׼��ܺ������",
                String.valueOf(privateKey));
        String password = new String(decodedData,"utf-8");//��ע�������ַ�������
        /*          <script type="text/javascript" src="${ctx}/js/encrypt/jsencrypt.min.js"></script>
                    var encrypt = new JSEncrypt();//js����
                    encrypt.setPublicKey('${sessionScope.publicKey}');
                    password = encrypt.encrypt(password.trim());
        *
        * */
    }

    /**
     * ��ȡ��Կ�ֽ�����ת��Ϊ����
     * @throws Exception
     */
    public PublicKey getPub(byte[] bt) throws Exception{
        X509EncodedKeySpec x=new X509EncodedKeySpec(bt);
        KeyFactory fac=KeyFactory.getInstance("RSA");
        return fac.generatePublic(x);
    }
    /**
     * ��ȡ˽Կ�ֽ�����ת��Ϊ����
     * @throws Exception
     */
    public PrivateKey getPri(byte[] bt) throws Exception{
        PKCS8EncodedKeySpec x=new PKCS8EncodedKeySpec(bt);
        KeyFactory fac=KeyFactory.getInstance("RSA");
        return fac.generatePrivate(x);
    }

    /*

    *
    * */

}
