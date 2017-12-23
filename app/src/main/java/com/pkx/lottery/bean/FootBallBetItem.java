package com.pkx.lottery.bean;

import java.util.ArrayList;

public class FootBallBetItem {
	private int id;
	private String handic;
	private ArrayList<Integer> selects;
	private int playType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHandic() {
		return handic;
	}

	public void setHandic(String handic) {
		this.handic = handic;
	}

	public ArrayList<Integer> getSelects() {
		return selects;
	}

	public void setSelects(ArrayList<Integer> selects) {
		this.selects = selects;
	}

	public int getPlayType() {
		return playType;
	}

	public void setPlayType(int playType) {
		this.playType = playType;
	}
}
