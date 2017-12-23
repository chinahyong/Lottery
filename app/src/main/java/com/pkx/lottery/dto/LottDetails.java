package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class LottDetails {
	@Expose
	private String act;
	@Expose
	private String p;
	@Expose
	private String t;

	public LottDetails(String act, String p, String t) {
		this.act = act;
		this.p = p;
		this.t = t;
	}

	public String getAct() {
		return act;
	}

	public String getP() {
		return p;
	}

	public String getT() {
		return t;
	}
}
