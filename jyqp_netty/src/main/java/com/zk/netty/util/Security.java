package com.zk.netty.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
/**
 * BASE64 ， URLEncoder
 * @author syf
 *
 */
public class Security {
	
	private static Logger logger = LoggerFactory.getLogger(Security.class);
	// 加密解密 KEY
	private static final String KEY ="wfchjdytgpynwww.sibu.cn";
	
	/**
	 * 加密
	 * @param input
	 * @param key
	 * @return
	 */
	
	public static String encrypt(String encode) {
		//密匙
		MD5 md5=new MD5();
		String key=md5.getMD5(KEY).toLowerCase().substring(6,22);
		byte[] crypted = null;

		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(),
					"AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, skey);

			crypted = cipher.doFinal(encode.getBytes());

		} catch (Exception e) {
			logger.error("decrypt",e);
		}
		//base64 编码
		crypted=BASE64EncoderStream.encode(crypted);
		String returnStr=null;
		try {
			//URLEncoder 编码 |   crypted  new String
			returnStr=URLEncoder.encode(new String(crypted), "utf-8").toString();
		} catch (UnsupportedEncodingException e) {
			logger.error("decrypt",e);
		}
		return returnStr;
	}
	/**
	 * 解密
	 * @param input
	 * @param key
	 * @return
	 */
	public static String decrypt(String decode) {
		//密匙
		MD5 md5=new MD5();
		String key=md5.getMD5(KEY).toLowerCase().substring(6,22);
		
		try {
			//URLDecoder 解码
			decode=new String(URLDecoder.decode(decode, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("decrypt",e1);
		}
		byte[] output = null;

		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(),"AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			
			output = cipher.doFinal(BASE64DecoderStream.decode(decode.getBytes()));
			
		} catch (Exception e) {
			logger.error("decrypt",e);
		}
		//new String
		return new String(output);
	}
}
