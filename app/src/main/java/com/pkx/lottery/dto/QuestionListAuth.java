package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class QuestionListAuth {
	@Expose
	private int num;
	@Expose
	private ArrayList<QuestionAuth> secret_security;

	public ArrayList<QuestionAuth> getSecret_security() {
		return secret_security;
	}

	public int getNum() {
		return num;
	}

}
