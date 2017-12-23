package com.pkx.lottery.dto.lott.details;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;

public class CorpDetail {
	@Expose
	private String status;
	@Expose
	private ArrayList<ChormItem> data;
	@Expose
	private MBuyInfo multibuy_info;

	@Expose
	private OrderInfo order_info;

	public String getStatus() {
		return status;
	}

	public ArrayList<ChormItem> getData() {
		return data;
	}

	public MBuyInfo getMultibuy_info() {
		return multibuy_info;
	}

	public OrderInfo getOrder_info() {
		return order_info;
	}

}
