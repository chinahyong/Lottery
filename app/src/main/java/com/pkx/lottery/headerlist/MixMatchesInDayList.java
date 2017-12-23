package com.pkx.lottery.headerlist;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class MixMatchesInDayList {
	@Expose
	private ArrayList<MixMatchesInDay> matchData;
	@Expose
	private ArrayList<String> scoreSpStr;

	public ArrayList<MixMatchesInDay> getMatchData() {
		return matchData;
	}

	public ArrayList<String> getScoreSpStr() {
		return scoreSpStr;
	}
}
