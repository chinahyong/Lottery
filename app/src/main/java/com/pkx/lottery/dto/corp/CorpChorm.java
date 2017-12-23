package com.pkx.lottery.dto.corp;

import com.google.gson.annotations.Expose;

public class CorpChorm {
	@Expose
	private CorpChormData data;
	@Expose
	private String error;

	public CorpChormData getData() {
		return data;
	}

	public String getError() {
		return error;
	}
}
