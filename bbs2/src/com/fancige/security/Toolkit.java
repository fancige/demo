package com.fancige.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Toolkit {
	
	private static String toHexString(byte[] bs) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bs) {
			String s = Integer.toHexString(0xFF & b);
			if (s.length() == 1)
				sb.append(0);
			sb.append(s);
		}
		return sb.toString();
	}

	public static String random(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("length can not be less than 1.");
		} else if (length == 1) {
			SecureRandom sr = new SecureRandom();
			return Integer.toHexString(sr.nextInt(15));
		} else {
			SecureRandom sr = new SecureRandom();
			byte[] bs = new byte[length / 2];
			sr.nextBytes(bs);
			return toHexString(bs);
		}
	}

	public static String md5(String key) {
		try {
			byte[] bs = MessageDigest.getInstance("MD5").digest(key.getBytes());
			return toHexString(bs);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
