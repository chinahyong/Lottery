package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class ListFilter {
	@Expose
	private String record_count;

	public int getRecord_count() {
		return Integer.valueOf(record_count);
	}

}
