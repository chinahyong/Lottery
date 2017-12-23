package com.pkx.lottery.dto.basketball;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class BasketMatchList {
	@Expose
	private ArrayList<DayMatchBasketdto> matchData;

	public ArrayList<DayMatchBasketdto> getMatchData() {
		return matchData;
	}
}
