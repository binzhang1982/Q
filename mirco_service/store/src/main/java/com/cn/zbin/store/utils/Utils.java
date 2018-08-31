package com.cn.zbin.store.utils;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Utils {

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();
    
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
    
    public static Long getChinaCurrentTimeInMillis() {
    	Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.getTimeInMillis();
    }
    
    public static Long getChinaCurrentTimeInSeconds() {
    	BigDecimal mills = new BigDecimal(getChinaCurrentTimeInMillis());
    	return mills.divide(new BigDecimal(1000)).longValue();
    }
    
    public static String getTradeNo() {
    	Calendar cal = Calendar.getInstance(); 
    	Date date = cal.getTime();
    	//20160505010134364
    	String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);

        char[] nonceChars = new char[15];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars) + time;
    }
}
