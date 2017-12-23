package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CardInfo {
	@Expose
	private String uid;
	@Expose
	private String bank_name;
	@Expose
	private String bank_card;
	@Expose
	private String bank_bind_id;
	@Expose
	private String bank_type;
	@Expose
	private String opening_bank;

	public String getUid() {
		return uid;
	}

	public String getBank_name() {
		return bank_name;
	}

	public String getBank_card() {
		return bank_card;
	}

	public String getBank_bind_id() {
		return bank_bind_id;
	}

	public String getBank_type() {
		return bank_type;
	}

	public String getOpening_bank() {
		return opening_bank;
	}
}
