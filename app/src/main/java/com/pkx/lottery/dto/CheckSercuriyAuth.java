package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class CheckSercuriyAuth {
	@Expose
	private String uid;

	public String getUid() {
		return uid;
	}

	@Expose
	private String answer_id;
	@Expose
	private String answer;
	@Expose
	private String act = "withdrawal_secret_security_a";

	public CheckSercuriyAuth(String id, String answer, String uid) {
		this.answer_id = id;
		this.answer = answer;
		this.uid = uid;
	}

	public String getAnswer_id() {
		return answer_id;
	}

	public String getAnswer() {
		return answer;
	}

	public String getAct() {
		return act;
	}
}
