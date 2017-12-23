package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class IndexListItemDto {
	@Expose
	private String logo;
	@Expose
	private String desc;
	@Expose
	private String bet_expire;
	@Expose
	private String rest_time;
	@Expose
	private String peroid_name;
	@Expose
	private int type_id;
	@Expose
	private String name;

	public String getLogo() {
		return logo;
	}

	public String getDesc() {
		return desc;
	}

	public String getBet_expire() {
		return bet_expire;
	}

	public String getRest_time() {
		return rest_time;
	}

	public String getPeroid_name() {
		return peroid_name;
	}

	public int getType_id() {
		return type_id;
	}

	public String getName() {
		return name;
	}
}
