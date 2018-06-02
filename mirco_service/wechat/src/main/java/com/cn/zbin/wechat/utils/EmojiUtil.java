package com.cn.zbin.wechat.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class EmojiUtil {
	/**
	 * @Description 过滤表情，替换成[-.-]
	 */
	public static String replaceContent(String content) {
		if (!StringUtils.isEmpty(content)) {
			String patternStr = "[^\\u0000-\\uFFFF]";
			Pattern pattern = Pattern.compile(patternStr, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(content);
			content = matcher.replaceAll("[-.-]");
		}
		return content;
	}

	/**
	 * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
	 */
	public static String emojiConvert(String str) throws UnsupportedEncodingException {
		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb, "[[" + URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
			} catch (UnsupportedEncodingException e) {
				throw e;
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
