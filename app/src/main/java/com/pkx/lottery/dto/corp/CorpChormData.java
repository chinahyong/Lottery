package com.pkx.lottery.dto.corp;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class CorpChormData {
	@Expose
	private String uid;
	@Expose
	private String bet_expire;
	@Expose
	private String rest_time;
	@Expose
	private String rest_count;
	@Expose
	private String keep_count;
	@Expose
	private String offer_count;
	@Expose
	private String unit_price;
	@Expose
	private String intro;
	@Expose
	private String nick_name;
	@Expose
	private int process;
	@Expose
	private ArrayList<CorpChromDetail> order_detail;

	public String getUid() {
		return uid;
	}

	public String getBet_expire() {
		return bet_expire;
	}

	public String getRest_time() {
		return rest_time;
	}

	public String getRest_count() {
		return rest_count;
	}

	public String getKeep_count() {
		return keep_count;
	}

	public String getOffer_count() {
		return offer_count;
	}

	public String getUnit_price() {
		return unit_price;
	}

	public String getIntro() {
		return intro;
	}

	public String getNick_name() {
		return nick_name;
	}

	public int getProcess() {
		return process;
	}

	public ArrayList<CorpChromDetail> getOrder_detail() {
		return order_detail;
	}
}
