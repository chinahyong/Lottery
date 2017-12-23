package com.pkx.lottery.headerlist;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class PointsMatchInDayList {
	@Expose
	private ArrayList<PointsMatchInDay> matchData;
	@Expose
	private ArrayList<String> scoreSpStr;

	public ArrayList<PointsMatchInDay> getMatchData() {
		return matchData;
	}

	public ArrayList<String> getScoreSpStr() {
		return scoreSpStr;
	}

}
