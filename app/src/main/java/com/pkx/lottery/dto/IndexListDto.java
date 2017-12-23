package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class IndexListDto {
	@Expose
	private String message;
	@Expose
	private ArrayList<IndexListItemDto> data;

	public String getMessage() {
		return message;
	}

	public ArrayList<IndexListItemDto> getIndexList() {
		return data;
	}

}
