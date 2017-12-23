package com.pkx.lottery.dto.lott.details;

import com.google.gson.annotations.Expose;

public class MatchOdds {
	@Expose
	private String score;
	@Expose
	private String letBall;
	@Expose
	private String tz;
	@Expose
	private String odds;
	@Expose
	private String mainTeam;
	@Expose
	private String guestTeam;
	public String getScore() {
		return score;
	}
	public String getLetBall() {
		return letBall;
	}
	public String getTz() {
		return tz;
	}
	public String getOdds() {
		return odds;
	}
	public String getMainTeam() {
		return mainTeam;
	}
	public String getGuestTeam() {
		return guestTeam;
	}
}
