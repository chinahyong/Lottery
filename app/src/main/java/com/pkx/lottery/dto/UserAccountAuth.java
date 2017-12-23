package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;
public class UserAccountAuth {
	// @Expose
	// private String act="getUserInfo";
	public UserAccountAuth(String u) {
		uid = u;
	}

	@Expose
	private String uid;

	public String getUid() {
		return uid;
	}

}
