package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class ExceptionAuth {
	@Expose
	private int page;
	@Expose
	private int page_sum;

	public int getPage() {
		return page;
	}

	public int getPage_sum() {
		return page_sum;
	}
}
