package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CorperBuyAuth {
	@Expose
	private String uid;
	@Expose
	private String id;
	@Expose
	private String count;

	public CorperBuyAuth(String uid, String id, String count) {
		this.uid = uid;
		this.id = id;
		this.count = count;
	}

	public String getUid() {
		return uid;
	}

	public String getId() {
		return id;
	}

	public String getCount() {
		return count;
	}
}
