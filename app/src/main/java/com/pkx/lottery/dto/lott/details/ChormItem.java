package com.pkx.lottery.dto.lott.details;

import com.google.gson.annotations.Expose;

public class ChormItem {
	@Expose
	private String expire_date;
	@Expose
	private String bonus_tax;
	@Expose
	private String bet_amount;
	@Expose
	private String lottery_type_ext;
	@Expose
	private String pay_status;
	@Expose
	private String sysID;
	@Expose
	private String oid;
	@Expose
	private String oiid;
	@Expose
	private String tid;
	@Expose
	private String order_date;
	@Expose
	private String lottery_type;
	@Expose
	private String orderID;
	@Expose
	private String status;
	// private String betData;
	@Expose
	private String bet_type;
	@Expose
	private String bonus_time;
	@Expose
	private String bonus_status;
	@Expose
	private String order_status;
	@Expose
	private String isbig;
	@Expose
	private String lottery_period;
	@Expose
	private String betStr;
	@Expose
	private String bet_multi;
	@Expose
	private String bet_count;
	@Expose
	private String betData_str;
	//
	@Expose
	private String amount;
	@Expose
	private String ticketID;
	@Expose
	private String ticket_status;

	//

	public String getTicket_status() {
		return ticket_status;
	}

	public void setTicket_status(String ticket_status) {
		this.ticket_status = ticket_status;
	}

	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public String getBonus_tax() {
		return bonus_tax;
	}

	public String getLottery_type_ext() {
		return lottery_type_ext;
	}

	public String getPay_status() {
		return pay_status;
	}

	public String getSysID() {
		return sysID;
	}

	public String getBet_amount() {
		return bet_amount;
	}

	public String getOid() {
		return oid;
	}

	public String getOiid() {
		return oiid;
	}

	public String getTid() {
		return tid;
	}

	public String getOrder_date() {
		return order_date;
	}

	public String getLottery_type() {
		return lottery_type;
	}

	public String getOrderID() {
		return orderID;
	}

	public String getStatus() {
		return status;
	}

	public String getBet_type() {
		return bet_type;
	}

	public String getBonus_time() {
		return bonus_time;
	}

	public String getBonus_status() {
		return bonus_status;
	}

	public String getOrder_status() {
		return order_status;
	}

	public String getIsbig() {
		return isbig;
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

	public String getBetData_str() {
		return betData_str;
	}
	//
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	//
}
