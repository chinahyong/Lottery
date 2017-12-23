package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CashCheckDto {
	@Expose
	private String err;
	@Expose
	private String status;
	@Expose
	private String Token;
	@Expose
	private String msg;
	@Expose
	private CardInfo user_bank;

	public String getErr() {
		return err;
	}

	public String getStatus() {
		return status;
	}

	public String getToken() {
		return Token;
	}

	public String getMsg() {
		return msg;
	}

	public CardInfo getUser_bank() {
		return user_bank;
	}

}
