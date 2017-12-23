package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class PublicAllAuth {
	@Expose
	private String act;
	@Expose
	private String DATA;
	public PublicAllAuth(String act,String data){
		this.act=act;
		DATA=data;
	}
	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getDATA() {
		return DATA;
	}

	public void setDATA(String dATA) {
		DATA = dATA;
	}
}
