package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class CorpAuthList {
	@Expose
	private ArrayList<CorpAuth> list;
	@Expose
	private ListFilter filter;

	public ArrayList<CorpAuth> getList() {
		return list;
	}

	public int getCount() {
		return filter.getRecord_count();
	}

}
