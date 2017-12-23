package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class QuestionAuth {
	@Expose
	private String answer;
	@Expose
	private String uid;
	@Expose
	private String id;
	@Expose
	private String secret_security;

	public String getAnswer() {
		return answer;
	}

	public String getUid() {
		return uid;
	}

	public String getId() {
		return id;
	}

	public String getSecret_security() {
		return secret_security;
	}

}
