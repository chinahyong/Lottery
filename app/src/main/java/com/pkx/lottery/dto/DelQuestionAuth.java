package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class DelQuestionAuth {
	@Expose
	private String uid;
	@Expose
	private String secret_security;
	@Expose
	private String answer;
	@Expose
	private String secret_id;

	public DelQuestionAuth(String uid, String secret_security, String answer,String secret_id) {
		this.uid = uid;
		this.secret_security = secret_security;
		this.answer = answer;
		this.secret_id=secret_id;
	}

	public String getSid() {
		return secret_id;
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
