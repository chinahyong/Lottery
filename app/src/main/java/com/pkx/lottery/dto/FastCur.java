package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class FastCur {
	@Expose
	private int request_time;
	@Expose
	private CurFast data;
	@Expose
	private LastFast cur_data;

	public int getRequest_time() {
		return request_time;
	}

	public CurFast getCurFast() {
		return data;
	}

	public LastFast getLastFast() {
		return cur_data;
	}
}
