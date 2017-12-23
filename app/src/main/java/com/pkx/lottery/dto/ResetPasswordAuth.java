package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class ResetPasswordAuth {
	@Expose
	private String act = "sendPassword";
	@Expose
	private String code;

	public ResetPasswordAuth(String code) {
		this.code = code;
	}

	public String getAct() {
		return act;
	}

	public String getCode() {
		return code;
	}
}
