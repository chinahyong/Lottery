package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CheckBean {
	@Expose
	private String apkURL;
	@Expose
	private String versionCode;
	@Expose
	private String version;

	public String getApkURL() {
		return apkURL;
	}

	public int getVersionCode() {
		try {
			return Integer.valueOf(versionCode);
		} catch (Exception e) {
			return 0;
		}
	}

	public String getVersion() {
		return version;
	}
}
