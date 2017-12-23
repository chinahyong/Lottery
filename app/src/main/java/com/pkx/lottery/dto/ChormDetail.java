package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class ChormDetail {
	@Expose
	private ArrayList<String> bonus_number;
	@Expose
	private String send_bonus_time;
	@Expose
	private String prize_pool;
	@Expose
	private String bet_expire;
	@Expose
	private String status;
	@Expose
	private String peroidID;
	@Expose
	private String bonus_total;
	@Expose
	private ArrayList<String> red_ball;
	@Expose
	private String res_date;
	@Expose
	private String end_date;
	@Expose
	private String sale_total;
	@Expose
	private ArrayList<ChormBonus> bonus_detail;
	@Expose
	private String start_date;
	@Expose
	private String lottery_type;
	@Expose
	private String peroid_name;
	@Expose
	private String blue_ball;

	public ArrayList<String> getBonus_number() {
		return bonus_number;
	}

	public String getSend_bonus_time() {
		return send_bonus_time;
	}

	public String getPrize_pool() {
		return prize_pool;
	}

	public String getBet_expire() {
		return bet_expire;
	}

	public String getStatus() {
		return status;
	}

	public String getPeroidID() {
		return peroidID;
	}

	public String getBonus_total() {
		return bonus_total;
	}

	public ArrayList<String> getRed_ball() {
		return red_ball;
	}

	public String getRes_date() {
		return res_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public String getSale_total() {
		return sale_total;
	}

	public ArrayList<ChormBonus> getBonus_detail() {
		return bonus_detail;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getLottery_type() {
		return lottery_type;
	}

	public String getPeroid_name() {
		return peroid_name;
	}

	public String getBlue_ball() {
		return blue_ball;
	}

}
