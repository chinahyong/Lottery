package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class RepayDto {
	@Expose
	private String uid;
	@Expose
	private String oid;

	public String getUid() {
		return uid;
	}

	@Expose
	private String mboiid;

	public RepayDto(String oid, String mboiid, String uid) {
		this.uid = uid;
		this.oid = oid;
		this.mboiid = mboiid;
	}

	public String getOid() {
		return oid;
	}

	public String getMboiid() {
		return mboiid;
	}
}
