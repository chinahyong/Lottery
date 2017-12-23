package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CashingRecoredDto {
	@Expose
	private String process_date;
	@Expose
	private String uid;
	@Expose
	private String bank_card;
	@Expose
	private String bank_name;
	@Expose
	private String apply_date;
	@Expose
	private String apply_status;
	@Expose
	private String money;
	@Expose
	private String bank_type;
	@Expose
	private String apply_id;

	public String getProcess_date() {
		return process_date;
	}

	public String getUid() {
		return uid;
	}

	public String getBank_card() {
		return bank_card;
	}

	public String getBank_name() {
		return bank_name;
	}

	public String getApply_date() {
		return apply_date;
	}

	public String getApply_status() {
		return apply_status;
	}

	public String getMoney() {
		return money;
	}

	public String getBank_type() {
		return bank_type;
	}

	public String getApply_id() {
		return apply_id;
	}
}
