package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CorpInfoAuth {
	@Expose
	private String uid;
	@Expose
	private String id;

	public CorpInfoAuth(String uid, String id) {
		this.uid = uid;
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public String getId() {
		return id;
	}
}
