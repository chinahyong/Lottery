package com.pkx.lottery.bean;

import java.util.ArrayList;

public class SelectFootBallTeam {
	private String teamNumber;
	private ArrayList<String> BF;
	private ArrayList<String> BQC;
	private ArrayList<String> SPF;
	private ArrayList<String> RQSPF;
	private ArrayList<String> JQS;

	public String getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(String teamNumber) {
		this.teamNumber = teamNumber;
	}

	public ArrayList<String> getBF() {
		return BF;
	}

	public void setBF(ArrayList<String> bF) {
		BF = bF;
	}

	public ArrayList<String> getBQC() {
		return BQC;
	}

	public void setBQC(ArrayList<String> bQC) {
		BQC = bQC;
	}

	public ArrayList<String> getSPF() {
		return SPF;
	}

	public void setSPF(ArrayList<String> sPF) {
		SPF = sPF;
	}

	public ArrayList<String> getRQSPF() {
		return RQSPF;
	}

	public void setRQSPF(ArrayList<String> rQSPF) {
		RQSPF = rQSPF;
	}

	public ArrayList<String> getJQS() {
		return JQS;
	}

	public void setJQS(ArrayList<String> jQS) {
		JQS = jQS;
	}

	
}
