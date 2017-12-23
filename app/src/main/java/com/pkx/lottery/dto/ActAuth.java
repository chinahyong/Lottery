package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class ActAuth {
	@Expose
	private String act;

	public String getAct() {
		return act;
	}

	public ActAuth(String act) {
		this.act = act;
	};

}
