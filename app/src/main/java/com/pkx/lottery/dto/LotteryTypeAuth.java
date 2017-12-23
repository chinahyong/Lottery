package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class LotteryTypeAuth {
	@Expose
	private String lottery_type;
	@Expose
	private String act;

	public LotteryTypeAuth(int type,String act) {
		lottery_type = String.valueOf(type);
		this.act=act;
	}

	public String getLottery_type() {
		return lottery_type;
	}

	public String getAct() {
		return act;
	}
	
}
