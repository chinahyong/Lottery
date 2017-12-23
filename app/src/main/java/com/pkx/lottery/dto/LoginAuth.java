package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class LoginAuth {
	@Expose
	private String act = "doLogin";
	@Expose
	private String accountName;
	@Expose
	private String accountPassword;
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPassword() {
		return accountPassword;
	}
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	public String getAct() {
		return act;
	}
}
