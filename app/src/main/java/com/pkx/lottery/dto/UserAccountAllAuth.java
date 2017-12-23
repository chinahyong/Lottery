package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class UserAccountAllAuth {
	@Expose
	private String act = "getUserAccount";
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
