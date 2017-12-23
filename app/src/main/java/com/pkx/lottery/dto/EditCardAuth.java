package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class EditCardAuth {
	@Expose
	private String uid;
	@Expose
	private String bank_type;
	@Expose
	private String bank_card;
	@Expose
	private String bank_name;// 开卡人姓名
	@Expose
	private String opening_bank;
	@Expose
	private String bank_bind_id;

	public EditCardAuth(String uid, String bankName, String cardNumber,
			String userName, String bankInfo,String bank_bind_id) {
		this.uid = uid;
		this.bank_type = bankName;
		this.bank_card = cardNumber;
		this.bank_name = userName;
		this.opening_bank = bankInfo;
		this.bank_bind_id=bank_bind_id;
	}

	public String getUid() {
		return uid;
	}

	public String getBank_type() {
		return bank_type;
	}

	public String getBank_card() {
		return bank_card;
	}

	public String getBank_bind_id() {
		return bank_bind_id;
	}

	public String getBank_name() {
		return bank_name;
	}

	public String getOpening_bank() {
		return opening_bank;
	}
}
