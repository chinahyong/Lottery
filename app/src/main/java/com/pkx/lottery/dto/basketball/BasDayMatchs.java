package com.pkx.lottery.dto.basketball;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

public class BasDayMatchs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;

	private ArrayList<MatchBasketBallWFdto> matchs;

	public String getTotal() {
		return String.valueOf(matchs.size());
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<MatchBasketBallWFdto> getMatchs() {
		return matchs;
	}

	public void setMatchs(ArrayList<MatchBasketBallWFdto> matchs) {
		Log.e("pkx", "setMatchs11111111111111:"+matchs.size());
		this.matchs = matchs;
		Log.e("pkx", "setMatchs22222222222222:"+matchs.size());
	}

}
