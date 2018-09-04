package EncryptionAndDecryption;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/9/3.
 * ����ǩ��
 */
public class DSA_demo {
    public static void main(String[] args) throws Exception {
        //��ȡ��Կ��˽Կ
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp=kpg.generateKeyPair();
        PublicKey pk=kp.getPublic();
        PrivateKey prk=kp.getPrivate();
        //ʹ��˽Կǩ��
        String message="hello my name is jiaozi";
        //���ص�byte�Ϳ��Խ��д���
        byte[] srcByte=sign(message.getBytes(),prk);
        //��������ģ��۸����� �϶�������쳣 ���߼��鲻ͨ��
        srcByte[9]=10;
        //���ڹ�Կ���û� ���ܵ���srcByte �Ϳ�����֤�Ƿ񱻴۸�
        System.out.println(verify(srcByte,pk));
    }
    /**
     * ǩ������
     */
    public static byte[] sign(byte[] content,PrivateKey pk) throws Exception{
        //�����Ľ���ժҪ
        MessageDigest md=MessageDigest.getInstance("MD5");
        byte[] zy=md.digest(content);

        //��ժҪ���м���
        Cipher cp=Cipher.getInstance("RSA");
        cp.init(Cipher.ENCRYPT_MODE, pk);
        byte[] enZy=cp.doFinal(zy);
        //Ҫһ���͵����� ˫��Լ����ʹ��Map
        Map map=new HashMap();
        map.put("content", content);
        map.put("enZy", enZy);
        //�������ʹ��byte���� ����ʹ�����л���������ת��Ϊ�ֽ�����
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(baos);
        oos.writeObject(map);
        oos.close();
        return baos.toByteArray();
    }

    /**
     * ��֤ǩ������
     */
    public static boolean verify(byte[] content,PublicKey pk) throws Exception{
        //����ȡ������ת��ΪMap
        ByteArrayInputStream baos=new ByteArrayInputStream(content);
        ObjectInputStream oos=new ObjectInputStream(baos);
        Map map=(Map)oos.readObject();
        oos.close();
        //��ȡ�����ĺͼ��ܵ�ժҪ��Ϣ
        byte[] srcContent=(byte[])map.get("content");
        byte[] enZy=(byte[])map.get("enZy");

        //ʹ����ͬ��ժҪ�㷨 ������ժҪ
        MessageDigest md=MessageDigest.getInstance("MD5");
        byte[] contentZy=md.digest(srcContent);
        //�����ܵ�ժҪ����
        Cipher cp=Cipher.getInstance("RSA");
        cp.init(Cipher.DECRYPT_MODE, pk);
        byte[] zy=cp.doFinal(enZy);
        BASE64Encoder bas=new BASE64Encoder();
        if(bas.encode(contentZy).equals(bas.encode(zy))){
            return true;
        }
        return false;
    }

}
