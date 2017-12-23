package com.pkx.lottery.dto.lott.details;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class FootballLottMatchOddsItem {
	@Expose
	private String chuanGuan;
	@Expose
	private String bonus;
	@Expose
	private String bonus_status;
	@Expose
	private String ticketID;
	@Expose
	private ArrayList<MatchOdds> odds;

	public String getChuanGuan() {
		return chuanGuan;
	}

	public String getBonus() {
		return bonus;
	}

	public String getBonus_status() {
		return bonus_status;
	}

	public String getTicketID() {
		return ticketID;
	}

	public ArrayList<MatchOdds> getOdds() {
		return odds;
	}
}
