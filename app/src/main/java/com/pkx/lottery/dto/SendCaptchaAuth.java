package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class SendCaptchaAuth {
	@Expose
	private String act = "sendCaptcha";
	@Expose
	private String mode = "2";
	@Expose
	private String val;

	public SendCaptchaAuth(String phone) {
		val = phone;
	}
}
