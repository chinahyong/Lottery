package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class UserInfoAllAuth {
	@Expose
	private String act = "getUserInfo";
	@Expose
	private String DATA;

	public String getDATA() {
		return DATA;
	}

	public void setDATA(String dATA) {
		DATA = dATA;
	}

	public String getAct() {
		return act;
	}
}
