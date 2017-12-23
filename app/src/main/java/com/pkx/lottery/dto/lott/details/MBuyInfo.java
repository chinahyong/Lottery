package com.pkx.lottery.dto.lott.details;

import com.google.gson.annotations.Expose;

public class MBuyInfo {
	@Expose
	private String intro;
	@Expose
	private String process;
	@Expose
	private String keep_count;
	@Expose
	private String max_count;
	@Expose
	private String unit_price;
	@Expose
	private String commission;
	//
	@Expose
	private String nick_name;//合买发起人
	//

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getIntro() {
		return intro;
	}

	public String getProcess() {
		return process;
	}

	public String getKeep_count() {
		return keep_count;
	}

	public String getMax_count() {
		return max_count;
	}

	public String getUnit_price() {
		return unit_price;
	}

	public String getCommission() {
		return commission;
	}

}
