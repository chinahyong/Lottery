package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CancelCashingAuth {
	@Expose
	private String uid;
	@Expose
	private String aid;

	public CancelCashingAuth(String uid, String aid) {
		this.uid = uid;
		this.aid = aid;
	}

	public String getUid() {
		return uid;
	}

	public String getAid() {
		return aid;
	}
}
