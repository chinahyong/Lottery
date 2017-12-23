package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CashAuth {
	@Expose
	private String Token;
	@Expose
	private String money;
	@Expose
	private String uid;

	public CashAuth(String token, String money, String uid) {
		this.Token = token;
		this.money = money;
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public String getToken() {
		return Token;
	}

	public String getMoney() {
		return money;
	}
}
