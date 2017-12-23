package com.pkx.lottery.headerlist;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class MatchesInDayList {
	@Expose
	private ArrayList<MatchesInDay> matchData;

	public ArrayList<MatchesInDay> getMatchData() {
		return matchData;
	}
}
