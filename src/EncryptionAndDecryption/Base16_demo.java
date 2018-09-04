package EncryptionAndDecryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2018/9/3.
 */
public class Base16_demo {
    public static byte[] toByte(String src){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        for(int i=0;i<src.length();i=i+2){
            char fchar=src.charAt(i);
            char nchar=src.charAt(i+1);
            byte srcb=0;
            if(fchar=='0'){
                srcb=Byte.parseByte(nchar+"", 16);
            }else{
                srcb=(byte)(Integer.parseInt(fchar+""+nchar, 16));
            }
            baos.write(srcb);
        }
        return baos.toByteArray();
    }


    public static String toHex(byte[] src){
        StringBuffer sb=new StringBuffer();
        for(byte s:src){
            //0XFF表示  8位的 11111111  和它&后 只剩下 8位 其他位都为0
            String result=Integer.toHexString(s&0xFF);
            if(result.length()==1){
                result='0'+result;
            }
            sb.append(result);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        byte[] src="hello".getBytes();
        //摘要出来的结果为字节数组  存储到数据库不方便
        MessageDigest md=MessageDigest.getInstance("SHA") ;
        byte[] bt=md.digest(src);
        //使用base64转换为字符串方便存储
        BASE64Encoder base=new BASE64Encoder();
        String str=base.encode(bt);
        System.out.println(str);
        //还原成字节数组
        BASE64Decoder de=new BASE64Decoder();
        byte[] bts=de.decodeBuffer(str);
        System.out.println(bt.length==bts.length);
    }

}
