package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class BetRecord {
	@Expose
	private String uid;
	@Expose
	private int page;

	public BetRecord(String uid, int page) {
		this.uid = uid;
		this.page = page;
	}

	public String getUid() {
		return uid;
	}

	public int getPage() {
		return page;
	}
}
