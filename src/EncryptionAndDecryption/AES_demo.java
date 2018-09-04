package EncryptionAndDecryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2018/9/3.
 */
public class AES_demo {
    public static final String AL="AES";
    /**
     * 生成56字节的秘钥
     */
    public static SecretKey genKey(int len) throws NoSuchAlgorithmException{
        KeyGenerator kg=KeyGenerator.getInstance(AL);
        kg.init(len);
        return kg.generateKey();
    }

    public static void main(String[] args) throws Exception {
        //SecretKey sk=new SecretKeySpec(kl.getBytes(), "DES");
        SecretKey sk=genKey(128);
        //---------加密
        String password="tiger";
        Cipher cipher=Cipher.getInstance(AL);
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        //被加密之后获取的字节数组
        byte[] mcontent=cipher.doFinal(password.getBytes());
        //---------解密
        Cipher cipher1=Cipher.getInstance(AL);
        cipher1.init(Cipher.DECRYPT_MODE, sk);
        System.out.println(new String(cipher1.doFinal(mcontent)));
    }

}
