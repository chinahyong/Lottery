package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class AccountDetailAuth {
	@Expose
	String uid;
	// @Expose
	// String act = "account_detail";
	@Expose
	String trade_detail;
	@Expose
	String page;

	public AccountDetailAuth(String uid, String trade_detail, int page) {
		this.uid = uid;
		this.trade_detail = trade_detail;
		this.page = String.valueOf(page);
	}
}
