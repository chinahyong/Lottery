package com.pkx.lottery.dto.corp;

import com.google.gson.annotations.Expose;

public class CorpFootballLottMatchItem {
	@Expose
	private String letBall;
	@Expose
	private String tz;
	@Expose
	private String mainTeam;
	@Expose
	private String guestTeam;
	@Expose
	private String match_id;
	@Expose
	private int dan;

	public String getLetBall() {
		return letBall;
	}

	public int getDan() {
		return dan;
	}

	public String getTz() {
		return tz;
	}

	public String getMainTeam() {
		return mainTeam;
	}

	public String getGuestTeam() {
		return guestTeam;
	}

	public String getMatch_id() {
		return match_id;
	}
}
