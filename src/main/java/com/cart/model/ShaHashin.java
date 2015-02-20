package com.cart.model;

import java.security.MessageDigest;

public class ShaHashin {
	public String shaHashing(String pswd)throws Exception
    {
		 MessageDigest mDigest = MessageDigest.getInstance("SHA1");
	        byte[] result = mDigest.digest(pswd.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < result.length; i++) {
	            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
	        }
	         
	        return sb.toString();
    }
}
