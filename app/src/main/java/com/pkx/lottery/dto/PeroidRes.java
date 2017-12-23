package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class PeroidRes {
	@Expose
	private String act;
	@Expose
	private String lottery_type;
	@Expose
	private String page;
	@Expose
	private String pageSize;

	public PeroidRes(String act, String lottery_type,
			String pageSize, String page) {
		this.act = act;
		this.lottery_type = lottery_type;
		this.page = page;
		this.pageSize = pageSize;
	}

	public String getAct() {
		return act;
	}

	public String getLottery_type() {
		return lottery_type;
	}

	public String getPage() {
		return page;
	}

	public String getPageSize() {
		return pageSize;
	}
}
