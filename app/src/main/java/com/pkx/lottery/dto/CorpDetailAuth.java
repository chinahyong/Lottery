package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CorpDetailAuth {
	@Expose
	private String id;

	public CorpDetailAuth(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
