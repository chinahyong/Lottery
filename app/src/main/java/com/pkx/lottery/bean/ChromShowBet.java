package com.pkx.lottery.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ChromShowBet implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String time;
	private ArrayList<Integer> balls;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ArrayList<Integer> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<Integer> balls) {
		this.balls = balls;
	}

}
