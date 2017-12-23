package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class UserInfoDto {
	@Expose
	private String status;
	@Expose
	private UserInfo userInfo;

	public String getStatus() {
		return status;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

}
