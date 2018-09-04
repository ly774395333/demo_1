package EncryptionAndDecryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2018/9/3.
 */
public class SHA_demo {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest md=MessageDigest.getInstance("SHA") ;//ªÚ’ﬂSHA-1 SHA1
        String code="hello";
        byte[] bt=md.digest(code.getBytes());
        System.out.println(bt.length);
    }
}
