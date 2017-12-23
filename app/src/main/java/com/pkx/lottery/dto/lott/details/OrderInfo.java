package com.pkx.lottery.dto.lott.details;

import com.google.gson.annotations.Expose;

public class OrderInfo {
	@Expose
	private String is_multibuyer;
	@Expose
	private String private_type;
	@Expose
	private String keep_count;
	@Expose
	private String offer_count;

	public String getIs_multibuyer() {
		return is_multibuyer;
	}
	//
	@Expose
	private String bet_amount;//总金额
	@Expose
	private String lottery_type;//彩票类型
	@Expose
	private String peroid_name;//彩票期号
	@Expose
	private String bet_expire;//开奖时间
	@Expose
	private String order_date;//认购时间
	@Expose
	private String max_count;//合买总份额
	//
	@Expose
	private String order_status;
	@Expose
	private String unit_price;
	@Expose
	private String offer_price;
	@Expose
	private String pay_status;
	@Expose
	private String res_date;
	@Expose
	private String buy_method;
	@Expose
	private String No;
	@Expose
	private String peroid_id;
	@Expose
	private String is_master;
	@Expose
	private String pay_date;
	@Expose
	private String intro;
	@Expose
	private String bonus_number;

	//
	public String getPrivate_type() {
		return private_type;
	}

	public String getKeep_count() {
		return keep_count;
	}

	public String getOffer_count() {
		return offer_count;
	}

	public String getOrder_status() {
		return order_status;
	}

	public String getUnit_price() {
		return unit_price;
	}

	public String getOffer_price() {
		return offer_price;
	}

	public String getPay_status() {
		return pay_status;
	}

	public String getRes_date() {
		return res_date;
	}

	public String getBuy_method() {
		return buy_method;
	}

	public String getNo() {
		return No;
	}

	public String getPeroid_id() {
		return peroid_id;
	}

	public String getIs_master() {
		return is_master;
	}

	public String getPay_date() {
		return pay_date;
	}

	public String getIntro() {
		return intro;
	}
	//
	public String getBet_amount() {
		return bet_amount;
	}

	public void setBet_amount(String bet_amount) {
		this.bet_amount = bet_amount;
	}
	public String getLottery_type() {
		return lottery_type;
	}

	public void setLottery_type(String lottery_type) {
		this.lottery_type = lottery_type;
	}
	public String getPeroid_name() {
		return peroid_name;
	}

	public void setPeroid_name(String peroid_name) {
		this.peroid_name = peroid_name;
	}
	
	public String getBet_expire() {
		return bet_expire;
	}

	public void setBet_expire(String bet_expire) {
		this.bet_expire = bet_expire;
	}
	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	
	public String getMax_count() {
		return max_count;
	}

	public void setMax_count(String max_count) {
		this.max_count = max_count;
	}
	
	public String getBonus_number() {
		return bonus_number;
	}

	public void setBonus_number(String bonus_number) {
		this.bonus_number = bonus_number;
	}
	//
}
