package com.pkx.lottery.dto.corp;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class CorpFootballDetail {
	@Expose
	private String bet_info;
	@Expose
	private String expire_date;
	@Expose
	private String chuanGuan;
	@Expose
	private String bet_type;
	@Expose
	private String lottery_type_ext;
	@Expose
	private String order_status;
	@Expose
	private String pay_status;
	@Expose
	private String bet_multi;
	@Expose
	private String bet_count;
	@Expose
	private String bet_amount;
	@Expose
	private ArrayList<CorpFootballLottMatchItem> match;

	public String getBet_info() {
		return bet_info;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public String getChuanGuan() {
		return chuanGuan;
	}

	public String getBet_type() {
		return bet_type;
	}

	public String getLottery_type_ext() {
		return lottery_type_ext;
	}

	public String getOrder_status() {
		return order_status;
	}

	public String getPay_status() {
		return pay_status;
	}

	public String getBet_multi() {
		return bet_multi;
	}

	public String getBet_count() {
		return bet_count;
	}

	public String getBet_amount() {
		return bet_amount;
	}

	public ArrayList<CorpFootballLottMatchItem> getMatch() {
		return match;
	}

}
