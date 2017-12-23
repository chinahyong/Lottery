package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class FootballCorpBetAuth {
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
	@Expose
	private String fangan_title;
	@Expose
	private String fangan_yongjin;
	@Expose
	private String fangan_num;
	@Expose
	private String fangan_self;
	@Expose
	private String fangan_baodi;
	@Expose
	private String fangan_show;

	public String getFangan_title() {
		return fangan_title;
	}

	public void setFangan_title(String fangan_title) {
		this.fangan_title = fangan_title;
	}

	public String getFangan_yongjin() {
		return fangan_yongjin;
	}

	public void setFangan_yongjin(String fangan_yongjin) {
		this.fangan_yongjin = fangan_yongjin;
	}

	public String getFangan_num() {
		return fangan_num;
	}

	public void setFangan_num(String fangan_num) {
		this.fangan_num = fangan_num;
	}

	public String getFangan_self() {
		return fangan_self;
	}

	public void setFangan_self(String fangan_self) {
		this.fangan_self = fangan_self;
	}

	public String getFangan_baodi() {
		return fangan_baodi;
	}

	public void setFangan_baodi(String fangan_baodi) {
		this.fangan_baodi = fangan_baodi;
	}

	public String getFangan_show() {
		return fangan_show;
	}

	public void setFangan_show(String fangan_show) {
		this.fangan_show = fangan_show;
	}

	public String getBetType() {
		return betType;
	}

	public FootballCorpBetAuth(String uid, String buy_amount, String buy_count,
			String buy_multiy, String betInfo, String betType) {
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

}
