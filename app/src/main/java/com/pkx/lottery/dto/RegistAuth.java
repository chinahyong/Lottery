package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class RegistAuth {
	@Expose
	private String act = "doReg";
	@Expose
	private String tel;
	@Expose
	private String accountName;
	@Expose
	private String accountPasswd;
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPasswd() {
		return accountPasswd;
	}
	public void setAccountPasswd(String accountPasswd) {
		this.accountPasswd = accountPasswd;
	}
	public String getAct() {
		return act;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}
