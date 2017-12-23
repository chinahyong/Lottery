package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class ChormBonus {
	@Expose
	private String key;
	@Expose
	private String prize;
	@Expose
	private String bet;

	public String getKey() {
		return key;
	}

	public String getPrize() {
		return prize;
	}

	public String getBet() {
		return bet;
	}

}
