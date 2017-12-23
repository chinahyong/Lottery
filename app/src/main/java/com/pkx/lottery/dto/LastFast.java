package com.pkx.lottery.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class LastFast {
	@Expose
	private String peroidID;
	@Expose
	private String peroid_name;
	@Expose
	private String lottery_type;
	@Expose
	private String bonus_number;

	public ArrayList<Integer> getBalls() {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		String[] balls = bonus_number.trim().split(" ");
		for (String b : balls) {
			try {
				if (b.length() > 0 && !b.equals("")) {
					ints.add(Integer.valueOf(b));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ints;
	}

	public String getPeroidID() {
		return peroidID;
	}

	public String getPeroid_name() {
		return peroid_name;
	}

	public String getLottery_type() {
		return lottery_type;
	}

	public String getBonus_number() {
		return bonus_number;
	}
}
