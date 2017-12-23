package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class LoopDto {
	@Expose
	private String message;
	@Expose
	private int status;
	@Expose
	private ArrayList<LoopItemDto> data;

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public ArrayList<LoopItemDto> getLoops() {
		return data;
	}
}
