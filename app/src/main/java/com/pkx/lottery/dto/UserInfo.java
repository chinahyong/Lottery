package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class UserInfo {
	@Expose
	private String nick_name;
	@Expose
	private String uid;
	@Expose
	private String real_name;
	@Expose
	private String account;
	@Expose
	private String fund;// 账户余额
	@Expose
	private String mphone;
	@Expose
	private String blocked_fund;// 冻结资金

	public String getUid() {
		return uid;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

	public void setBlocked_fund(String blocked_fund) {
		this.blocked_fund = blocked_fund;
	}

	public String getReal_name() {
		return real_name;
	}

	public String getAccount() {
		return account;
	}

	public String getFund() {
		return fund;
	}

	public String getMphone() {
		return mphone;
	}

	public String getNick_name() {
		return nick_name;
	}

	public String getBlocked_fund() {
		return blocked_fund;
	}

}
