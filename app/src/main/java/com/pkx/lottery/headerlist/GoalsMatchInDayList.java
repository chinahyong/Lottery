package com.pkx.lottery.headerlist;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class GoalsMatchInDayList {
	@Expose
	private ArrayList<GoalsMatchInDay> matchData;
	@Expose
	private ArrayList<String> scoreSpStr;

	public ArrayList<GoalsMatchInDay> getMatchData() {
		return matchData;
	}

	public ArrayList<String> getScoreSpStr() {
		return scoreSpStr;
	}
}
