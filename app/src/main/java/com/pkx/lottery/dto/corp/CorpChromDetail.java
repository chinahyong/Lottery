package com.pkx.lottery.dto.corp;

import com.google.gson.annotations.Expose;

public class CorpChromDetail {
	@Expose
	private String bet_info;
	@Expose
	private String expire_date;
	@Expose
	private String bet_type;
	@Expose
	private String order_status;
	@Expose
	private String pay_status;
	@Expose
	private String betStr;
	@Expose
	private String bet_multi;
	@Expose
	private String bet_count;
	@Expose
	private String bet_amount;
	@Expose
	private String order_date;
	@Expose
	private String betData_str;

	public String getBet_info() {
		return bet_info;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public String getBet_type() {
		return bet_type;
	}

	public String getOrder_status() {
		return order_status;
	}

	public String getPay_status() {
		return pay_status;
	}

	public String getBetStr() {
		return betStr;
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

	public String getOrder_date() {
		return order_date;
	}

	public String getBetData_str() {
		return betData_str;
	}
}
