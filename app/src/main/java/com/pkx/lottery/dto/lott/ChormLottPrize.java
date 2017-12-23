package com.pkx.lottery.dto.lott;

import com.google.gson.annotations.Expose;

public class ChormLottPrize {
	@Expose
	private String key;
	@Expose
	private String bet;
	@Expose
	private String prize;

	public String getKey() {
		return key;
	}

	public String getBet() {
		return bet;
	}

	public String getPrize() {
		return prize;
	}
}
