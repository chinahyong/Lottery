package com.pkx.lottery.headerlist;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class HalfMatchInDayList {
	@Expose
	private ArrayList<HalfMatchInDay> matchData;
	@Expose
	private ArrayList<String> scoreSpStr;

	public ArrayList<HalfMatchInDay> getMatchData() {
		return matchData;
	}

	public ArrayList<String> getScoreSpStr() {
		return scoreSpStr;
	}

}
