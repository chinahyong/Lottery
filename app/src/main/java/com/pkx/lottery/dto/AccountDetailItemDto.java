package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class AccountDetailItemDto {
	@Expose
	private String amount;
	@Expose
	private String uid;
	@Expose
	private String trade_date;
	@Expose
	private String remark;
	@Expose
	private String trade_detail;
	@Expose
	private String last_amount;
	@Expose
	private String trade_type;
	@Expose
	private String log_id;

	public String getAmount() {
		return amount;
	}

	public String getUid() {
		return uid;
	}

	public String getTrade_date() {
		return trade_date;
	}

	public String getRemark() {
		return remark;
	}

	public String getTrade_detail() {
		return trade_detail;
	}

	public String getLast_amount() {
		return last_amount;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public String getLog_id() {
		return log_id;
	}
}
