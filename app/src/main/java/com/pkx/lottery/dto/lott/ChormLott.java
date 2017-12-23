package com.pkx.lottery.dto.lott;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;

public class ChormLott {
	@Expose
	private String peroidID;
	@Expose
	private String peroid_name;
	@Expose
	private String lottery_type;
	@Expose
	private String start_date;
	@Expose
	private String end_date;
	@Expose
	private String res_date;
	@Expose
	private String bet_expire;
	@Expose
	private String send_bonus_time;
	@Expose
	private String bonus_number;
	@Expose
	private String bonus_total;
	@Expose
	private String prize_pool;
	@Expose
	private String sale_total;
	@Expose
	private ArrayList<ChormLottPrize> bonus_detail;
	@Expose
	private String status;

	// public ArrayList<ChormLottPrize> getChormPrice(Context ctx){
	// if(bonus_detail==null&& bonus_detail.length()==0){
	// return null;
	// }
	// String[]
	// pris=bonus_detail.substring(1,bonus_detail.length()-1).split(",");
	// ArrayList<ChormLottPrize> prizes=new ArrayList<ChormLottPrize>();
	// for(String pri:pris){
	// prizes.add(Net.gson.fromJson(pri, ChormLottPrize.class));
	// }
	// return prizes;
	// }
	public ArrayList<Integer> getBalls() {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		String[] balls = bonus_number.trim().split(" ");
		for (String b : balls) {
			if (b.length() > 0 && !b.equals("")) {
				ints.add(Integer.valueOf(b));
			}
		}
		return ints;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPeroidID() {
		return peroidID;
	}

	public void setPeroidID(String peroidID) {
		this.peroidID = peroidID;
	}

	public String getPeroid_name() {
		return peroid_name;
	}

	public void setPeroid_name(String peroid_name) {
		this.peroid_name = peroid_name;
	}

	public String getLottery_type() {
		return lottery_type;
	}

	public void setLottery_type(String lottery_type) {
		this.lottery_type = lottery_type;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getRes_date() {
		return res_date;
	}

	public void setRes_date(String res_date) {
		this.res_date = res_date;
	}

	public String getBet_expire() {
		return bet_expire;
	}

	public void setBet_expire(String bet_expire) {
		this.bet_expire = bet_expire;
	}

	public String getSend_bonus_time() {
		return send_bonus_time;
	}

	public ArrayList<ChormLottPrize> getBonus_detail() {
		return bonus_detail;
	}

	public void setSend_bonus_time(String send_bonus_time) {
		this.send_bonus_time = send_bonus_time;
	}

	public String getBonus_number() {
		return bonus_number;
	}

	public void setBonus_number(String bonus_number) {
		this.bonus_number = bonus_number;
	}

	public String getBonus_total() {
		return bonus_total;
	}

	public void setBonus_total(String bonus_total) {
		this.bonus_total = bonus_total;
	}

	public String getPrize_pool() {
		return prize_pool;
	}

	public void setPrize_pool(String prize_pool) {
		this.prize_pool = prize_pool;
	}

	public String getSale_total() {
		return sale_total;
	}

	public void setSale_total(String sale_total) {
		this.sale_total = sale_total;
	}
	// public String getBonus_detail() {
	// return bonus_detail;
	// }
	// public void setBonus_detail(String bonus_detail) {
	// this.bonus_detail = bonus_detail;
	// }
}
