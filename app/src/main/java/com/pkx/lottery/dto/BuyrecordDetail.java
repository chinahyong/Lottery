package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class BuyrecordDetail {
	@Expose
	private String uid;
	@Expose
	private String oid;
	@Expose
	private String mboiid;

	public String getUid() {
		return uid;
	}

	public String getOid() {
		return oid;
	}

	public String getMboiid() {
		return mboiid;
	}

	public BuyrecordDetail(String uid, String oid, String mboiid) {
		this.uid = uid;
		this.oid = oid;
		this.mboiid = mboiid;
	}
}
