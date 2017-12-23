package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class BuyRecord {
	@Expose
	private String oid;
	@Expose
	private String bet_amount;
	@Expose
	private String bet_count;
	@Expose
	private String bet_multi;
	@Expose
	private String is_multibuyer;
	@Expose
	private String order_data;
	@Expose
	private String pay_date;
	@Expose
	private String order_date;
	@Expose
	private String order_status;
	@Expose
	private String pay_status;
	@Expose
	private String buy_method;
	@Expose
	private String id;
	@Expose
	private String mboid;// mutibuy order id
	@Expose
	private String lotteryType;
	@Expose
	private String dateline;
	@Expose
	private String expire_date;
	@Expose
	private String mboiid;// mutibuy order item id
	@Expose
	private String offer_count;
	@Expose
	private String offer_date;
	@Expose
	private String offer_price;
	@Expose
	private String is_master;

	public String toString() {
		return "oid:" + oid + "bet_amount:" + bet_amount + "bet_count:"
				+ bet_count + "id:" + id + "mboid:" + mboid + "lotteryType:"
				+ lotteryType + "mboiid:" + mboiid + "is_master:" + is_master
				+ "order_status:" + order_status;
	}

	public String getOid() {
		return oid;
	}

	public String getBet_amount() {
		return bet_amount;
	}

	public String getBet_count() {
		return bet_count;
	}

	public String getBet_multi() {
		return bet_multi;
	}

	public String getOrder_date() {
		return order_date;
	}

	public String getOrder_data() {
		return order_data;
	}

	public String getPay_date() {
		return pay_date;
	}

	public String getOrder_status() {
		if(null==order_status)
			return "0";
		return order_status;
	}

	public String getIs_multibuyer() {
		return is_multibuyer;
	}

	public String getPay_status() {
		if(null==pay_status)
			return "0";
		return pay_status;
	}

	public String getBuy_method() {
		return buy_method;
	}

	public String getId() {
		return id;
	}

	public String getMboid() {
		return mboid;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public String getDateline() {
		return dateline;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public String getMboiid() {
		return mboiid;
	}

	public String getOffer_count() {
		return offer_count;
	}

	public String getOffer_date() {
		return offer_date;
	}

	public String getOffer_price() {
		return offer_price;
	}

	public String getIs_master() {
		return is_master;
	}
}
