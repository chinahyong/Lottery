package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class UserInfoAuth {
	// @Expose
	// private String act="getUserInfo";
	@Expose
	private String uid;

	public UserInfoAuth(String u) {
		uid = u;
	}

	public String getUid() {
		return uid;
	}

}
