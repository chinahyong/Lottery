package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class ChormBetAuth {
	@Expose
	private String buy_amount;
	@Expose
	private String buy_count;
	@Expose
	private String buy_multiy;
	@Expose
	private String betInfo;
	@Expose
	private String uid;
	@Expose
	private String buy_type;
	@Expose
	private String peroid_nums;

	public ChormBetAuth(String uid, String buy_amount, String buy_count,
			String buy_multiy, String betInfo, String buyType, int follow) {
		this.buy_amount = buy_amount;
		this.buy_count = buy_count;
		this.buy_multiy = buy_multiy;
		this.betInfo = betInfo;
		this.uid = uid;
		this.buy_type = buyType;
		this.peroid_nums = String.valueOf(follow);
	}

	public String getBuy_amount() {
		return buy_amount;
	}

	public String getBuy_count() {
		return buy_count;
	}

	public String getBuy_multiy() {
		return buy_multiy;
	}

	public String getBetInfo() {
		return betInfo;
	}

	public String getUid() {
		return uid;
	}

	public String getBuy_type() {
		return buy_type;
	}

	public String getPeroid_nums() {
		return peroid_nums;
	}

}
