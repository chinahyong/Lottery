package com.pkx.lottery.dto.lott.details;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class FootballLottItem {
	@Expose
	private String bet_info;
	@Expose
	private String expire_date;
	@Expose
	private String chuanGuan;
	@Expose
	private String bet_type;
	@Expose
	private String lottery_type_ext;
	@Expose
	private String order_status;
	// @Expose
	// private String intro;
	@Expose
	private String pay_status;
	@Expose
	private String lottery_period;
	@Expose
	private String bet_multi;
	@Expose
	private String bet_count;
	@Expose
	private String oid;
	@Expose
	private String pay_date;
	@Expose
	private String bet_amount;
	@Expose
	private ArrayList<FootballLottMatchOddsItem> matchOdds;
	@Expose
	private ArrayList<FootballLottMatchItem> match;
	@Expose
	private String oiid;
	@Expose
	private String order_date;
	@Expose
	private String lottery_type;

	public String getBet_info() {
		return bet_info;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public String getChuanGuan() {
		return chuanGuan;
	}

	public String getBet_type() {
		return bet_type;
	}

	public String getLottery_type_ext() {
		return lottery_type_ext;
	}

	public String getOrder_status() {
		return order_status;
	}

	// public OrderInfo getOrder_info() {
	// return order_info;
	// }

	public String getPay_status() {
		return pay_status;
	}

	// public String getIntro() {
	// return intro;
	// }

	public String getLottery_period() {
		return lottery_period;
	}

	public String getBet_multi() {
		return bet_multi;
	}

	public String getBet_count() {
		return bet_count;
	}

	public String getOid() {
		return oid;
	}

	public String getPay_date() {
		return pay_date;
	}

	public String getBet_amount() {
		return bet_amount;
	}

	public ArrayList<FootballLottMatchItem> getMatch() {
		return match;
	}

	public String getOiid() {
		return oiid;
	}

	public ArrayList<FootballLottMatchOddsItem> getMatchOdds() {
		return matchOdds;
	}

	public String getOrder_date() {
		return order_date;
	}

	public String getLottery_type() {
		return lottery_type;
	}
}
