package com.pkx.lottery.headerlist;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class GoalsMatchInDay implements Serializable{
	private static final long serialVersionUID = 1L;
	@Expose
	private String total;
	@Expose
	private ArrayList<GoalsMatch> list;
	private String yearmonthday;
	public String getYearmonthday() {
		return yearmonthday;
	}

	public void setYearmonthday(String yearmonthday) {
		this.yearmonthday = yearmonthday;
	}

	public String getTotal() {
		return total;
	}

	public ArrayList<GoalsMatch> getList() {
		return list;
	}
}
