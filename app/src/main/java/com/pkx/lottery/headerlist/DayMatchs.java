package com.pkx.lottery.headerlist;

import java.io.Serializable;
import java.util.ArrayList;

public class DayMatchs implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String date;
private ArrayList<Match> matchs;
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public ArrayList<Match> getMatchs() {
	return matchs;
}
public void setMatchs(ArrayList<Match> matchs) {
	this.matchs = matchs;
}
}
