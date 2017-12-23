package com.pkx.lottery.dto.corp;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class CorpFootballData {
	@Expose
	private String uid;
	@Expose
	private ArrayList<CorpFootballDetail> order_detail;
	@Expose
	private String intro;
	@Expose
	private String rest_count;
	@Expose
	private String keep_count;
	@Expose
	private String max_count;
	@Expose
	private String offer_count;
	@Expose
	private String unit_price;
	@Expose
	private String private_type;

	public String getUid() {
		return uid;
	}

	public CorpFootballDetail getOrder_detail() {
		return order_detail.get(0);
	}

	public String getIntro() {
		return intro;
	}

	public String getMax_count() {
		return max_count;
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

	public String getPrivate_type() {
		return private_type;
	}
}
