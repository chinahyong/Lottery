package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class AccountDetailListDto {
	@Expose
	private ArrayList<AccountDetailItemDto> log_list;
	@Expose
	private int page;
	@Expose
	private int page_sum;

	public ArrayList<AccountDetailItemDto> getLog_list() {
		return log_list;
	}

	public int getPage() {
		return page;
	}

	public int getPage_sum() {
		return page_sum;
	}
}
