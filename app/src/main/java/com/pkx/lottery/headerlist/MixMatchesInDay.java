package com.pkx.lottery.headerlist;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class MixMatchesInDay implements Serializable{
	private static final long serialVersionUID = 1L;
	@Expose
	private String total;
	@Expose
	private ArrayList<MixMatch> list;
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

	public ArrayList<MixMatch> getList() {
		return list;
	}
}
