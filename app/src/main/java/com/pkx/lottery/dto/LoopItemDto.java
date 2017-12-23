package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class LoopItemDto {
	@Expose
	private int type_id;
	@Expose
	private String imgurl;
	@Expose
	private String name;

	public int getType_id() {
		return type_id;
	}

	public String getImgurl() {
		return imgurl;
	}

	public String getName() {
		return name;
	}
}
