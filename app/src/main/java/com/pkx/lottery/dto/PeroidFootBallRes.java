package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class PeroidFootBallRes {
	@Expose
	private String act;
	@Expose
	private String page;
	@Expose
	private String pageSize;

	public PeroidFootBallRes(String act, String pageSize, String page) {
		this.act = act;
		this.page = page;
		this.pageSize = pageSize;
	}

	public String getAct() {
		return act;
	}

	public String getPage() {
		return page;
	}

	public String getPageSize() {
		return pageSize;
	}
}
