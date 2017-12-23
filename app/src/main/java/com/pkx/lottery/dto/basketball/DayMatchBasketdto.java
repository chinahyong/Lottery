package com.pkx.lottery.dto.basketball;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class DayMatchBasketdto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	private int total;
	@Expose
	private ArrayList<MatchBasketBallWFdto> list;

	public int getTotal() {
		return total;
	}

	public ArrayList<MatchBasketBallWFdto> getList() {
		return list;
	}
}
