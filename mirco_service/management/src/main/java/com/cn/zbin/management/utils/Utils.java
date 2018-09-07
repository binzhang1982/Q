package com.cn.zbin.management.utils;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
    public static boolean stringNotEmpty(String res) {
        return null != res && !"".equals(res.trim());
    }

    public static boolean listNotNull(List a) {
        return null != a && !a.isEmpty();
    }

    public static Integer dayToInteger(String dayStr) {
        if ("Sun".equals(dayStr)) {
            return 1;
        } else if ("Mon".equals(dayStr)) {
            return 2;
        } else if ("Tue".equals(dayStr)) {
            return 3;
        } else if ("Wed".equals(dayStr)) {
            return 4;
        } else if ("Thu".equals(dayStr)) {
            return 5;
        } else if ("Fri".equals(dayStr)) {
            return 6;
        } else if ("Sat".equals(dayStr)) {
            return 7;
        }
        return 0;
    }

    public static String listToStringSpitByComma(List<String> strs){
        StringBuilder res = new StringBuilder();
        if (Utils.listNotNull(strs)) {
            for (String user : strs) {
                res.append(user);
                res.append(",");
            }
            res.substring(0, res.length() - 1);
        }
        return res.toString();
    }

	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
    public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	/**
	 * 替换字符串并让它的下一个字母为大写
	 * 
	 * @param srcStr
	 * @param org
	 * @param ob
	 * @return
	 */
    public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
		String newString = "";
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString = newString + srcStr.substring(0, first) + ob;
				srcStr = srcStr.substring(first + org.length(), srcStr.length());
				srcStr = firstCharacterToUpper(srcStr);
			}
		}
		newString = newString + srcStr;
		return newString;
	}
    
    public static Date getChinaCurrentTime() {
    	Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.getTime();
    }

	public static String MD5(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString(item & 0xFF | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	public static String HMACSHA256(String data) throws Exception {
		String key = MgmtKeyConstants.SMS_ACCESS_KEY_SECRET;
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"),
				"HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString(item & 0xFF | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}
}
