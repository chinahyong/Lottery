package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class UpdateAuth {
	@Expose
	private String act = "checkUpdate";
	public String getAct() {
		return act;
	}
}
