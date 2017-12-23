package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class FootballBetAuth {
	@Expose
	private String betAmount;
	@Expose
	private String betCount;
	@Expose
	private String betMulti;
	@Expose
	private String betInfo;
	@Expose
	private String uid;
	@Expose
	private String betType;
	

	public FootballBetAuth(String uid, String buy_amount, String buy_count,
			String buy_multiy, String betInfo,String betType) {
		this.betAmount = buy_amount;
		this.betCount = buy_count;
		this.betMulti = buy_multiy;
		this.betInfo = betInfo;
		this.uid = uid;
		this.betType=betType;
	}

	public String getBuy_amount() {
		return betAmount;
	}

	public String getBuy_count() {
		return betCount;
	}

	public String getBuy_multiy() {
		return betMulti;
	}

	public String getBetInfo() {
		return betInfo;
	}

	public String getUid() {
		return uid;
	}

	public String getBetAmount() {
		return betAmount;
	}

	public String getBetCount() {
		return betCount;
	}

	public String getBetMulti() {
		return betMulti;
	}

	public String getBetType() {
		return betType;
	}

}
