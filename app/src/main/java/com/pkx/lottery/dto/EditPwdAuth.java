package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class EditPwdAuth {
	@Expose
	private String uid;
	@Expose
	private String oldPassword;
	@Expose
	private String newPassword;

	public EditPwdAuth(String id, String oldP, String newP) {
		uid = id;
		oldPassword = oldP;
		newPassword = newP;
	}

	public String getUid() {
		return uid;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

}
