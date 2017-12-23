package com.pkx.lottery.headerlist;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class HalfMatchInDay implements Serializable{
	private static final long serialVersionUID = 1L;
	@Expose
	private String total;
	@Expose
	private ArrayList<HalfMatch> list;
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

	public ArrayList<HalfMatch> getList() {
		return list;
	}
}
