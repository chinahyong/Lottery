package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class CashingRecoredListDto {

	@Expose
	private ArrayList<CashingRecoredDto> list;
	@Expose
	private String status;

	public ArrayList<CashingRecoredDto> getList() {
		return list;
	}

	public String getStatus() {
		return status;
	}
}
