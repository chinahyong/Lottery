package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class AuthJson {
	@Expose
	private String deviceSN;
	@Expose
	private String deviceMobile;

	public String getDeviceSN() {
		return deviceSN;
	}

	public void setDeviceSN(String deviceSN) {
		this.deviceSN = deviceSN;
	}

	public String getDeviceMobile() {
		return deviceMobile;
	}

	public void setDeviceMobile(String deviceMobile) {
		this.deviceMobile = deviceMobile;
	}
}
