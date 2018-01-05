package com.zk.netty.util;

/**
 * Base64加密
 * @author liyafeng 2015年4月9日
 *
 */
public class Base64 {

	/**
	 * BASE64加密(string)
	 * 
	 * @param value
	 * @return String
	 */
	public static String encode(String value) {
		byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(value.getBytes());
		return new String(bytes);
	}

	/**
	 * BASE64解密(string)
	 * 
	 * @param value
	 * @return String
	 */
	public static String decode(String value) {
		byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(value.getBytes());
		return new String(bytes);
	}

	/**
	 * BASE64加密(bytes)
	 * 
	 * @param value
	 * @return String
	 */
	public static String encodeByBytes(byte[] value) {
		byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(value);
		return new String(bytes);
	}

	/**
	 * BASE64解密(bytes)
	 * 
	 * @param value
	 * @return byte[]
	 */
	public static byte[] decodeToBytes(String value) {
		byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(value.getBytes());
		return bytes;
	}
}
