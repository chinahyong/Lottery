package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class ChormCorpBetAuth {
	@Expose
	private String buy_amount;
	@Expose
	private String buy_type;
	@Expose
	private String buy_count;
	@Expose
	private String buy_multiy;
	@Expose
	private String betInfo;
	@Expose
	private String uid;
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

	public ChormCorpBetAuth(String uid, String buy_amount, String buy_count,
			String buy_multiy, String betInfo, String buyType) {
		this.buy_amount = buy_amount;
		this.buy_count = buy_count;
		this.buy_multiy = buy_multiy;
		this.betInfo = betInfo;
		this.uid = uid;
		this.buy_type = buyType;
	}

	public String getBuy_type() {
		return buy_type;
	}

	public void setBuy_type(String buy_type) {
		this.buy_type = buy_type;
	}

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

}
