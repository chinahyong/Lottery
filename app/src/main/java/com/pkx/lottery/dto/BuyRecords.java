package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class BuyRecords {
	@Expose
	private ArrayList<BuyRecord> order_list;
	@Expose
	private String page;
	@Expose
	private String page_sum;
	public ArrayList<BuyRecord> getOrder_list() {
		return order_list;
	}
	public int getPage() {
		return Integer.valueOf(page);
	}
	public int getPage_sum() {
		return Integer.valueOf(page_sum);
	}
}
