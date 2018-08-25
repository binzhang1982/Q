package com.cn.zbin.store.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;

public class QLHWXPayConfig implements WXPayConfig {
	private byte[] certData;
	private String appid;
	private String key;
	private String mchid;
	
	public QLHWXPayConfig(String certPath, String appid, 
			String key, String mchid) throws Exception {
		this.appid = appid;
		this.key = key;
		this.mchid = mchid;
		
		if (certPath != null) {
	        File file = new File(certPath);
	        InputStream certStream = new FileInputStream(file);
	        this.certData = new byte[(int) file.length()];
	        certStream.read(this.certData);
	        certStream.close();
		}
    }
	
	@Override
	public String getAppID() {
		return this.appid;
	}

	@Override
	public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 10000;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String getMchID() {
		return this.mchid;
	}
}
