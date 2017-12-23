package com.pkx.lottery.dto;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class CorpAuth implements Serializable{
	private static final long serialVersionUID = 1L;
	@Expose
	private int buy_amount_rate;
	@Expose
	private String uid;
	@Expose
	private String mboid;
	@Expose
	private String bet_expire;
	@Expose
	private int rest_count;
	@Expose
	private int keep_count;
	@Expose
	private String offer_count;
	@Expose
	private String unit_price;
	@Expose
	private String intro;
	@Expose
	private String return_win_rate;
	@Expose
	private int process;
	@Expose
	private String nick_name;
	@Expose
	private String private_type;
	@Expose
	private String end_date;
	@Expose
	private String oid;
	@Expose
	private String max_count;
	@Expose
	private String bet_amount;
	@Expose
	private String order_date;
	@Expose
	private String commission;
	@Expose
	private String start_date;
	@Expose
	private String peroid_name;
	@Expose
	private String lottery_type;
	public int getBuy_amount_rate() {
		return buy_amount_rate;
	}
	public String getUid() {
		return uid;
	}
	public String getMboid() {
		return mboid;
	}
	public String getBet_expire() {
		return bet_expire;
	}
	public int getRest_count() {
		return rest_count;
	}
	public int getKeep_count() {
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
	public String getReturn_win_rate() {
		return return_win_rate;
	}
	public int getProcess() {
		return process;
	}
	public String getNick_name() {
		return nick_name;
	}
	public String getPrivate_type() {
		return private_type;
	}
	public String getEnd_date() {
		return end_date;
	}
	public String getOid() {
		return oid;
	}
	public String getMax_count() {
		return max_count;
	}
	public String getBet_amount() {
		return bet_amount;
	}
	public String getOrder_date() {
		return order_date;
	}
	public String getCommission() {
		return commission;
	}
	public String getStart_date() {
		return start_date;
	}
	public String getPeroid_name() {
		return peroid_name;
	}
	public String getLottery_type() {
		return lottery_type;
	}
}
