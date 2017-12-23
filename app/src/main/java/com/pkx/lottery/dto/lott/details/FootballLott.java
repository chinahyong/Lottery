package com.pkx.lottery.dto.lott.details;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class FootballLott {
	@Expose
	private ArrayList<FootballLottItem> data;
	@Expose
	private String status;

	@Expose
	private MBuyInfo multibuy_info;

	@Expose
	private OrderInfo order_info;

	public OrderInfo getOrder_info() {
		return order_info;
	}

	public MBuyInfo getMultibuy_info() {
		return multibuy_info;
	}

	public FootballLottItem getData() {
		return data.get(0);
	}

	public String getStatus() {
		return status;
	}
}
