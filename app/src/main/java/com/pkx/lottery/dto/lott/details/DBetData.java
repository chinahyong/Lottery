package com.pkx.lottery.dto.lott.details;

import com.google.gson.annotations.Expose;

public class DBetData {
	@Expose
	private String bet_type;
	@Expose
	private String lottery_type_ext;
	@Expose
	private String order_status;
	@Expose
	private String pay_status;
	@Expose
	private String lottery_period;
	@Expose
	private String betStr;
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
	private String oiid;
	@Expose
	private String order_date;
	@Expose
	private String lottery_type;

	// private String ;
	// private String ;
	// private String ;
	public String getBet_type() {
		return bet_type;
	}

	public String getLottery_type_ext() {
		return lottery_type_ext;
	}

	public String getOrder_status() {
		return order_status;
	}

	public String getPay_status() {
		return pay_status;
	}

	public String getLottery_period() {
		return lottery_period;
	}

	public String getBetStr() {
		return betStr;
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

	public String getOiid() {
		return oiid;
	}

	public String getOrder_date() {
		return order_date;
	}

	public String getLottery_type() {
		return lottery_type;
	}

	// public String[] getD3() {
	// return d3;
	// }
}
