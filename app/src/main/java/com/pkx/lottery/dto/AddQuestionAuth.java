package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class AddQuestionAuth {
	@Expose
	private String uid;
	@Expose
	private String secret_security;
	@Expose
	private String answer;

	public AddQuestionAuth(String uid, String secret_security, String answer) {
		this.uid = uid;
		this.secret_security = secret_security;
		this.answer = answer;
	}

	public String getUid() {
		return uid;
	}

	public String getSecret_security() {
		return secret_security;
	}

	public String getAnswer() {
		return answer;
	}
}
