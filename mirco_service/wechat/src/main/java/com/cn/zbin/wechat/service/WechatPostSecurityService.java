package com.cn.zbin.wechat.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.cn.zbin.wechat.utils.WechatConstants;

@Service
public class WechatPostSecurityService {
	
	public Boolean checkSignature(String signature, String timestamp, String nonce) {
		// 排序
		String sortString = sort(WechatConstants.SERVER_POST_TOKEN, timestamp, nonce);
		// 加密
		String myString = sha1(sortString);
		// 校验
		if (myString != null && myString != "" && myString.equals(signature)) {
			System.out.println("签名校验通过");
			return Boolean.TRUE;
		} else {
			System.out.println("签名校验失败");
			return Boolean.FALSE;
		}
	}
	
	private String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);
		StringBuilder sb = new StringBuilder();
		for (String str : strArray) {
			sb.append(str);
		}
		return sb.toString();
	}

	private String sha1(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
